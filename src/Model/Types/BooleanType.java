package Model.Types;

import Model.Value.BooleanValue;
import Model.Value.IValue;

public class BooleanType implements IType {
    @Override
    public boolean equals(IType another){
        return another instanceof BooleanType;
    }

    @Override
    public IValue defaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public IType getInner() {
        return null;
    }

    @Override
    public String toString() { return "boolean";}
}
