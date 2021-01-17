package ex3;

// This a strategy pattern
public class BalanceMeasurer implements Measurer<BankAccount> {
    public double measure (BankAccount item) {
        return item.getBalance();
    }
}
