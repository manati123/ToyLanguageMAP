package Model.ADT;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
* @param <K> type of the key
* @param <T> the type of the  item
*
* */

public interface IDict<K,T> {

    void setContent(HashMap<K,T> deppCop);
    void add(K key, T val);
    T get(K keyValue);
    boolean isDefined(K keyValue);
    void update(K updateKey, T updateValue);
    void remove(K removeKey);
    ArrayList<ArrayList<String>> getElementsStrings();
    void clr();
    Map<K,T> getAll();
   // Dict<K,T> clone();

}
