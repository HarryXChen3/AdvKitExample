package frc.robot.subsystems;

import frc.robot.states.SuperstructureStates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClawTest {
    @Mock
    private ClawIO clawIO;
    private Claw claw;

    @BeforeEach
    void setUp() {
        this.claw = new Claw(clawIO);
    }

    @Test
    void periodic_shouldCallIOPeriodicAndUpdateInputs() {
        claw.periodic();
        verify(clawIO).periodic();
        verify(clawIO).updateInputs(any());
    }

    @ParameterizedTest
    @EnumSource(SuperstructureStates.ClawState.class)
    void setDesiredState_shouldSetDesiredState(final SuperstructureStates.ClawState clawState) {
        claw.setDesiredState(clawState);
        verify(clawIO).setDesiredState(clawState);
    }

    @ParameterizedTest
    @EnumSource(SuperstructureStates.ClawState.class)
    void getDesiredState(final SuperstructureStates.ClawState clawState) {
        claw.setDesiredState(clawState);
        assertEquals(clawState, claw.getDesiredState());
    }
}