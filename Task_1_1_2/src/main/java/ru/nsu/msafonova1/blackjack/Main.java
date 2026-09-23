package ru.nsu.msafonova1.blackjack;

import java.util.Scanner;

/**
 * Точка входа в приложение.
 */
public class Main {

    /**
     * Запускает игру.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        BlackjackController controller =
                new BlackjackController("Игрок");

        BlackjackView view = new BlackjackView();
        Scanner scanner = new Scanner(System.in);

        Deck deck = controller.getDeck();
        Player player = controller.getPlayer();
        Dealer dealer = controller.getDealer();

        view.showWelcome();
        view.showRound(1);

        // Перемешиваем колоду.
        deck.shuffle();

        // Раздаём две карты игроку и две карты дилеру.
        player.addCard(deck.draw());
        dealer.addCard(deck.draw());
        player.addCard(deck.draw());
        dealer.addCard(deck.draw());

        System.out.println("Дилер раздал карты");

        view.showPlayerCards(player);
        view.showDealerHiddenCards(dealer);

        // Сначала проверяем блэкджек.
        if (player.getHand().isBlackjack()) {
            if (dealer.getHand().isBlackjack()) {
                view.showDealerCards(dealer);
                view.showResult("У обоих блэкджек. Ничья!");
            } else {
                view.showResult("У вас блэкджек! Вы победили!");
            }

            return;
        }

        if (dealer.getHand().isBlackjack()) {
            view.showDealerCards(dealer);
            view.showResult("У дилера блэкджек. Вы проиграли.");
            return;
        }

        // Ход игрока.
        boolean playerStopped = false;

        while (!playerStopped) {
            view.showPlayerTurn();

            int choice = scanner.nextInt();

            if (choice == 1) {
                player.addCard(deck.draw());

                view.showPlayerCards(player);

                if (player.getHand().isBust()) {
                    view.showResult("Вы набрали больше 21. Вы проиграли.");
                    return;
                }
            } else if (choice == 0) {
                playerStopped = true;
            } else {
                System.out.println("Введите 1 или 0.");
            }
        }

        // Ход дилера.
        view.showDealerTurn();

        view.showDealerCards(dealer);

        while (dealer.shouldDraw()) {
            dealer.addCard(deck.draw());
            view.showDealerCards(dealer);
        }

        // Проверяем перебор дилера.
        if (dealer.getHand().isBust()) {
            view.showResult("Дилер набрал больше 21. Вы победили!");
            return;
        }

        // Сравниваем значения рук.
        int playerValue = player.getHand().getValue();
        int dealerValue = dealer.getHand().getValue();

        if (playerValue > dealerValue) {
            view.showResult("Вы победили!");
        } else if (playerValue < dealerValue) {
            view.showResult("Дилер победил.");
        } else {
            view.showResult("Ничья!");
        }
    }
}