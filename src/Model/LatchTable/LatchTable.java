package Model.LatchTable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LatchTable<K,V> implements ILatchTable<K,V>{
    public HashMap<K,V> latch;
    public LatchTable(){
        this.latch = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        this.latch.put(key,value);
    }

    @Override
    public V get(K key) {
        return this.latch.get(key);
    }

    @Override
    public Collection<V> values() {
        return this.latch.values();
    }

    @Override
    public Collection<K> keys() {
        return this.latch.keySet();
    }

    @Override
    public void remove(K fd) {
        this.latch.remove(fd);
    }

    @Override
    public boolean contains(K key) {
        return this.latch.containsKey(key);
    }

    @Override
    public ILatchTable<K, V> clone() {
        ILatchTable newLatch = new LatchTable();
        for(K key : this.latch.keySet())
            newLatch.put(key,this.latch.get(key));

        return newLatch;
    }

    @Override
    public Map<K, V> toMap() {
        return this.latch;
    }

    @Override
    public String toString(){
        String returnValue = "";
        boolean k = false;
        for(HashMap.Entry<K,V> entry : this.latch.entrySet())
            if(k)
                returnValue += "\n";
            else{
                returnValue = returnValue + entry.getKey().toString() + " -> " + entry.getValue().toString();
                k = true;
            }
            return returnValue;
    }
}
