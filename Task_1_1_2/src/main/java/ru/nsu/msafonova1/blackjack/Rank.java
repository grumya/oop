package ru.nsu.msafonova1.blackjack;

/**
 * Представляет достоинства карт и их базовые значения.
 */
public enum Rank {
    TWO(2, "Two"),
    THREE(3, "Three"),
    FOUR(4, "Four"),
    FIVE(5, "Five"),
    SIX(6, "Six"),
    SEVEN(7, "Seven"),
    EIGHT(8, "Eight"),
    NINE(9, "Nine"),
    TEN(10, "Ten"),
    JACK(10, "Jack"),
    QUEEN(10, "Queen"),
    KING(10, "King"),
    ACE(11, "Ace");

    private final int value;
    private final String name;

    Rank(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Возвращает базовое значение достоинства карты.
     *
     * @return значение карты
     */
    public int getValue() {
        return value;
    }

    /**
     * Возвращает название достоинства карты.
     *
     * @return название карты
     */
    public String getName() {
        return name;
    }
}