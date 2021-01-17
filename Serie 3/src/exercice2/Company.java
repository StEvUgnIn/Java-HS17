package exercice2;

import com.sun.istack.internal.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

interface ICompany {
    ArrayList<Employee> getEmployees();
    ArrayList<Manager> getManagers();
    ArrayList<Vendor> getVendors();
    ArrayList<Trainee> getTrainees();
    ArrayList<Worker> getWorkers();
    double getValue ();
    double pay(Employee employee);
    double payEveryOne();
    void hire(int number);
    void work();
}

/**
 * A company is a singleton
 */
public final class Company implements ICompany {
    private static Company instance = null;
    private LocalDate foundingDate;
    private String name;
    private ArrayList<Employee> employees;
    private double value;

    /**
     * Constructor
     * @param name name of this company
     * @param employees workforce of this company
     * @param value evolving value of this company
     */
    private Company(@NotNull String name, @NotNull ArrayList<Employee> employees, double value) {
        this.name = name;
        this.employees = employees;
        this.value = value;
        this.foundingDate = LocalDate.ofEpochDay(0); // In reality, we would use LocalDate.now()
    }

    private Company(@NotNull String name, ArrayList<Employee> employees) {
        this(name, employees, 0.00);
    }

    private Company(@NotNull String name) {
        this(name, null);
    }

    /**
     * singleton access method
     * @param name name of this company
     * @param employees employees of this company
     * @param value value of this company
     * @return single instance of class company
     */
    public static Company getInstance (String name, ArrayList<Employee> employees, double value) {
        if (instance == null) instance = new Company(name, employees, value);
        return instance;
    }

    public static Company getInstance (String name, ArrayList<Employee> employees) {
        return getInstance(name, employees, 0);
    }

    public static Company getInstance (String name) {
        return getInstance(name, new ArrayList<>());
    }

    public static Company getInstance () {
        return getInstance(null);
    }

    /**
     * Conversion from years into milliseconds
     * @param years number of years is a long integer
     * @return number of milliseconds equivalents in a long
     */
    private long yearsToMilliseconds(long years) {
        return years * 365L * 24L * 3600L * 1000L;
    }

    public void hire(int number) {
        for (int i = 0; i < number; i++) {
            double value = Math.random();
            int age = Math.toIntExact(Math.round(value * 60.0));
            switch (i % 5) {
                case 0:
                    employees.add(new Manager(age, getCurrentDate(), 5000.0, instance));
                    break;
                case 1:
                    employees.add(new Trainee(age, getCurrentDate(), 3500.0));
                case 2:
                    employees.add(new Vendor(age, getCurrentDate(), 3500.0));
                case 3:
                    employees.add(new Worker(age, getCurrentDate(), Worker.Type.SALARY, 15.0));
                case 4:
                    employees.add(new Worker(age, getCurrentDate(), Worker.Type.HOUR, 2000.0));
            }
        }
    }

    public void work() {
        for (Employee employee : employees)
            employee.work();
    }

    /**
     * This company pays an employee
     * @param employee employee getting pay
     * @return amount of pay which is insurance premium substracted from
     */
    public double pay(@NotNull Employee employee) {
        double salary = 0;
        double insurancePremium = 200.0;

        if (Objects.isNull(employee)) {
            /* Calculate insurance premium */
            /*
             * For all employees (except trainees and some workers), 50% of the insurance premium is
             * withdrawn from the salary
             */
            for (int i = 1; employee.getEntryDate().isAfter(getCurrentDate().plusYears(i)); ++i)
                insurancePremium += i * 10.0;

            if (employee instanceof Manager) {
                /* deposit money from value */
                return Math.max(employee.getSalary(), Math.random() * getValue());
            } else if (employee instanceof Vendor) { // trainees are also vendors
                Vendor vendor = (Vendor)employee;

                /*
                    After one year in the company, they receive only 20% ($40) thereof
                    pro item sold, after two years, 40 % ($80) , ..., and after five years, 100% ($200).
                 */
                if (vendor instanceof Trainee) {
                    Trainee trainee = (Trainee)employee;
                    long y;
                    for (y = 1L; y <= 5L && getCurrentDate().isAfter(trainee.getEntryDate().plus(y, ChronoUnit.YEARS)); ++y)
                        trainee.setCommission(trainee.getCommission() + 20.0 / 100.0);
                    /* trainee becomes a vendor */
                    if (y == 5L) employee = new Vendor(trainee);
                }

                salary = vendor.getSalary() + vendor.getCommission() * vendor.getSales();
            } else if (employee instanceof Worker) {
                final int MAX_HOURS = 170;
                final double PRIME = 100.0;
                Worker worker = (Worker)employee;

                if (worker.getType().equals(Worker.Type.HOUR)) {
                    salary = worker.getHours() * worker.getSalary();
                    // excess hours get paid half a time more
                    if (worker.getHours() > MAX_HOURS) {
                        salary += (worker.getHours() - MAX_HOURS) * worker.getSalary() / 2;
                    }
                } else if (worker.getType().equals(Worker.Type.SALARY)) {
                    salary = worker.getSalary();

                    /* You must have been a worker for 15 years to get a prime each month with a salary */
                    LocalDate date = worker.getEntryDate().plusYears(15L);

                    if (getCurrentDate().isAfter(date))
                        salary += PRIME;
                    /* insurance premium of workers does not excess 300$ */
                    if (insurancePremium > 300.0)
                        insurancePremium = 300.0;
                }
            }
        }

        /* substract 50% insurance from salary */
        salary -= insurancePremium / 2.0;

        /* take salary and 50% insurance premium from company value */
        value -= salary + insurancePremium / 2.0;

        /* insurance premium is paid, salary is returned */
        return salary;
    }

    /**
     * Pay any listed employee from this company
     * @return sum of salaries
     */
    public double payEveryOne () {
        double sum = 0;
        for (Employee employee : employees)
            sum += pay(employee);

        return sum;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    @Override
    public ArrayList<Manager> getManagers () {
        ArrayList<Manager> managers = new ArrayList<>();
        for (Employee employee : employees)
            if (employee instanceof Manager)
                managers.add((Manager)employee);
        return managers;
    }

    @Override
    public ArrayList<Vendor> getVendors () {
        ArrayList<Vendor> vendors = new ArrayList<>();
        for (Employee employee : employees)
            if (employee.getClass() == Vendor.class)
                vendors.add((Vendor)employee);
        return vendors;
    }

    @Override
    public ArrayList<Trainee> getTrainees () {
        ArrayList<Trainee> trainees = new ArrayList<>();
        for (Employee employee : employees)
            if (employee instanceof Trainee)
                trainees.add((Trainee)employee);
        return trainees;
    }

    @Override
    public ArrayList<Worker> getWorkers () {
        ArrayList<Worker> workers = new ArrayList<>();
        for (Employee employee : employees)
            if (employee instanceof Worker)
                workers.add((Worker)employee);
        return workers;
    }

    /**
     * Evaluate value of this company now
     * @return evaluated value of this company
     */
    public double getValue () {
        // fluctuation of value
        return value += value * (Math.random() - 0.5);
    }

    /**
     * Return current date in a LocalDate
     * @return current date
     */
    public static LocalDate getCurrentDate () {
        // ~ 3min = 20 years
        LocalDate now = instance.foundingDate.plus(System.currentTimeMillis() / 2000000000L, ChronoUnit.DAYS);
        return now;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
}
