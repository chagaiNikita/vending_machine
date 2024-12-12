package PaymentMethods;

public abstract class PaymentMethods {
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PaymentMethods(int amount) {
        this.amount = amount;
    }
}
