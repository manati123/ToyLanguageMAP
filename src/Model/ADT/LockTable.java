package Model.ADT;

import java.util.HashMap;

public class LockTable<TElem, KValue> implements ILockTable<TElem, KValue>{
    HashMap<TElem, KValue> dict;
    private AddressBuilder address;

    public LockTable(){
        dict = new HashMap<>();
        address = new AddressBuilder();
    }

    @Override
    public Integer getLockAddress(){
        return this.address.getFreeAddress();
    }

    @Override
    public void put(TElem location, KValue value) {
        dict.put(location, value);
    }

    @Override
    public boolean isDefined(TElem key) {
        return dict.containsKey(key);
    }

    @Override
    public KValue get(TElem key) {
        return dict.get(key);
    }

    @Override
    public String toString(){
        String to_be_returned = "";
        for(TElem k: dict.keySet()) {
            to_be_returned += "(" + k.toString() + "  " + dict.get(k) +")";
        }
        return to_be_returned;
    }


}