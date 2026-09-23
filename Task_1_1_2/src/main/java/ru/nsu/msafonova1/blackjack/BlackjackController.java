package ru.nsu.msafonova1.blackjack;

/**
 * Управляет игроком, дилером и колодой.
 */
public class BlackjackController {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;

    /**
     * Создаёт новую игру.
     *
     * @param playerName имя игрока
     */
    public BlackjackController(String playerName) {
        deck = new Deck();
        player = new Player(playerName);
        dealer = new Dealer();
    }

    /**
     * Возвращает игрока.
     *
     * @return игрок
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Возвращает дилера.
     *
     * @return дилер
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Возвращает колоду.
     *
     * @return колода
     */
    public Deck getDeck() {
        return deck;
    }
}