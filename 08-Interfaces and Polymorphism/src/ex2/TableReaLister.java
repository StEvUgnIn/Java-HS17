package ex2;

import java.util.ArrayList;

public class TableReaLister {

    public static void main (String[] args) {
        ArrayList<Table> list = new ArrayList<>();
        list.add(new CircularTable(0.75));
        list.add(new RectangularTable(3.0, 1.0));
        list.add(new RectangularTable(1.0, 0.75));
        list.add(new CircularTable(1.23));
        list.add(new RectangularTable(2.25, 1.0));

        for (Table t : list) {
            System.out.println(t + "; Area: " + t.getArea());
        }
    }
}
