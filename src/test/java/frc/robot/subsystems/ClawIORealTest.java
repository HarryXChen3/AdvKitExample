package frc.robot.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CANcoderConfigurator;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import frc.robot.Constants;
import frc.robot.states.SuperstructureStates;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import testutils.JNIUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClawIORealTest {
    private ClawIOReal clawIOReal;

    @Spy
    private final TalonSRX clawMainWheelMotor = new TalonSRX(2);
    @Spy
    private final TalonSRX clawFollowerWheelMotor = new TalonSRX(3);
    @Spy
    private final TalonFX clawOpenCloseMotor = new TalonFX(4);
    @Spy
    private final CANcoder clawOpenCloseEncoder = new CANcoder(5);
    @Spy
    private final CANSparkMax clawTiltNeo = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
    @Spy
    private final CANcoder clawTiltEncoder = new CANcoder(7);

    @BeforeAll
    static void beforeAll() {
        JNIUtils.initializeHAL();
        JNIUtils.loadCTREPhoenix5JNI();
        JNIUtils.loadCTREPhoenix6JNI();
        JNIUtils.loadRevJNI();
    }

    @BeforeEach
    void setUp() {
        this.clawIOReal = new ClawIOReal(
                clawMainWheelMotor,
                clawFollowerWheelMotor,
                Constants.Claw.MAIN_WHEEL_MOTOR_INVERTED,
                clawOpenCloseMotor,
                Constants.Claw.OPEN_CLOSE_MOTOR_INVERTED,
                clawOpenCloseEncoder,
                clawTiltNeo,
                clawTiltEncoder
        );
    }

    @AfterEach
    void tearDown() {
        clawOpenCloseMotor.close();
        clawOpenCloseEncoder.close();
        clawTiltNeo.close();
        clawTiltEncoder.close();
    }

    @AfterAll
    static void afterAll() {
        JNIUtils.shutdownHAL();
    }

    @Test
    void updateInputs() {
        // mock the tilt duty cycle (percent output)
        final double tiltDutyCycle = 0.1683;
        when(clawTiltNeo.getAppliedOutput()).thenReturn(tiltDutyCycle);

        // mock the tilt absolute position signal
        final double tiltAbsolutePosition = 0.2974;
        final StatusSignal<Double> mockedTiltAbsolutePositionSignal = mock();
        when(mockedTiltAbsolutePositionSignal.refresh()).thenReturn(mockedTiltAbsolutePositionSignal);
        when(mockedTiltAbsolutePositionSignal.getValue()).thenReturn(tiltAbsolutePosition);
        when(clawTiltEncoder.getAbsolutePosition()).thenReturn(mockedTiltAbsolutePositionSignal);

        // mock the tilt velocity signal
        final double tiltVelocity = -0.1771;
        final StatusSignal<Double> mockedTiltVelocitySignal = mock();
        when(mockedTiltVelocitySignal.refresh()).thenReturn(mockedTiltVelocitySignal);
        when(mockedTiltVelocitySignal.getValue()).thenReturn(tiltVelocity);
        when(clawTiltEncoder.getVelocity()).thenReturn(mockedTiltVelocitySignal);

        // mock the tilt output current
        final double tiltOutputCurrent = 16.83;
        when(clawTiltNeo.getOutputCurrent()).thenReturn(tiltOutputCurrent);

        // mock the open close duty cycle signal
        final double openCloseDutyCycle = -0.1746;
        final StatusSignal<Double> mockedOpenCloseDutyCycle = mock();
        when(mockedOpenCloseDutyCycle.refresh()).thenReturn(mockedOpenCloseDutyCycle);
        when(mockedOpenCloseDutyCycle.getValue()).thenReturn(openCloseDutyCycle);
        when(clawOpenCloseMotor.getDutyCycle()).thenReturn(mockedOpenCloseDutyCycle);

        // mock the open close absolute position signal
        final double openCloseAbsolutePosition = -0.4509;
        final StatusSignal<Double> mockedOpenCloseAbsolutePositionSignal = mock();
        when(mockedOpenCloseAbsolutePositionSignal.refresh()).thenReturn(mockedOpenCloseAbsolutePositionSignal);
        when(mockedOpenCloseAbsolutePositionSignal.getValue()).thenReturn(openCloseAbsolutePosition);
        when(clawOpenCloseEncoder.getAbsolutePosition()).thenReturn(mockedOpenCloseAbsolutePositionSignal);

        // mock the open close velocity signal
        final double openCloseVelocity = -4.188;
        final StatusSignal<Double> mockedOpenCloseVelocitySignal = mock();
        when(mockedOpenCloseVelocitySignal.refresh()).thenReturn(mockedOpenCloseVelocitySignal);
        when(mockedOpenCloseVelocitySignal.getValue()).thenReturn(openCloseVelocity);
        when(clawOpenCloseEncoder.getVelocity()).thenReturn(mockedOpenCloseVelocitySignal);

        // mock the open close torque current signal
        final double openCloseTorqueCurrent = 6.919;
        final StatusSignal<Double> mockedOpenCloseTorqueCurrentSignal = mock();
        when(mockedOpenCloseTorqueCurrentSignal.refresh()).thenReturn(mockedOpenCloseTorqueCurrentSignal);
        when(mockedOpenCloseTorqueCurrentSignal.getValue()).thenReturn(openCloseTorqueCurrent);
        when(clawOpenCloseMotor.getTorqueCurrent()).thenReturn(mockedOpenCloseTorqueCurrentSignal);

        // mock the main wheel motor percent output
        final double mainWheelPercentOutput = 0.1261;
        when(clawMainWheelMotor.getMotorOutputPercent()).thenReturn(mainWheelPercentOutput);

        final ClawIOInputsAutoLogged inputs = new ClawIOInputsAutoLogged();
        clawIOReal.updateInputs(inputs);

        assertEquals(tiltDutyCycle, inputs.tiltPercentOutput);
        assertEquals(tiltAbsolutePosition, inputs.tiltEncoderPositionRots);
        assertEquals(tiltVelocity, inputs.tiltEncoderVelocityRotsPerSec);
        assertEquals(tiltOutputCurrent, inputs.tiltCurrentAmps);
        assertEquals(openCloseDutyCycle, inputs.openClosePercentOutput);
        assertEquals(openCloseAbsolutePosition, inputs.openCloseEncoderPositionRots);
        assertEquals(openCloseVelocity, inputs.openCloseEncoderVelocityRotsPerSec);
        assertEquals(openCloseTorqueCurrent, inputs.openCloseCurrentAmps);
        assertEquals(mainWheelPercentOutput, inputs.intakeWheelsPercentOutput);
    }

    @ParameterizedTest
    @EnumSource(SuperstructureStates.ClawState.class)
    void periodic(final SuperstructureStates.ClawState clawState) {
        final SparkMaxPIDController clawTiltNeoPID = spy(clawTiltNeo.getPIDController());
        doReturn(clawTiltNeoPID).when(clawTiltNeo).getPIDController();

        clawIOReal.setDesiredState(clawState);
        clawIOReal.periodic();

        // require that the intake wheels percent output is set to the claw main wheel motor
        verify(clawMainWheelMotor).set(ControlMode.PercentOutput, clawState.getIntakeWheelsPercentOutput());
        // require that there are no interactions with the follower (as they are not needed)
        verify(clawFollowerWheelMotor, never()).set((ControlMode) any(), anyDouble());

        // we can't exactly check the open close motor as it involves phoenix 6 requests like PositionVoltage
        // so just loosely check it for now...
        switch (clawState.getClawOpenCloseControlMode()) {
            case POSITION -> assertInstanceOf(
                    PositionTorqueCurrentFOC.class,
                    clawOpenCloseMotor.getAppliedControl()
            );
            case DUTY_CYCLE -> assertInstanceOf(DutyCycleOut.class, clawOpenCloseMotor.getAppliedControl());
        }

        // we can't easily check the tilt motor as it involves PID outputs however, not only can we not
        // check the input itself, there are multiple ways to demand a DutyCycle to a SparkMAX
        // (through .set() or .getPIDController().setReference())

        // -> the solution to this is to enforce students to only invoke .getPIDController().setReference()
        // instead of using .set()

        // why? because .set() isn't clear as to what control is being set, and I'd rather be explicitly clear
        // regarding the actual input, that can just be left as anyDouble()
        switch (clawState.getClawTiltControlMode()) {
            case POSITION -> verify(clawTiltNeoPID).setReference(anyDouble(), eq(CANSparkMax.ControlType.kDutyCycle));
            case DUTY_CYCLE -> assertEquals(clawState.getTiltControlInput(), clawTiltNeo.getAppliedOutput());
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static boolean invertTypeToBool(final InvertType invertType) {
        return switch(invertType){
            case None -> false;
            case InvertMotorOutput -> true;
            default -> throw new RuntimeException(String.format("Cannot convert InvertType %s to bool", invertType));
        };
    }

    @Test
    void config() {
        when(clawMainWheelMotor.configFactoryDefault()).thenReturn(ErrorCode.OK);

        clawIOReal.config();

        // require that the main wheel motor must be properly inverted
        assertEquals(invertTypeToBool(Constants.Claw.MAIN_WHEEL_MOTOR_INVERTED), clawMainWheelMotor.getInverted());
        // require that the follower wheel motor must be a follower to the main wheel motor
        assertEquals(ControlMode.Follower, clawFollowerWheelMotor.getControlMode());

        // require that the follower wheel motor must be OpposeMaster
        // TODO: maybe there is a way to use an assertion here but, I tried but it seems like its
        //  not possible to get the current inversion of the motor as an InvertType, only as a bool,
        //  and that bool won't reflect the changes of FollowMaster or OpposeMaster, as to do that,
        //  it must call the JNI, but it cannot call the JNI since we're in sim (while we're in a test)
        verify(clawFollowerWheelMotor).setInverted(InvertType.OpposeMaster);

        final CANcoderConfigurator openCloseEncoderConfigurator = clawOpenCloseEncoder.getConfigurator();
        final CANcoderConfiguration openCloseEncoderConfiguration = new CANcoderConfiguration();
        assertTrue(openCloseEncoderConfigurator.refresh(openCloseEncoderConfiguration).isOK());

        // require that the MagnetOffset must be 0
        assertEquals(0, openCloseEncoderConfiguration.MagnetSensor.MagnetOffset);
        // require that the AbsoluteSensorRange should be +-0.5
        assertEquals(
                AbsoluteSensorRangeValue.Signed_PlusMinusHalf,
                openCloseEncoderConfiguration.MagnetSensor.AbsoluteSensorRange
        );
        // require that the SensorDirectionValue should remain unchanged (CCW+)
        assertEquals(
                SensorDirectionValue.CounterClockwise_Positive,
                openCloseEncoderConfiguration.MagnetSensor.SensorDirection
        );

        final TalonFXConfigurator openCloseMotorConfigurator = clawOpenCloseMotor.getConfigurator();
        final TalonFXConfiguration openCloseMotorConfiguration = new TalonFXConfiguration();
        assertTrue(openCloseMotorConfigurator.refresh(openCloseMotorConfiguration).isOK());

        // require that the FeedbackRemoteSensorID must be the OpenCloseEncoder device ID
        assertEquals(clawOpenCloseEncoder.getDeviceID(), openCloseMotorConfiguration.Feedback.FeedbackRemoteSensorID);

        // require that the FeedbackSensorSource is either Fused or Remote
        final FeedbackSensorSourceValue configFeedbackSensorSource
                = openCloseMotorConfiguration.Feedback.FeedbackSensorSource;
        assertTrue(
                configFeedbackSensorSource == FeedbackSensorSourceValue.FusedCANcoder
                        || configFeedbackSensorSource == FeedbackSensorSourceValue.RemoteCANcoder
        );

        // require that the MotorOutput.Inverted value is correctly set
        assertEquals(Constants.Claw.OPEN_CLOSE_MOTOR_INVERTED, openCloseMotorConfiguration.MotorOutput.Inverted);
        // require that the OpenClose motor NeutralMode be set as brake
        assertEquals(NeutralModeValue.Brake, openCloseMotorConfiguration.MotorOutput.NeutralMode);

        // require that the IdleMode of the Tilt Neo is set as brake
        assertEquals(CANSparkMax.IdleMode.kBrake, clawTiltNeo.getIdleMode());

        final CANcoderConfigurator tiltEncoderConfigurator = clawTiltEncoder.getConfigurator();
        final CANcoderConfiguration tiltEncoderConfiguration = new CANcoderConfiguration();
        assertTrue(tiltEncoderConfigurator.refresh(tiltEncoderConfiguration).isOK());
        // require that the AbsoluteSensorRange should be +1
        assertEquals(
                AbsoluteSensorRangeValue.Unsigned_0To1,
                tiltEncoderConfiguration.MagnetSensor.AbsoluteSensorRange
        );
    }

    @ParameterizedTest
    @EnumSource(SuperstructureStates.ClawState.class)
    void setDesiredState(final SuperstructureStates.ClawState clawState) {
        final SuperstructureStates.ClawState spiedClawState = spy(clawState);

        clawIOReal.setDesiredState(spiedClawState);
        verify(spiedClawState).getIntakeWheelsPercentOutput();
        verify(spiedClawState).getClawTiltControlMode();
        verify(spiedClawState).getTiltControlInput();
        verify(spiedClawState).getClawOpenCloseControlMode();
        verify(spiedClawState).getOpenCloseControlInput();
    }
}