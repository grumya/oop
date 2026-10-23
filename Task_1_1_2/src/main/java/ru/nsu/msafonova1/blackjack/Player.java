package ru.nsu.msafonova1.blackjack;

/**
 * Представляет игрока в игре.
 */
public class Player {
    private final String name;
    private final Hand hand;

    /**
     * Создаёт игрока.
     *
     * @param name имя игрока
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    /**
     * Возвращает имя игрока.
     *
     * @return имя игрока
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает руку игрока.
     *
     * @return рука игрока
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Добавляет карту игроку.
     *
     * @param card добавляемая карта
     */
    public void addCard(Card card) {
        hand.addCard(card);
    }
}