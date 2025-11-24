package model.adt;

import model.value.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<K,V> implements MyIHeap<K,V> {
    private Map<Integer, IValue> heap;
    private int freeLocation;


    public MyHeap() {
        this.heap = new HashMap<>();
        this.freeLocation = 1;
    }

    @Override
    public int add(IValue value) {
        this.heap.put(this.freeLocation, value);
        this.freeLocation++;
        return this.freeLocation - 1;
    }

    @Override
    public void update(int address, IValue value) {
        if (!this.isDefined(address))
            throw new RuntimeException("Address not found in heap");
        this.heap.put(address, value);
    }


    @Override
    public IValue lookup(int address)  {
        if (!this.isDefined(address))
            throw new RuntimeException("Address not found in heap");
        return this.heap.get(address);
    }

    @Override
    public boolean isDefined(int address) {
        return this.heap.get(address) != null;
    }

    @Override
    public void remove(int address)  {
        this.heap.remove(address);
    }

    @Override
    public String toString() {
        return this.heap.toString();
    }

    @Override
    public Map getContent() {
        return this.heap;
    }

    @Override
    public void setContent(Map newContent) {
        this.heap.clear();
        this.heap.putAll(newContent);
    }

}