package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix6.signals.InvertedValue;

public class Constants {
    public static class Claw {
        public static final InvertedValue OPEN_CLOSE_MOTOR_INVERTED = InvertedValue.CounterClockwise_Positive;
        public static final InvertType MAIN_WHEEL_MOTOR_INVERTED = InvertType.None;
    }
}
