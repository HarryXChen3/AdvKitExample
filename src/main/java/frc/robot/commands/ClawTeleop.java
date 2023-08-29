package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.states.SuperstructureStates;
import frc.robot.subsystems.Claw;

/**
 * This teleop command should interact with the {@link Claw} in some way.
 */
public class ClawTeleop extends CommandBase {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final Claw claw;

    /**
     * This is some basic logic that takes in a {@link Claw} and stores it into a field while also adding it to the
     * command's requirements using {@link CommandBase#addRequirements(Subsystem...)}.
     *
     * <p>There may need to be more code here. The implementation is left up to the student's discretion.</p>
     * @param claw the {@link Claw}
     */
    public ClawTeleop(final Claw claw) {
        this.claw = claw;
        addRequirements(claw);
    }


    /**
     * Initialize the command here.
     * It may not need initialization depending on what you have set up.
     */
    @Override
    public void initialize() {
        super.initialize();
    }

    /**
     * This should do make some basic {@link Claw#setDesiredState(SuperstructureStates.ClawState)} calls.
     * <p>The actual calls themselves are up to the student's discretion.</p>
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * On finished, end will be invoked. Do any cleanup or finalizing here (you may not need to!)
     * @param interrupted whether the command was interrupted/canceled
     */
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }

    /**
     * Returns if this command is finished (this logic is up to the student's discretion).
     * @return whether the command is finished
     */
    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
