package Model.Value;

import Model.Types.IType;
import Model.Types.RefType;

public class RefValue implements IValue{
    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    int address;
    IType locationType;
    @Override
    public IType get_type() {
        return new RefType(locationType);
    }
    public IType getLocationType(){return this.locationType;}

    @Override
    public boolean equals(IValue other) {
        RefValue v = (RefValue)other;
        return this.address == v.address;
    }

    public int getAddr() {return address;}

    @Override
    public String toString() {
        return "(" + this.address + ", " + this.locationType + ")";
    }
}
