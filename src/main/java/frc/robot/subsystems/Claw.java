package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.states.SuperstructureStates;

@SuppressWarnings("unused")
public class Claw extends SubsystemBase {
    private final ClawIO clawIO;
    private final ClawIOInputsAutoLogged inputs;

    /**
     * Example constructor that takes a {@link ClawIO}.
     * @param clawIO the {@link ClawIO}
     */
    public Claw(final ClawIO clawIO) {
        this.clawIO = clawIO;
        this.inputs = new ClawIOInputsAutoLogged();
    }

    /**
     * Example periodic call that invokes {@link ClawIO#periodic()}
     * and also {@link ClawIO#updateInputs(ClawIO.ClawIOInputs)}.
     *
     * <p>There may need to be more logic here.</p>
     */
    @Override
    public void periodic() {
        clawIO.periodic();
        clawIO.updateInputs(inputs);
    }

    /**
     * Sets the desired {@link frc.robot.states.SuperstructureStates.ClawState}.
     *
     * @param desiredState the new desired {@link frc.robot.states.SuperstructureStates.ClawState}
     * @see ClawIO#setDesiredState(SuperstructureStates.ClawState)
     */
    public void setDesiredState(final SuperstructureStates.ClawState desiredState) {}

    /**
     * Get the desired {@link SuperstructureStates.ClawState}
     * @return the currently desired {@link SuperstructureStates.ClawState}
     * @see SuperstructureStates.ClawState
     */
    public SuperstructureStates.ClawState getDesiredState() {
        return null;
    }
}
