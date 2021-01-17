package ex3;

import java.util.ArrayList;

public class DataSetDemo {
    public static void main (String[] args) {
        ArrayList<BankAccount> list = new ArrayList<>();
        list.add(new BankAccount(15000, 2.3));
        list.add(new BankAccount(10000, 2.5));
        list.add(new BankAccount(20000, 1.5));

        // list.sort(Comparator.comparingDouble(BankAccount::getBalance));
        list.sort((o1, o2) -> Double.compare(o1.getBalance(), o2.getBalance()));

        System.out.println("Max. balance: " + list.get(list.size() - 1).getBalance());

        DataSet<BankAccount> data = new DataSet<>(item -> item.getBalance());

        for (BankAccount acc : list)
            data.add(acc);

        BankAccount max = data.getMax();
        System.out.println("Max. balance: " + max.getBalance());

        data = new DataSet<>(item -> item.getInterestRate());

        for (BankAccount acc : list)
            data.add(acc);

        // list.sort((Comparator.comparingDouble(BankAccount::getInterestRate)));
        list.sort(((o1, o2) -> Double.compare(o1.getInterestRate(), o2.getInterestRate())));

        System.out.println("Max. interest rate: " + list.get(list.size() - 1).getInterestRate());

        max = data.getMax();
        System.out.println("Max. interest rate: " + max.getInterestRate());
    }
}
