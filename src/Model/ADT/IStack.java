package Model.ADT;

import java.util.Collection;

public interface IStack<T> {

    T pop();
    void push(T newItem);
    boolean empty();

    void clr();

    Collection<? extends String> getElementsStrings();
}
