package Model.ADT;

public class AddressBuilder {
    private Integer address = 1;
    private static IStack<Integer> freeAddress = new Stack<>();

    public Integer getFreeAddress() {
        return freeAddress.empty() ? this.address++ : freeAddress.pop();
    }
}
