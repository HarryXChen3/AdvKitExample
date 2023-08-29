package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.states.SuperstructureStates;

/**
 * This is the IO layer of the subsystem, which should only contain the direct hardware interactions.
 * @implSpec you <b>need</b> to implement all methods found in {@link ClawIO},
 * or let someone know if you feel that isn't needed
 */
public class ClawIOReal implements ClawIO {
    private final TalonSRX clawMainWheelMotor;
    private final TalonSRX clawFollowerWheelMotor;
    private final InvertType clawMainWheelMotorInverted;
    private final TalonFX clawOpenCloseMotor;
    private final InvertedValue clawOpenCloseMotorInverted;
    private final CANcoder clawOpenCloseEncoder;
    private final CANSparkMax clawTiltNeo;
    private final CANcoder clawTiltEncoder;

    private final ProfiledPIDController tiltPID;

    private final PositionVoltage positionVoltage = new PositionVoltage(0);
    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);

    private SuperstructureStates.ClawOpenCloseControlMode openCloseControlMode;
    private SuperstructureStates.ClawTiltControlMode clawTiltControlMode;
    private double desiredIntakeWheelsPercentOutput;
    private double desiredTiltControlInput;
    private double desiredOpenCloseControlInput;

    /**
     * This constructor should remain the same.
     * If you feel a need to modify this constructor, please let someone know, so they can adjust the test.
     * @param clawMainWheelMotor the {@link TalonSRX} controlling the main intake wheels
     * @param clawFollowerWheelMotor the {@link TalonSRX} following the main intake wheels
     * @param clawMainWheelMotorInverted  whether the {@link TalonSRX} controlling the main intake wheels is inverted
     * @param clawOpenCloseMotor  the {@link TalonFX} controlling the intake open & close
     * @param clawOpenCloseMotorInverted  whether the {@link TalonFX} controlling the intake open & close is inverted
     * @param clawOpenCloseEncoder the {@link CANcoder} for the open close {@link TalonFX}
     * @param clawTiltNeo the {@link CANSparkMax} for the claw tilt
     * @param clawTiltEncoder the {@link CANcoder} for the claw tilt
     */
    public ClawIOReal(
            final TalonSRX clawMainWheelMotor,
            final TalonSRX clawFollowerWheelMotor,
            final InvertType clawMainWheelMotorInverted,
            final TalonFX clawOpenCloseMotor,
            final InvertedValue clawOpenCloseMotorInverted,
            final CANcoder clawOpenCloseEncoder,
            final CANSparkMax clawTiltNeo,
            final CANcoder clawTiltEncoder
    ) {
        this.clawMainWheelMotor = clawMainWheelMotor;
        this.clawFollowerWheelMotor = clawFollowerWheelMotor;
        this.clawMainWheelMotorInverted = clawMainWheelMotorInverted;
        this.clawOpenCloseMotor = clawOpenCloseMotor;
        this.clawOpenCloseMotorInverted = clawOpenCloseMotorInverted;
        this.clawOpenCloseEncoder = clawOpenCloseEncoder;
        this.clawTiltNeo = clawTiltNeo;
        this.clawTiltEncoder = clawTiltEncoder;

        this.tiltPID = new ProfiledPIDController(
                1, 0, 0,
                new TrapezoidProfile.Constraints(20, 30)
        );

        this.tiltPID.reset(
                clawTiltEncoder.getAbsolutePosition().waitForUpdate(0.25).getValue(),
                clawTiltEncoder.getVelocity().waitForUpdate(0.25).getValue()
        );
    }

    @Override
    public void updateInputs(final ClawIOInputs inputs) {
        inputs.tiltPercentOutput = clawTiltNeo.getAppliedOutput();
        inputs.tiltEncoderPositionRots = clawTiltEncoder.getAbsolutePosition().refresh().getValue();
        inputs.tiltEncoderVelocityRotsPerSec = clawTiltEncoder.getVelocity().refresh().getValue();
        inputs.tiltCurrentAmps = clawTiltNeo.getOutputCurrent();

        inputs.openClosePercentOutput = clawOpenCloseMotor.getDutyCycle().refresh().getValue();
        inputs.openCloseEncoderPositionRots = clawOpenCloseEncoder.getAbsolutePosition().refresh().getValue();
        inputs.openCloseEncoderVelocityRotsPerSec = clawOpenCloseEncoder.getVelocity().refresh().getValue();
        inputs.openCloseCurrentAmps = clawOpenCloseMotor.getTorqueCurrent().refresh().getValue();

        inputs.intakeWheelsPercentOutput = clawMainWheelMotor.getMotorOutputPercent();
    }

    @Override
    public void periodic() {
        clawMainWheelMotor.set(ControlMode.PercentOutput, desiredIntakeWheelsPercentOutput);

        switch(openCloseControlMode) {
            case POSITION -> clawOpenCloseMotor.setControl(positionVoltage.withPosition(desiredOpenCloseControlInput));
            case DUTY_CYCLE -> clawOpenCloseMotor.setControl(dutyCycleOut.withOutput(desiredOpenCloseControlInput));
        }

        switch (clawTiltControlMode) {
            case POSITION -> clawTiltNeo.getPIDController().setReference(
                    tiltPID.calculate(
                            clawTiltEncoder.getAbsolutePosition().refresh().getValue(),
                            desiredTiltControlInput
                    ),
                    CANSparkMax.ControlType.kDutyCycle
            );
            case DUTY_CYCLE -> clawTiltNeo.getPIDController().setReference(
                    desiredTiltControlInput,
                    CANSparkMax.ControlType.kDutyCycle
            );
        }
    }

    @Override
    public void config() {
        // Bag Motors
        clawMainWheelMotor.configFactoryDefault();
        clawMainWheelMotor.setInverted(clawMainWheelMotorInverted);

        clawFollowerWheelMotor.configFactoryDefault();
        clawFollowerWheelMotor.set(ControlMode.Follower, clawMainWheelMotor.getDeviceID());
        clawFollowerWheelMotor.setInverted(InvertType.OpposeMaster);

        // Claw OpenClose Encoder
        final CANcoderConfiguration openCloseEncoderConfig = new CANcoderConfiguration();
        openCloseEncoderConfig.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Signed_PlusMinusHalf;
        clawOpenCloseEncoder.getConfigurator().apply(openCloseEncoderConfig);

        // Claw OpenClose Motor
        final TalonFXConfiguration openCloseMotorConfig = new TalonFXConfiguration();
        openCloseMotorConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
        openCloseMotorConfig.Feedback.FeedbackRemoteSensorID = clawOpenCloseEncoder.getDeviceID();
        openCloseMotorConfig.MotorOutput.Inverted = clawOpenCloseMotorInverted;
        openCloseMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        clawOpenCloseMotor.getConfigurator().apply(openCloseMotorConfig);

        // Claw Tilt Motor
        clawTiltNeo.setIdleMode(CANSparkMax.IdleMode.kBrake);

        // Claw Tilt Encoder
        final CANcoderConfiguration tiltEncoderConfig = new CANcoderConfiguration();
        tiltEncoderConfig.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
        clawTiltEncoder.getConfigurator().apply(tiltEncoderConfig);
    }

    @Override
    public void setDesiredState(final SuperstructureStates.ClawState desiredState) {
        desiredIntakeWheelsPercentOutput = desiredState.getIntakeWheelsPercentOutput();
        clawTiltControlMode = desiredState.getClawTiltControlMode();
        desiredTiltControlInput = desiredState.getTiltControlInput();
        openCloseControlMode = desiredState.getClawOpenCloseControlMode();
        desiredOpenCloseControlInput = desiredState.getOpenCloseControlInput();
    }
}
