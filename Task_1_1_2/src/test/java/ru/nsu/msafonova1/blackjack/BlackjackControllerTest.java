package ru.nsu.msafonova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса BlackjackController.
 */
class BlackjackControllerTest {

    /**
     * Проверяет инициализацию контроллера, игрока, дилера и колоды.
     */
    @Test
    void shouldCreateGameWithPlayerDealerAndDeck() {
        BlackjackController controller =
                new BlackjackController("Alice");

        assertNotNull(controller.getPlayer());
        assertNotNull(controller.getDealer());
        assertNotNull(controller.getDeck());
    }

    /**
     * Проверяет правильность установки имени игрока.
     */
    @Test
    void shouldCreatePlayerWithCorrectName() {
        BlackjackController controller =
                new BlackjackController("Alice");

        assertEquals("Alice", controller.getPlayer().getName());
    }

    /**
     * Проверяет, что при старте создаётся полная колода из 52 карт.
     */
    @Test
    void shouldCreateFullDeck() {
        BlackjackController controller =
                new BlackjackController("Alice");

        assertEquals(52, controller.getDeck().size());
    }
}