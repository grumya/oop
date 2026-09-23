package ru.nsu.msafonova1.blackjack;

/**
 * Отвечает за вывод информации об игре в консоль.
 */
public class BlackjackView {

    /**
     * Выводит приветствие.
     */
    public void showWelcome() {
        System.out.println("Добро пожаловать в Блэкджек!");
    }

    /**
     * Выводит номер раунда.
     *
     * @param roundNumber номер раунда
     */
    public void showRound(int roundNumber) {
        System.out.println("Раунд " + roundNumber);
    }

    /**
     * Выводит карты игрока и их значение.
     *
     * @param player игрок
     */
    public void showPlayerCards(Player player) {
        System.out.println(
                "Ваши карты: "
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
                "Карты дилера: ["
                        + dealer.getHand().getCards().get(0)
                        + ", <закрытая карта>]"
        );
    }

    /**
     * Выводит все открытые карты дилера.
     *
     * @param dealer дилер
     */
    public void showDealerCards(Dealer dealer) {
        System.out.println(
                "Карты дилера: "
                        + dealer.getHand()
                        + " > "
                        + dealer.getHand().getValue()
        );
    }

    /**
     * Выводит сообщение о ходе игрока.
     */
    public void showPlayerTurn() {
        System.out.println("Ваш ход.");
        System.out.println("Введите 1, чтобы взять карту, или 0, чтобы остановиться.");
    }

    /**
     * Выводит сообщение о ходе дилера.
     */
    public void showDealerTurn() {
        System.out.println("Ход дилера.");
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