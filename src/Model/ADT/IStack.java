package Model.ADT;

public interface IStack<T> {

    T pop();
    void push(T newItem);
    boolean empty();

    void clr();
}
