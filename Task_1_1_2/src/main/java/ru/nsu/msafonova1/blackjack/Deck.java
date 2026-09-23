package ru.nsu.msafonova1.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Представляет колоду игральных карт.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Создаёт стандартную колоду из 52 карт.
     */
    public Deck() {
        cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Перемешивает колоду.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Извлекает одну карту из колоды.
     *
     * @return извлечённая карта
     * @throws IllegalStateException если колода пуста
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Колода пуста");
        }

        return cards.remove(cards.size() - 1);
    }

    /**
     * Возвращает количество карт в колоде.
     *
     * @return количество оставшихся карт
     */
    public int size() {
        return cards.size();
    }
}