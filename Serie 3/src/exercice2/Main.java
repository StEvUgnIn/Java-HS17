package exercice2;

import java.util.ArrayList;

public class Main {
    public static void main (String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        Company company = Company.getInstance("Apple");
        company.hire(50);

        for (Employee employee : company.getEmployees()) {
            System.out.println(employee);
            System.out.println();
        }

        System.out.println("Pay for everyone: " + company.payEveryOne());
    }
}
