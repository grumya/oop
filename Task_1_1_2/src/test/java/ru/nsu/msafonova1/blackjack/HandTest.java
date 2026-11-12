package ru.nsu.msafonova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Hand.
 */
class HandTest {

    /**
     * Проверяет, что новая рука пуста и её сумма равна нулю.
     */
    @Test
    void shouldStartEmpty() {
        Hand hand = new Hand();

        assertEquals(0, hand.size());
        assertEquals(0, hand.getValue());
    }

    /**
     * Проверяет добавление карты в руку.
     */
    @Test
    void shouldAddCard() {
        Hand hand = new Hand();
        Card card = new Card(Rank.KING, Suit.HEARTS);

        hand.addCard(card);

        assertEquals(1, hand.size());
        assertEquals(card, hand.getCards().get(0));
    }

    /**
     * Проверяет подсчёт суммы очков без тузов.
     */
    @Test
    void shouldCalculateSimpleValue() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.TEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.SEVEN, Suit.SPADES));

        assertEquals(17, hand.getValue());
    }

    /**
     * Проверяет уменьшение стоимости туза до 1 при переборе.
     */
    @Test
    void shouldTreatAceAsOneWhenNecessary() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.SPADES));
        hand.addCard(new Card(Rank.FIVE, Suit.CLUBS));

        assertEquals(16, hand.getValue());
    }

    /**
     * Проверяет определение комбинации Блэкджек из двух карт.
     */
    @Test
    void shouldRecognizeBlackjack() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.SPADES));

        assertTrue(hand.isBlackjack());
    }

    /**
     * Проверяет, что сумма 21 из трёх карт не является Блэкджеком.
     */
    @Test
    void shouldNotRecognizeBlackjackWithThreeCards() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.TEN, Suit.SPADES));
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));

        assertFalse(hand.isBlackjack());
    }

    /**
     * Проверяет определение перебора (более 21 очка).
     */
    @Test
    void shouldRecognizeBust() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.KING, Suit.HEARTS));
        hand.addCard(new Card(Rank.QUEEN, Suit.SPADES));
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));

        assertTrue(hand.isBust());
    }

    /**
     * Проверяет, что метод getCards возвращает копию списка карт.
     */
    @Test
    void shouldReturnCopyOfCards() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));

        List<Card> cards = hand.getCards();
        cards.clear();

        assertEquals(1, hand.size());
    }
}