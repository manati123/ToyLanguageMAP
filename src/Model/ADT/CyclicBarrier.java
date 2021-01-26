package Model.ADT;

import java.util.HashMap;
import java.util.Map;

public class CyclicBarrier<K,V> implements ICyclicBarrier<K,V>{
    private Map<K,V> dict;

    public CyclicBarrier(){
        this.dict = new HashMap<>();
    }

    @Override
    public void add(K key, V value) {
        this.dict.put(key,value);
    }

    @Override
    public V get(K key) {
        return this.dict.get(key);
    }

    @Override
    public void update(K key, V value) {
        this.dict.put(key,value);
    }

    @Override
    public boolean contains(K key) {
        return this.dict.containsKey(key);
    }

    @Override
    public Iterable<K> getAll() {
        return this.dict.keySet();
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("\nBarrierTable:");
        for(Map.Entry<K,V> e : this.dict.entrySet()){
            s.append("\n");
            s.append(e.getKey());
            s.append("-->");
            s.append(e.getValue());
        }
        return s.toString();
    }
}
