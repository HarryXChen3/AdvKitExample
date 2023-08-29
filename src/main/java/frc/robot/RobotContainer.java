// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

/**
 * This class typically contains all subsystem and hardware declarations.
 * <p>
 * You will need to initialize at least {@link frc.robot.subsystems.Claw} and {@link frc.robot.commands.ClawTeleop}.
 * There may be more to initialize depending on your own implementation.
 * </p>
 *
 * @implSpec most, if not all fields should be <b>public final</b>, and can be accessed by directly indexing the
 * object without the need for a getter (this should be the only class where that is the case)
 * <pre>
 * {@code
 * public final TalonFX talonFx;
 * public final Swerve swerve;
 * }
 * </pre>
 */
public class RobotContainer {
    public final TalonSRX clawMainWheelsMotor;
    public final TalonSRX clawFollowerWheelsMotor;
    public final TalonFX clawOpenCloseMotor;
    public final CANCoder clawOpenCloseEncoder;
    public final CANcoder clawTiltEncoder;
    public final CANSparkMax clawTiltNeo;

    /**
     * The constructor is where you will typically initialize all objects in this class.
     */
    public RobotContainer() {
        clawMainWheelsMotor = new TalonSRX(2);
        clawFollowerWheelsMotor = new TalonSRX(3);
        clawOpenCloseMotor = new TalonFX(4);
        clawOpenCloseEncoder = new CANCoder(5);

        clawTiltNeo = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
        clawTiltEncoder = new CANcoder(7);

        configureBindings();
    }

    /**
     * Any button bindings can be configured here. (you might not have any)
     */
    private void configureBindings() {}

    /**
     * You probably do not need to change this unless we are doing something related to autonomous.
     *
     * @return the autonomous {@link Command}
     */
    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
