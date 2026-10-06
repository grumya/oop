package ru.nsu.msafonova1.blackjack;

/**
 * Представляет четыре масти стандартной колоды.
 */
public enum Suit {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs"),
    SPADES("Spades");

    private final String name;

    Suit(String name) {
        this.name = name;
    }

    /**
     * Возвращает название масти.
     *
     * @return название масти
     */
    public String getName() {
        return name;
    }
}