package ru.nsu.msafonova1.blackjack;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void shouldCreateStandardDeck() {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
    }

    @Test
    void shouldDrawCardAndDecreaseSize() {
        Deck deck = new Deck();

        Card card = deck.draw();

        assertNotNull(card);
        assertEquals(51, deck.size());
    }

    @Test
    void shouldContainUniqueCards() {
        Deck deck = new Deck();
        Set<String> cards = new HashSet<>();

        while (deck.size() > 0) {
            cards.add(deck.draw().toString());
        }

        assertEquals(52, cards.size());
    }

    @Test
    void shouldThrowExceptionWhenDrawingFromEmptyDeck() {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertThrows(IllegalStateException.class, deck::draw);
    }

    @Test
    void shuffleShouldNotChangeDeckSize() {
        Deck deck = new Deck();

        deck.shuffle();

        assertEquals(52, deck.size());
    }
}