package PaymentMethods;

public class BankCard extends PaymentMethods {
    private String cardNumber;
    private int cardPassword;

    public BankCard(int amount, String cardNumber, int cardPassword) {
        super(amount);
        this.cardNumber = cardNumber;
        this.cardPassword = cardPassword;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCardPassword() {
        return cardPassword;
    }
}
