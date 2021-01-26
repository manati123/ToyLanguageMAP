package Model.ADT;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dict<K,T> implements IDict<K,T>, Serializable {
    HashMap<K,T> dict;

    public Dict(){this.dict = new HashMap<>();}

    @Override
    public void setContent(HashMap<K, T> deppCop) {
        this.dict = deppCop;
    }

    @Override
    public synchronized void add(K key, T val) {
        this.dict.put(key, val);
    }

    @Override
    public T get(K keyValue) {
        return this.dict.get(keyValue);
    }

    @Override
    public synchronized boolean isDefined(K keyValue) {
        return this.dict.containsKey(keyValue);
    }

    @Override
    public synchronized void update(K updateKey, T updateValue) {
        this.dict.replace(updateKey, updateValue);
    }

    @Override
    public synchronized void remove(K removeKey) {
        this.dict.remove(removeKey);
    }

    @Override
    public ArrayList<ArrayList<String>> getElementsStrings() {
        ArrayList<ArrayList<String>> elements = new ArrayList<>();
        for (K key : this.dict.keySet()){
            ArrayList<String> list = new ArrayList<>();
            list.add(key.toString());
            list.add(dict.get(key).toString());
            elements.add(list);
        }
        return elements;
    }

    @Override
    public synchronized void clr() {
        this.dict.clear();
    }

    @Override
    public synchronized Map<K, T> getAll() {
        return this.dict;
    }

   /* @Override
    public Dict clone() {
        return new Dict<K,T>(this.dict);
    }*/

    @Override
    public String toString(){return this.dict.toString();}


    public synchronized IDict<K, T> deepClone() {
        IDict<K, T> newDictionary = new Dict<>();
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
            outputStrm.writeObject(this.dict);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            newDictionary.setContent(this.dict);

        } catch (IOException  ex){
            System.out.println(ex);
        }
        return newDictionary;
    }
}
//ConcurrentHashMap, ConcurrentLinkedDoubleEndedQueue