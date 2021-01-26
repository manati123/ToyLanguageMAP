package Model.CyclicBarrier;

public class Pair<K,V> {
    private K first;
    private V second;

    public Pair(K F, V S){
        this.first = F;
        this.second = S;
    }

    public K getFirst(){
        return this.first;
    }

    public V getSecond(){
        return this.second;
    }

    public void setFirst(K _first){
        this.first = _first;
    }

    public void setSecond(V _second){
        this.second = _second;
    }

    @Override
    public String toString(){
        return first.toString() + " " + second.toString();
    }

}
