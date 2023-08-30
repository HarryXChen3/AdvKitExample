// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;

public class Robot extends LoggedRobot {
    private Command autonomousCommand;
    private RobotContainer robotContainer;

    /**
     * When the robot is powered on, this is called. You might need to do some initialization logic here.
     * <p>A new {@link RobotContainer} is made here and {@link Logger#start()} is called here.</p>
     */
    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        Logger.getInstance().start();
    }

    /**
     * While the robot is on, this is called periodically. You should not need to do anything extra here.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /**
     * On disabled initialize, this is called. You do not need to do anything here.
     */
    @Override
    public void disabledInit() {
    }

    /**
     * While disabled, this is called periodically. You do not need to do anything here.
     */
    @Override
    public void disabledPeriodic() {
    }

    /**
     * On disabled exit, this is called. You do not need to do anything here.
     */
    @Override
    public void disabledExit() {
    }

    /**
     * On autonomous initialize, this is called. You probably do not need to do anything here.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    /**
     * While in autonomous, this is called periodically. You probably do not need to do anything here.
     */
    @Override
    public void autonomousPeriodic() {
    }

    /**
     * On autonomous exit, this is called. You probably do not need to do anything here.
     */
    @Override
    public void autonomousExit() {
    }

    /**
     * On teleop initialize, this is called. Do initialization work that needs to happen on teleop here.
     * <p>
     *     To schedule your {@link frc.robot.commands.ClawTeleop} command,
     *     you need to invoke {@link CommandScheduler#setDefaultCommand(Subsystem, Command)}
     *     with the {@link frc.robot.subsystems.Claw} and the {@link frc.robot.commands.ClawTeleop} commands,
     *     respectively.
     * </p>
     */
    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }

        CommandScheduler.getInstance().setDefaultCommand(robotContainer.claw, robotContainer.clawTeleop);
    }

    /**
     * While in teleop, this is called periodically. You might need to do something here.
     */
    @Override
    public void teleopPeriodic() {
    }

    /**
     * On teleop exit, this is called. You probably do not need to do anything here.
     */
    @Override
    public void teleopExit() {
    }

    /**
     * On test initialize, this is called. You do not need to do anything here.
     */
    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * While in test, this is called periodically. You do not need to do anything here.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * On test exit, this is called. You do not need to do anything here.
     */
    @Override
    public void testExit() {
    }
}
