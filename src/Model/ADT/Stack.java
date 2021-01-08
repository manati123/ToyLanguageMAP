package Model.ADT;

import java.util.LinkedList;

public class Stack<T> implements IStack<T> {
    LinkedList<T> stack;

    public Stack(){this.stack = new LinkedList<>();}
    @Override
    public T pop() {
        return this.stack.pop();
    }

    @Override
    public void push(T newItem) {
        this.stack.push(newItem);
    }

    @Override
    public boolean empty() {
        return this.stack.isEmpty();
    }

    @Override
    public void clr() {
        this.stack.clear();
    }

    @Override
    public String toString(){
        return this.stack.toString();
    }
}
