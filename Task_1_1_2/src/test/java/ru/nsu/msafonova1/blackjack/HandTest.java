package ru.nsu.msafonova1.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void shouldStartEmpty() {
        Hand hand = new Hand();

        assertEquals(0, hand.size());
        assertEquals(0, hand.getValue());
    }

    @Test
    void shouldAddCard() {
        Hand hand = new Hand();
        Card card = new Card(Rank.KING, Suit.HEARTS);

        hand.addCard(card);

        assertEquals(1, hand.size());
        assertEquals(card, hand.getCards().get(0));
    }

    @Test
    void shouldCalculateSimpleValue() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.TEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.SEVEN, Suit.SPADES));

        assertEquals(17, hand.getValue());
    }

    @Test
    void shouldTreatAceAsOneWhenNecessary() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.SPADES));
        hand.addCard(new Card(Rank.FIVE, Suit.CLUBS));

        assertEquals(16, hand.getValue());
    }

    @Test
    void shouldRecognizeBlackjack() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.SPADES));

        assertTrue(hand.isBlackjack());
    }

    @Test
    void shouldNotRecognizeBlackjackWithThreeCards() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.TEN, Suit.SPADES));
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));

        assertFalse(hand.isBlackjack());
    }

    @Test
    void shouldRecognizeBust() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.KING, Suit.HEARTS));
        hand.addCard(new Card(Rank.QUEEN, Suit.SPADES));
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));

        assertTrue(hand.isBust());
    }

    @Test
    void shouldReturnCopyOfCards() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));

        var cards = hand.getCards();
        cards.clear();

        assertEquals(1, hand.size());
    }
}