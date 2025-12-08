package model.adt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    final Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() {
        return this.stack.pop();
    }

    @Override
    public void push(T elem) {
        this.stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public String toString() {
        String str = "\n";
        for (int i = stack.size() - 1; i >= 0; i--)
            str += stack.get(i)
                    .toString() + "\n";
        return str;
    }
}

