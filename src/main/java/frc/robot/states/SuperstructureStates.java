package frc.robot.states;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * States relevant to the Superstructure of the combined Elevator and Claw.
 */
public class SuperstructureStates {
    /**
     * Describes the category (mutual with the elevator) that a state belongs to.
     * <p>i.e. any state that involves scoring will be {@link ElevatorClawStateType#SCORING}</p>
     * @see ClawState
     */
    public enum ElevatorClawStateType {
        STANDBY,
        INTAKING,
        SCORING
    }

    /**
     * Describes the ControlMode of a {@link ClawState} for the Tilt motor (rev)
     */
    public enum ClawTiltControlMode {
        /**
         * Positional control mode for the Tilt motor
         */
        POSITION,
        /**
         * DutyCycle control mode for the Tilt motor
         */
        DUTY_CYCLE
    }

    /**
     * Describes the ControlMode of a {@link ClawState} for the OpenClose motor
     */
    // general rule of thumb...don't use suppress warnings if you don't absolutely need to!
    // they are only here because we don't want you to be confused by
    // warnings (that exist due to unused code) when committing!
    @SuppressWarnings("unused")
    public enum ClawOpenCloseControlMode {
        /**
         * Positional control mode for the OpenClose motor
         */
        POSITION,
        /**
         * DutyCycle (PercentOutput) control mode for the OpenClose motor
         */
        DUTY_CYCLE
    }

    // see previous comment about suppression of warnings
    @SuppressWarnings("unused")
    public enum ClawState {
        /**
         * Claw stowed up and standby wheel speed, open close is holding
         */
        CLAW_HOLDING(
                0.2,
                ClawTiltControlMode.POSITION,
                0,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                -0.37,
                ElevatorClawStateType.STANDBY
        ),
        /**
         * Claw tilted down and open cube
         */
        CLAW_INTAKING_CUBE(
                0.5,
                ClawTiltControlMode.POSITION,
                0.3,
                ClawOpenCloseControlMode.POSITION,
                0.171,
                ElevatorClawStateType.INTAKING
        ),
        /**
         * Claw stowed up and standby wheel speed, open close is open
         */
        CLAW_STANDBY(
                0.2,
                ClawTiltControlMode.POSITION,
                0,
                ClawOpenCloseControlMode.POSITION,
                0,
                ElevatorClawStateType.STANDBY
        );

        /**
         * The PercentOutput of the intake wheels at this state.
         */
        final double intakeWheelsPercentOutput;
        /**
         * The {@link ClawTiltControlMode} of this state.
         */
        final ClawTiltControlMode clawTiltControlMode;
        /**
         * The Tilt Control Input.
         *
         * <p>(This input may represent different units depending on the current {@link ClawTiltControlMode})</p>
         * <p>When {@link ClawTiltControlMode} is {@link ClawTiltControlMode#POSITION},
         * the units for this control input are in position rotations</p>
         */
        final double tiltControlInput;
        /**
         * The {@link ClawOpenCloseControlMode} of this state.
         */
        final ClawOpenCloseControlMode clawOpenCloseControlMode;
        /**
         * The OpenClose Control Input.
         *
         * <p>(This input may represent different units depending on the current {@link ClawOpenCloseControlMode})</p>
         * <p>When {@link ClawOpenCloseControlMode} is {@link ClawOpenCloseControlMode#POSITION},
         * the units for this control input are in position rotations</p>
         */
        final double openCloseControlInput;
        /**
         * The {@link ElevatorClawStateType} of this state.
         */
        final ElevatorClawStateType elevatorClawStateType;

        public double getIntakeWheelsPercentOutput() {
            return intakeWheelsPercentOutput;
        }

        public ClawTiltControlMode getClawTiltControlMode() {
            return clawTiltControlMode;
        }

        public double getTiltControlInput() {
            return tiltControlInput;
        }

        public ClawOpenCloseControlMode getClawOpenCloseControlMode() {
            return clawOpenCloseControlMode;
        }

        public double getOpenCloseControlInput() {
            return openCloseControlInput;
        }

        public ElevatorClawStateType getElevatorStateType() {
            return elevatorClawStateType;
        }

        ClawState(final double intakeWheelsPercentOutput,
                  final ClawTiltControlMode clawTiltControlMode,
                  final double tiltControlInput,
                  final ClawOpenCloseControlMode clawOpenCloseControlMode,
                  final double openCloseControlInput,
                  final ElevatorClawStateType elevatorClawStateType
        ) {
            this.intakeWheelsPercentOutput = intakeWheelsPercentOutput;
            this.clawTiltControlMode = clawTiltControlMode;
            this.tiltControlInput = tiltControlInput;
            this.clawOpenCloseControlMode = clawOpenCloseControlMode;
            this.openCloseControlInput = openCloseControlInput;
            this.elevatorClawStateType = elevatorClawStateType;
        }
    }
}
