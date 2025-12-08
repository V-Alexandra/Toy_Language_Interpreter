package model.adt;


import exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {
    final private HashMap<T1, T2> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    @Override
    public void put(T1 key, T2 value) {
        dictionary.put(key, value);
    }

    @Override
    public boolean containsKey(T1 key) {
        return dictionary.containsKey(key);
    }

    @Override
    public T2 get(T1 key) {
        return dictionary.get(key);
    }

    @Override
    public Set<T1> keySet() {
        return dictionary.keySet();
    }

    @Override
    public void remove(T1 key) {
        dictionary.remove(key);
    }

    @Override
    public HashMap<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public T2 lookup(T1 key) throws KeyNotFoundException {
        if (!containsKey(key))
            throw new KeyNotFoundException();
        return dictionary.get(key);
    }

    @Override
    public void update(T1 key, T2 value) throws KeyNotFoundException {
        if (!containsKey(key))
            throw new KeyNotFoundException();
        dictionary.put(key, value);
    }

    @Override
    public MyIDictionary<T1, T2> clone() {
        MyIDictionary<T1, T2> newDict = new MyDictionary<>();
        for(T1 key : dictionary.keySet())
            newDict.update(key, dictionary.get(key));
        return newDict;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<T1, T2> entry : dictionary.entrySet()) {
            result.append(entry.getKey()).append("->").append(entry.getValue()).append("\n");
        }
        return result.toString().trim();
    }
}
