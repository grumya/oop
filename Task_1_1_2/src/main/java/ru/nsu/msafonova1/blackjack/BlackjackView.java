package ru.nsu.msafonova1.blackjack;

/**
 * Отвечает за вывод информации об игре в консоль.
 */
public class BlackjackView {

    /**
     * Выводит приветствие.
     */
    public void showWelcome() {
        System.out.println("Welcome to Blackjack!");
    }

    /**
     * Выводит номер раунда.
     *
     * @param roundNumber номер раунда
     */
    public void showRound(int roundNumber) {
        System.out.println("Round " + roundNumber);
    }

    /**
     * Выводит карты игрока и их значение.
     *
     * @param player игрок
     */
    public void showPlayerCards(Player player) {
        System.out.println(
                "Your cards: "
                        + player.getHand()
                        + " > "
                        + player.getHand().getValue()
        );
    }

    /**
     * Выводит карты дилера, скрывая первую карту.
     *
     * @param dealer дилер
     */
    public void showDealerHiddenCards(Dealer dealer) {
        if (dealer.getHand().size() < 2) {
            return;
        }

        System.out.println(
                "Dealer's cards: ["
                        + dealer.getHand().getCards().get(0)
                        + ", <hidden card>]"
        );
    }

    /**
     * Выводит все открытые карты дилера.
     *
     * @param dealer дилер
     */
    public void showDealerCards(Dealer dealer) {
        System.out.println(
                "Dealer's cards: "
                        + dealer.getHand()
                        + " > "
                        + dealer.getHand().getValue()
        );
    }

    /**
     * Выводит сообщение о ходе игрока.
     */
    public void showPlayerTurn() {
        System.out.println("Your turn.");
        System.out.println("Type 1 to hit, or 0 to stand.");
    }

    /**
     * Выводит сообщение о ходе дилера.
     */
    public void showDealerTurn() {
        System.out.println("Dealer's turn.");
    }

    /**
     * Выводит результат раунда.
     *
     * @param message сообщение с результатом
     */
    public void showResult(String message) {
        System.out.println(message);
    }
}