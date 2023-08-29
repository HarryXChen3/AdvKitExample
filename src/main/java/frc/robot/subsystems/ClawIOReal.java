package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.revrobotics.CANSparkMax;
/**
 * This is the IO layer of the subsystem, which should only contain the direct hardware interactions.
 * @implSpec you <b>need</b> to implement all methods found in {@link ClawIO},
 * or let someone know if you feel that isn't needed
 */
@SuppressWarnings("unused")
public class ClawIOReal implements ClawIO {
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
    ) {}
}
