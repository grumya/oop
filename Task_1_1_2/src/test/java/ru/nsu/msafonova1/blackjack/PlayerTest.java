package ru.nsu.msafonova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Player.
 */
class PlayerTest {

    /**
     * Проверяет правильность сохранения имени игрока.
     */
    @Test
    void shouldCreatePlayerWithCorrectName() {
        Player player = new Player("Alice");

        assertEquals("Alice", player.getName());
    }

    /**
     * Проверяет инициализацию игрока с пустой рукой.
     */
    @Test
    void shouldCreatePlayerWithEmptyHand() {
        Player player = new Player("Alice");

        assertNotNull(player.getHand());
        assertEquals(0, player.getHand().size());
    }

    /**
     * Проверяет добавление карты в руку игрока.
     */
    @Test
    void shouldAddCardToHand() {
        Player player = new Player("Alice");
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        player.addCard(card);

        assertEquals(1, player.getHand().size());
        assertEquals(card, player.getHand().getCards().get(0));
    }
}