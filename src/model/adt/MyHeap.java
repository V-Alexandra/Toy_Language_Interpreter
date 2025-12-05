package model.adt;

import exceptions.VariableNotDefinedException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    private final Map<Integer, IValue> heap;
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
            throw new VariableNotDefinedException();
        this.heap.put(address, value);
    }


    @Override
    public IValue lookup(int address) {
        if (!this.isDefined(address))
            throw new VariableNotDefinedException();
        return this.heap.get(address);
    }

    @Override
    public boolean isDefined(int address) {
        return this.heap.get(address) != null;
    }

    @Override
    public void remove(int address) {
        this.heap.remove(address);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, IValue> entry : heap.entrySet()) {
            result.append(entry.getKey()).append("->").append(entry.getValue()).append("\n");
        }
        return result.toString().trim();
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