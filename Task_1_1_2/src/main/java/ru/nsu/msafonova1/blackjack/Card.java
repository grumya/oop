package ru.nsu.msafonova1.blackjack;

/**
 * Представляет игральную карту.
 */
public class Card {
    private final Rank rank;
    private final Suit suit;

    /**
     * Создаёт игральную карту.
     *
     * @param rank достоинство карты
     * @param suit масть карты
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Возвращает достоинство карты.
     *
     * @return достоинство карты
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Возвращает масть карты.
     *
     * @return масть карты
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Возвращает базовое значение карты.
     *
     * @return значение карты
     */
    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank.getName() + " " + suit.getName()
                + " (" + getValue() + ")";
    }
}