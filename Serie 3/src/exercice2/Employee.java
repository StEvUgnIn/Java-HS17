package exercice2;

import com.sun.istack.internal.NotNull;
import com.sun.media.jfxmediaimpl.MediaDisposer.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A person has a name and an age
 */
abstract class Person implements Disposable {
    private String name;
    private int age;

    protected Person(@NotNull String name, int age) {
        this.name = name;
        this.age  = age;
    }

    @Override
    public void dispose () {
        name = null;
        age  = 0;
    }

    public String getName () {
        return name;
    }

    public int getAge () {
        return age;
    }
}

/**
 * An employee is a person who works in exchange of a salary
 * Entry date of any employee is stocked in a database
 */
public abstract class Employee extends Person implements Disposable, Cloneable {
    private LocalDate entryDate;
    private double salary;

    /**
     * Constructor
     * @param name name of this employee
     * @param age age of this employee
     * @param entryDate entry date in the company
     * @param salary relative salary of this employee
     */
    public Employee (@NotNull String name, int age, LocalDate entryDate, double salary) {
        super(name, age = Math.max(age, 16));
        this.entryDate = entryDate;
        this.salary = salary;
    }

    /**
     * An employee does a specific job
     */
    public abstract void work();

    /**
     * An employee can dismiss from the company
     */
    public void dismiss() {
        if (Math.round(Math.random() % 7.0) == 0)
            this.dispose();
    }

    /**
     * An employee can be fired and deleted from database
     */
    @Override
    public void dispose () {
        entryDate = null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", " + getAge() + "\nEmployee since: " + entryDate;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public double getSalary() {
        return salary;
    }
}

final class Manager<T extends Employee> extends Employee implements ResourceDisposer {
    Company team;

    /**
     * Constuctor
     * @param name
     * @param age
     * @param entryDate
     * @param minSalary
     * @param team
     */
    public Manager(String name, int age, LocalDate entryDate, double minSalary, Company team) {
        super(name, age, entryDate, minSalary);
        this.team = team;
    }

    public Manager(Employee employee, Company team) {
        this(employee.getName(), employee.getAge(), employee.getEntryDate(), employee.getSalary(), team);
    }

    Manager(int age, LocalDate entryDate, double minSalary, Company team) {
        this(null, age, entryDate, minSalary, team);
    }

    private void manageWorkers(ArrayList<Worker> workersList) {
        for (ListIterator<Worker> worker = workersList.listIterator(); worker.hasNext(); )
            if (worker.next().getHours() < 120)
                fire(worker);
    }

    private void manageVendors(ArrayList vendorsList) {
        for (ListIterator<Vendor> vendor = vendorsList.listIterator(); vendor.hasNext(); )
            if (vendor.next().getSales() < 5)
                fire(vendor);
    }

    @Override
    /**
     * Manager must fire low performance coworkers
     */
    public void work () {
        manageWorkers(team.getWorkers());
        manageVendors(team.getVendors());
        manageVendors(team.getTrainees());
    }

    /**
     * An manager can be asked to put an end to the contract of some employee
     * @param employee
     */
    private void fire(ListIterator employee) {
        employee.set(null);
    }

    /**
     *
     * @param employee
     */
    public void disposeResource (T employee) {
        employee.dispose();
    }

    public void disposeResource (Object resource) {
        if (resource instanceof Disposable)
            ((Disposable)resource).dispose(); // Only Object implementing Disposable can be disposed
        else resource = null; // others must be simply deleted by garbage collection
    }

    @Override
    protected Object clone () {
        return new Manager(this, team);
    }

    @Override
    public String toString () {
        return super.toString() + "\nMinimum salary: " + getSalary();
    }

    public Company getTeam () {
        return team;
    }
}

class Vendor extends Employee {
    protected double commission;
    private int sales;

    public Vendor(String name, int age, LocalDate entryDate, double salary) {
        super(name, age, entryDate, salary);
        commission  = 200.0;
        sales = 0;  // at the end of month sales go back to 0
    }

    Vendor(int age, LocalDate entryDate, double salary) {
        this(null, age, entryDate, salary);
    }

    public Vendor(Trainee trainee) {
        this(trainee.getName(), trainee.getAge(), trainee.getEntryDate(), trainee.getSalary());
    }

    @Override
    public void work () {
        sales = Math.toIntExact(Math.round(Math.random() * 50.0));
    }

    @Override
    protected Object clone () {
        return new Vendor(getName(), getAge(), getEntryDate(), getSalary());
    }

    @Override
    public String toString () {
        return super.toString() + "\nSalary: " + (double)sales*getSalary()
                + "\nCommision: " + getCommission();
    }

    public double getCommission () {
        return commission;
    }

    public int getSales () {
        return sales;
    }
}

final class Trainee extends Vendor {
    public Trainee(String name, int age, LocalDate entryDate, double salary) {
        super(name, age, entryDate, salary);
        commission = 0;
    }

    Trainee(int age, LocalDate entryDate, double salary) {
        this(null, age, entryDate, salary);
    }

    @Override
    protected Object clone () {
        return new Trainee(getName(), getAge(), getEntryDate(), getSalary());
    }

    public void setCommission(double commision) {
        this.commission = commision;
    }
}

final class Worker extends Employee {
    private Type type;
    private int hours;

    public Worker (String name, int age, LocalDate entryDate, Type type, double salary) {
        super(name, age, entryDate, salary);
        this.type = type;
        hours = 0;
    }

    Worker(int age, LocalDate entryDate, Type type, double salary) {
        this(null, age, entryDate, type, salary);
    }

    public void addHours(int workedHours) {
        this.hours += workedHours;
    }

    @Override
    public void work () {
        addHours(Math.toIntExact(Math.round(Math.random() * 200.0)));
    }

    @Override
    protected Object clone () {
        return new Worker(getName(), getAge(), getEntryDate(), getType(), getSalary());
    }

    @Override
    public String toString () {
        String ret = null;
        if (getType().equals(Type.HOUR))
            ret = super.toString() + "\nWage: " + hours*getSalary();
        else if (getType().equals(Type.SALARY))
            ret = super.toString() + "\nSalary: " + getSalary();
        return ret;
    }

    public int getHours () {
        return hours;
    }

    public Type getType () {
        return type;
    }

    /**
     * Workers get either wage or salary
     */
    enum Type {
        SALARY,
        HOUR
    }
}