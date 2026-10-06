package ru.nsu.msafonova1.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    @Test
    void shouldCreateDealerWithEmptyHand() {
        Dealer dealer = new Dealer();

        assertNotNull(dealer.getHand());
        assertEquals(0, dealer.getHand().size());
    }

    @Test
    void shouldDrawWhenValueIsLessThan17() {
        Dealer dealer = new Dealer();

        dealer.addCard(new Card(Rank.TEN, Suit.HEARTS));
        dealer.addCard(new Card(Rank.FIVE, Suit.SPADES));

        assertTrue(dealer.shouldDraw());
    }

    @Test
    void shouldNotDrawWhenValueIs17OrMore() {
        Dealer dealer = new Dealer();

        dealer.addCard(new Card(Rank.TEN, Suit.HEARTS));
        dealer.addCard(new Card(Rank.SEVEN, Suit.SPADES));

        assertFalse(dealer.shouldDraw());
    }
}