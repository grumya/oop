package ru.nsu.msafonova1.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void shouldCreateCardWithCorrectRankAndSuit() {
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        assertEquals(Rank.ACE, card.getRank());
        assertEquals(Suit.HEARTS, card.getSuit());
    }

    @Test
    void shouldReturnCorrectValue() {
        Card card = new Card(Rank.KING, Suit.SPADES);

        assertEquals(10, card.getValue());
    }

    @Test
    void shouldReturnCorrectString() {
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        assertEquals(
                Rank.ACE.getName() + " " + Suit.HEARTS.getName() + " (11)",
                card.toString()
        );
    }
}