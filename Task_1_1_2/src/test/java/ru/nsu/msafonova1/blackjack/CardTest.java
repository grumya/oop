package ru.nsu.msafonova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Card.
 */
class CardTest {

    /**
     * Проверяет правильность установки достоинства и масти карты.
     */
    @Test
    void shouldCreateCardWithCorrectRankAndSuit() {
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        assertEquals(Rank.ACE, card.getRank());
        assertEquals(Suit.HEARTS, card.getSuit());
    }

    /**
     * Проверяет базовое значение карты.
     */
    @Test
    void shouldReturnCorrectValue() {
        Card card = new Card(Rank.KING, Suit.SPADES);

        assertEquals(10, card.getValue());
    }

    /**
     * Проверяет строковое представление карты.
     */
    @Test
    void shouldReturnCorrectString() {
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        assertEquals(
                Rank.ACE.getName() + " " + Suit.HEARTS.getName() + " (11)",
                card.toString()
        );
    }
}