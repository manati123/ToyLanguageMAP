package Model.ADT;

import java.util.ArrayList;

public class List<T> implements  IList<T> {
    ArrayList<T> list;
    public List() {this.list = new ArrayList<>();}
    @Override
    public void add(T newItem) {
        this.list.add(newItem);
    }

    @Override
    public void empty() {
        this.list.clear();
    }

    @Override
    public String toString(){return list.toString();}
}
