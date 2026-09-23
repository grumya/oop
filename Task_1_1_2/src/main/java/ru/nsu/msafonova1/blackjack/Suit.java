package ru.nsu.msafonova1.blackjack;

/**
 * Представляет четыре масти стандартной колоды.
 */
public enum Suit {
    HEARTS("Червы"),
    DIAMONDS("Бубны"),
    CLUBS("Трефы"),
    SPADES("Пики");

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