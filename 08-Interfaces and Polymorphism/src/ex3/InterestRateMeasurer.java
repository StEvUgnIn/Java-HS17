package ex3;

public class InterestRateMeasurer implements Measurer<BankAccount> {
    public double measure (BankAccount item) {
        return item.getInterestRate();
    }
}
