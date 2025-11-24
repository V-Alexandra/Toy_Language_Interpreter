package model.adt;

import model.value.IValue;

import java.util.Map;
import java.util.Set;

public interface MyIHeap<K, V> {
    int add(IValue value);
    void update(int address, IValue value);
    IValue lookup(int address);
    boolean isDefined(int address);
    void remove(int address);
    String toString();
    Map getContent();
    void setContent(Map newContent);
}