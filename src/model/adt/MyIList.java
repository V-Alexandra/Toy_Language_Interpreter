package model.adt;


public interface MyIList<T> {
    int size();

    boolean isEmpty();

    void add(T e);

    T get(int index);

    String toString();

}
