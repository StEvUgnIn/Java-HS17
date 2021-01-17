package ex3;

public class BankAccount implements Measurable, Comparable<BankAccount> {

    private double balance;
    private double interestRate;

    public BankAccount(double balance, double interestRate) {
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public double getBalance () {
        return balance;
    }

    public double getInterestRate () {
        return interestRate;
    }

    public double getMeasure () {
        return balance;
    }

    public int compareTo (BankAccount other) {
        return Double.compare(balance, other.balance);
    }

    public String toString () {
        return super.toString();
    }
}
