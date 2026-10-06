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
                new BlackjackController("Player");

        BlackjackView view = new BlackjackView();
        Scanner scanner = new Scanner(System.in);

        Deck deck = controller.getDeck();
        Player player = controller.getPlayer();
        Dealer dealer = controller.getDealer();

        view.showWelcome();

        boolean playAgain = true;
        int roundNumber = 1;

        while (playAgain) {
            view.showRound(roundNumber);

            // Сбрасываем карты у игрока и дилера перед новым раундом
            player.getHand().clear();
            dealer.getHand().clear();

            // Если в колоде мало карт, обновляем её
            if (deck.size() < 15) {
                deck = new Deck();
            }
            deck.shuffle();

            // Раздаём две карты игроку и две карты дилеру
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());

            System.out.println("Dealer dealt the cards");

            view.showPlayerCards(player);
            view.showDealerHiddenCards(dealer);

            boolean roundFinished = false;

            // Проверяем блэкджек в начале раунда
            if (player.getHand().isBlackjack()) {
                if (dealer.getHand().isBlackjack()) {
                    view.showDealerCards(dealer);
                    view.showResult("Both have Blackjack. It's a tie!");
                } else {
                    view.showResult("You have Blackjack! You win!");
                }
                roundFinished = true;
            } else if (dealer.getHand().isBlackjack()) {
                view.showDealerCards(dealer);
                view.showResult("Dealer has Blackjack. You lose.");
                roundFinished = true;
            }

            // Ход игрока
            if (!roundFinished) {
                boolean playerStopped = false;

                while (!playerStopped) {
                    view.showPlayerTurn();

                    int choice = scanner.nextInt();

                    if (choice == 1) {
                        player.addCard(deck.draw());
                        view.showPlayerCards(player);

                        if (player.getHand().isBust()) {
                            view.showResult("Bust! You score over 21. You lose.");
                            roundFinished = true;
                            playerStopped = true;
                        }
                    } else if (choice == 0) {
                        playerStopped = true;
                    } else {
                        System.out.println("Please enter 1 or 0.");
                    }
                }
            }

            // Ход дилера (если раунд ещё не завершён)
            if (!roundFinished) {
                view.showDealerTurn();
                view.showDealerCards(dealer);

                while (dealer.shouldDraw()) {
                    dealer.addCard(deck.draw());
                    view.showDealerCards(dealer);
                }

                // Проверяем результат
                if (dealer.getHand().isBust()) {
                    view.showResult("Dealer busts! You win!");
                } else {
                    int playerValue = player.getHand().getValue();
                    int dealerValue = dealer.getHand().getValue();

                    if (playerValue > dealerValue) {
                        view.showResult("You win!");
                    } else if (playerValue < dealerValue) {
                        view.showResult("Dealer wins!");
                    } else {
                        view.showResult("It's a tie!");
                    }
                }
            }

            // Спрашиваем, хочет ли игрок продолжить
            System.out.println("Play another round? (1 - Yes, 0 - No):");
            int againChoice = scanner.nextInt();

            if (againChoice == 1) {
                roundNumber++;
            } else {
                playAgain = false;
            }
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }
}