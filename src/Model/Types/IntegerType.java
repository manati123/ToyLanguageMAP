package Model.Types;

import Model.Value.IValue;
import Model.Value.IntegerValue;

public class IntegerType implements IType{
    @Override
    public boolean equals(IType another){
        return another instanceof IntegerType;
    }

    @Override
    public IValue defaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public IType getInner() {
        return null;
    }

    @Override
    public String toString() { return "int";}
}
