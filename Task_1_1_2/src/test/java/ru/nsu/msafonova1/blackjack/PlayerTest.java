package ru.nsu.msafonova1.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void shouldCreatePlayerWithCorrectName() {
        Player player = new Player("Alice");

        assertEquals("Alice", player.getName());
    }

    @Test
    void shouldCreatePlayerWithEmptyHand() {
        Player player = new Player("Alice");

        assertNotNull(player.getHand());
        assertEquals(0, player.getHand().size());
    }

    @Test
    void shouldAddCardToHand() {
        Player player = new Player("Alice");
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        player.addCard(card);

        assertEquals(1, player.getHand().size());
        assertEquals(card, player.getHand().getCards().get(0));
    }
}