package frc.robot.states;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class SuperstructureStates {
    public enum ElevatorClawStateType {
        STANDBY,
        INTAKING,
        SCORING
    }

    public enum ClawTiltControlMode {
        POSITION,
        DUTY_CYCLE
    }

    public enum ClawOpenCloseControlMode {
        POSITION(ControlMode.Position),
        DUTY_CYCLE(ControlMode.PercentOutput);

        private final ControlMode controlMode;
        ClawOpenCloseControlMode(final ControlMode controlMode) {
            this.controlMode = controlMode;
        }

        public ControlMode getControlMode() {
            return controlMode;
        }
    }

    public enum ClawState {
        //Claw shoot cube
        CLAW_OUTTAKE(
                -0.1,
                ClawTiltControlMode.POSITION,
                0.295,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                0.2,
                ElevatorClawStateType.SCORING
        ),
        //Claw shoot cube
        CLAW_OUTTAKE_HYBRID(
                -0.2,
                ClawTiltControlMode.POSITION,
                .295,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                .3,
                ElevatorClawStateType.SCORING
        ),
        //Claw tilted down and closed
        CLAW_HOLDING(
                0.2,
                ClawTiltControlMode.POSITION,
                0,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                -0.37,
                ElevatorClawStateType.STANDBY
        ),
        //Claw tilted down and open cone
        CLAW_INTAKING_CONE(
                0.5,
                ClawTiltControlMode.POSITION,
                0.3,
                ClawOpenCloseControlMode.POSITION,
                0.024,
                ElevatorClawStateType.INTAKING
        ),
        //Claw tilted down and open cube
        CLAW_INTAKING_CUBE(
                0.5,
                ClawTiltControlMode.POSITION,
                0.3,
                ClawOpenCloseControlMode.POSITION,
                0.171,
                ElevatorClawStateType.INTAKING
        ),
        //Claw tilted down and standby wheel speed
        CLAW_STANDBY(
                0.2,
                ClawTiltControlMode.POSITION,
                0,
                ClawOpenCloseControlMode.POSITION,
                0,
                ElevatorClawStateType.STANDBY
        ),
        //Drop claw to outtake height
        CLAW_DROP(
                0.3,
                ClawTiltControlMode.POSITION,
                0.2,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                -0.37,
                ElevatorClawStateType.SCORING
        ),
        CLAW_ANGLE_SHOOT(
                0.2,
                ClawTiltControlMode.POSITION,
                0.12,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                -0.37,
                ElevatorClawStateType.SCORING
        ),
        CLAW_SHOOT_HIGH(
                -0.8,
                ClawTiltControlMode.POSITION,
                0.12,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                -0.37,
                ElevatorClawStateType.SCORING
        ),
        // TODO: when we get real robot check if this is mid or low, it used to be named CLAW_SHOOT_LOW (max cooked)
        CLAW_SHOOT_MID(
                -0.3,
                ClawTiltControlMode.POSITION,
                0.12,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                -0.37,
                ElevatorClawStateType.SCORING
        ),
        // TODO: tune these numbers on real
        CLAW_SHOOT_LOW(
                -0.15,
                ClawTiltControlMode.POSITION,
                0.2,
                ClawOpenCloseControlMode.DUTY_CYCLE,
                0.171,
                ElevatorClawStateType.SCORING
        ),
        CLAW_ANGLE_CUBE(
                0.6,
                ClawTiltControlMode.POSITION,
                0.4,
                ClawOpenCloseControlMode.POSITION,
                0.171,
                ElevatorClawStateType.INTAKING
        ),
        SINGLE_SUB(
                0.5,
                ClawTiltControlMode.POSITION,
                0.2,
                ClawOpenCloseControlMode.POSITION,
                0.049,
                ElevatorClawStateType.INTAKING
        ),
        TIPPED_CONE(
                0.5,
                ClawTiltControlMode.POSITION,
                0.45,
                ClawOpenCloseControlMode.POSITION,
                0.024,
                ElevatorClawStateType.INTAKING
        );

        final double intakeWheelsPercentOutput;
        final ClawTiltControlMode clawTiltControlMode;
        final double tiltControlInput;
        final ClawOpenCloseControlMode clawOpenCloseControlMode;
        final double openCloseControlInput;
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
