package dev.prototype.blackjack;

/**
 * Blackjack scoring model. Card events will be connected after map-art recognition
 * and a user-confirmed card-value mapping are implemented.
 */
public final class GameTracker {
    private int playerScore;
    private int dealerScore;
    private int cardsSeen;

    public int playerScore() { return playerScore; }
    public int dealerScore() { return dealerScore; }
    public int cardsSeen() { return cardsSeen; }

    public void recordPlayerCard(int value) {
        playerScore = addCard(playerScore, value);
        cardsSeen++;
    }

    public void recordDealerCard(int value) {
        dealerScore = addCard(dealerScore, value);
        cardsSeen++;
    }

    public void reset() {
        playerScore = 0;
        dealerScore = 0;
        cardsSeen = 0;
    }

    private int addCard(int score, int value) {
        if (value < 1 || value > 11) throw new IllegalArgumentException("Card value must be 1..11");
        return score + value;
    }
}
