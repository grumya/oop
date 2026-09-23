package ru.nsu.msafonova1.blackjack;

/**
 * Представляет дилера в игре.
 */
public class Dealer {
    private final Hand hand;

    /**
     * Создаёт дилера с пустой рукой.
     */
    public Dealer() {
        hand = new Hand();
    }

    /**
     * Возвращает руку дилера.
     *
     * @return рука дилера
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Добавляет карту дилеру.
     *
     * @param card добавляемая карта
     */
    public void addCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Проверяет, должен ли дилер взять ещё одну карту.
     * По правилам дилер берёт карту, пока сумма меньше 17.
     *
     * @return true, если дилеру нужно взять карту
     */
    public boolean shouldDraw() {
        return hand.getValue() < 17;
    }
}