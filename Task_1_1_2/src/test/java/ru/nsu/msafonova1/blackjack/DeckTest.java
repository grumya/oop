package ru.nsu.msafonova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Deck.
 */
class DeckTest {

    /**
     * Проверяет создание стандартной колоды из 52 карт.
     */
    @Test
    void shouldCreateStandardDeck() {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
    }

    /**
     * Проверяет извлечение карты и уменьшение размера колоды.
     */
    @Test
    void shouldDrawCardAndDecreaseSize() {
        Deck deck = new Deck();

        Card card = deck.draw();

        assertNotNull(card);
        assertEquals(51, deck.size());
    }

    /**
     * Проверяет, что колода содержит ровно 52 уникальные карты.
     */
    @Test
    void shouldContainUniqueCards() {
        Deck deck = new Deck();
        Set<String> cards = new HashSet<>();

        while (deck.size() > 0) {
            cards.add(deck.draw().toString());
        }

        assertEquals(52, cards.size());
    }

    /**
     * Проверяет выброс исключения при попытке взять карту из пустой колоды.
     */
    @Test
    void shouldThrowExceptionWhenDrawingFromEmptyDeck() {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertThrows(IllegalStateException.class, deck::draw);
    }

    /**
     * Проверяет, что перемешивание колоды не меняет количество карт.
     */
    @Test
    void shuffleShouldNotChangeDeckSize() {
        Deck deck = new Deck();

        deck.shuffle();

        assertEquals(52, deck.size());
    }
}