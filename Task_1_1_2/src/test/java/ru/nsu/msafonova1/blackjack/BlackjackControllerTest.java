package ru.nsu.msafonova1.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackControllerTest {

    @Test
    void shouldCreateGameWithPlayerDealerAndDeck() {
        BlackjackController controller =
                new BlackjackController("Alice");

        assertNotNull(controller.getPlayer());
        assertNotNull(controller.getDealer());
        assertNotNull(controller.getDeck());
    }

    @Test
    void shouldCreatePlayerWithCorrectName() {
        BlackjackController controller =
                new BlackjackController("Alice");

        assertEquals("Alice", controller.getPlayer().getName());
    }

    @Test
    void shouldCreateFullDeck() {
        BlackjackController controller =
                new BlackjackController("Alice");

        assertEquals(52, controller.getDeck().size());
    }
}