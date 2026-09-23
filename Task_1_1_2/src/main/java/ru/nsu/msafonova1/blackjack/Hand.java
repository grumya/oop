package ru.nsu.msafonova1.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет набор карт игрока или дилера.
 */
public class Hand {
    private final List<Card> cards;

    /**
     * Создаёт пустую руку.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку.
     *
     * @param card добавляемая карта
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Возвращает копию списка карт.
     *
     * @return список карт
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Возвращает количество карт в руке.
     *
     * @return количество карт
     */
    public int size() {
        return cards.size();
    }

    /**
     * Вычисляет текущее значение руки.
     * Туз считается за 11, если это возможно,
     * и за 1 в противном случае.
     *
     * @return значение руки
     */
    public int getValue() {
        int value = 0;
        int aces = 0;

        for (Card card : cards) {
            value += card.getValue();

            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }

        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }

    /**
     * Проверяет, является ли рука блэкджеком.
     *
     * @return true, если в руке ровно две карты со значением 21
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    /**
     * Проверяет, превышает ли значение руки 21.
     *
     * @return true, если игрок проиграл из-за превышения 21
     */
    public boolean isBust() {
        return getValue() > 21;
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}