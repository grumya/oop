package ru.nsu.msafonova1.blackjack;

/**
 * Представляет достоинства карт и их базовые значения.
 */
public enum Rank {
    TWO(2, "Двойка"),
    THREE(3, "Тройка"),
    FOUR(4, "Четверка"),
    FIVE(5, "Пятерка"),
    SIX(6, "Шестерка"),
    SEVEN(7, "Семерка"),
    EIGHT(8, "Восьмерка"),
    NINE(9, "Девятка"),
    TEN(10, "Десятка"),
    JACK(10, "Валет"),
    QUEEN(10, "Дама"),
    KING(10, "Король"),
    ACE(11, "Туз");

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