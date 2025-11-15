package model.adt;

import exceptions.ADTExceptions;
import exceptions.KeyNotFoundException;


import java.util.HashMap;
import java.util.Set;

public interface MyIDictionary<T1, T2> {
    void put(T1 key, T2 value);

    boolean containsKey(T1 key);

    T2 get(T1 key);

    Set<T1> keySet();

    void remove(T1 key);

    HashMap<T1, T2> getContent();

    T2 lookup(T1 key) throws KeyNotFoundException;

    void update(T1 key, T2 value) throws KeyNotFoundException;
}
