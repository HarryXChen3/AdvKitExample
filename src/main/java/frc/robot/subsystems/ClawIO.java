package frc.robot.subsystems;

import frc.robot.states.SuperstructureStates;
import org.littletonrobotics.junction.AutoLog;

/**
 * The common abstraction layer between subsystems (which are completely detached from the hardware) and the underlying
 * IO layers (which commonly consist of real and simulated implementations)
 */
public interface ClawIO {
    /**
     * The current inputs of the IO layer (that are fed into the abstracted subsystem layer,
     * hence why they are called inputs, are they are inputs to the subsystem layer).
     * <p>
     *     This can also be considered as the current <i>outputs</i> of the IO layer
     *     (which are then fed as inputs into the subsystem layer).
     * </p>
     * @see ClawIOInputsAutoLogged
     * @see AutoLog
     */
    @AutoLog
    class ClawIOInputs {
        /**
         * The current DutyCycle or PercentOutput of the Tilt motor
         */
        double tiltPercentOutput = 0.0;
        /**
         * The current Tilt encoder position (in rots, or rotations)
         */
        double tiltEncoderPositionRots = 0.0;
        /**
         * The current Tilt encoder velocity (in rots/sec, or rps)
         */
        double tiltEncoderVelocityRotsPerSec = 0.0;
        /**
         * The measured current draw of the Tilt motor (in amps)
         */
        double tiltCurrentAmps = 0.0;

        /**
         * The current DutyCycle or PercentOutput of the OpenClose motor
         */
        double openClosePercentOutput = 0.0;
        /**
         * The current OpenClose encoder position (in rots, or rotations)
         */
        double openCloseEncoderPositionRots = 0.0;
        /**
         * The current OpenClose encoder velocity (in rots/sec, or rps)
         */
        double openCloseEncoderVelocityRotsPerSec = 0.0;
        /**
         * The measured current draw of the OpenClose motor (in amps)
         */
        double openCloseCurrentAmps = 0.0;

        /**
         * The current DutyCycle or PercentOutput of the IntakeWheels motor(s)
         */
        double intakeWheelsPercentOutput = 0.0;
    }

    /**
     * Updates the set of loggable inputs.
     * @param inputs Logged class of IOInputs
     * @see ClawIOInputs
     * @see AutoLog
     */
    @SuppressWarnings("unused")
    default void updateInputs(final ClawIOInputs inputs) {}

    /**
     * Periodic update call, containing only the logic that interacts directly with the hardware
     * in the IO layer.
     */
    default void periodic() {}

    /**
     * Configs the hardware (or simulated hardware) of the IO layer.
     */
    @SuppressWarnings("unused")
    default void config() {}

    /**
     * Sets the desired {@link SuperstructureStates.ClawState} of the IO layer,
     * this should only contain the necessary logic that interacts directly with hardware.
     * @param desiredState the desired {@link SuperstructureStates.ClawState}
     */
    default void setDesiredState(final SuperstructureStates.ClawState desiredState) {}
}
