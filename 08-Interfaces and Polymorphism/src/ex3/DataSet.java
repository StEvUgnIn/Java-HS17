package ex3;

public class DataSet<E> {
    private E max;
    private Measurer<E> meas;

    public DataSet(Measurer<E> m) {
        meas = m;
    }

    public void add(E x) {
        if (max == null || meas.measure(x) > meas.measure(max)) max = x;
    }

    public E getMax () {
        return max;
    }
}
