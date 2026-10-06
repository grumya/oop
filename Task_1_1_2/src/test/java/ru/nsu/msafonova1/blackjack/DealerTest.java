package ru.nsu.msafonova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Dealer.
 */
class DealerTest {

    /**
     * Проверяет создание дилера с пустой рукой.
     */
    @Test
    void shouldCreateDealerWithEmptyHand() {
        Dealer dealer = new Dealer();

        assertNotNull(dealer.getHand());
        assertEquals(0, dealer.getHand().size());
    }

    /**
     * Проверяет, что дилер должен добирать карту при сумме очков меньше 17.
     */
    @Test
    void shouldDrawWhenValueIsLessThan17() {
        Dealer dealer = new Dealer();

        dealer.addCard(new Card(Rank.TEN, Suit.HEARTS));
        dealer.addCard(new Card(Rank.FIVE, Suit.SPADES));

        assertTrue(dealer.shouldDraw());
    }

    /**
     * Проверяет, что дилер не добирает карту при сумме очков 17 и более.
     */
    @Test
    void shouldNotDrawWhenValueIs17OrMore() {
        Dealer dealer = new Dealer();

        dealer.addCard(new Card(Rank.TEN, Suit.HEARTS));
        dealer.addCard(new Card(Rank.SEVEN, Suit.SPADES));

        assertFalse(dealer.shouldDraw());
    }
}