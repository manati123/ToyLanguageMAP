package Model.ADT;

import java.util.Map;

public interface IHeap<T, T1> {
    void add(T key, T1 elem);
    T1 get(T key);
    void update(T key, T1 elem);
    void autoAdd(T1 elem);
    T getAddr();
    void setContent(Map<T, T1> heap);
    Map<T, T1> getContent();
    boolean isDefined(T addr);
}
