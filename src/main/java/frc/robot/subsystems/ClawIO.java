package frc.robot.subsystems;

import frc.robot.states.SuperstructureStates;
import org.littletonrobotics.junction.AutoLog;

public interface ClawIO {
    @AutoLog
    class ClawIOInputs {
        double tiltPercentOutput = 0.0;
        double tiltEncoderPositionRots = 0.0;
        double tiltEncoderVelocityRotsPerSec = 0.0;
        double tiltCurrentAmps = 0.0;

        double openClosePercentOutput = 0.0;
        double openCloseEncoderPositionRots = 0.0;
        double openCloseEncoderVelocityRotsPerSec = 0.0;
        double openCloseCurrentAmps = 0.0;

        double intakeWheelsPercentOutput = 0.0;
    }

    /**
     * Updates the set of loggable inputs.
     * @param inputs Logged class of IOInputs
     * @see ClawIOInputs
     * @see AutoLog
     */
    default void updateInputs(final ClawIOInputs inputs) {}

    /**
     * Periodic update call, containing only the logic that interacts directly with the hardware
     * in the IO layer.
     */
    default void periodic() {}

    /**
     * Configs the hardware (or simulated hardware) of the IO layer.
     */
    default void config() {}

    /**
     * Sets the desired {@link SuperstructureStates.ClawState} of the IO layer,
     * this should only contain the necessary logic that interacts directly with hardware.
     * @param desiredState the desired {@link SuperstructureStates.ClawState}
     */
    default void setDesiredState(final SuperstructureStates.ClawState desiredState) {}
}
