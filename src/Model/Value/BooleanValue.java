package Model.Value;

import Model.Types.BooleanType;
import Model.Types.IType;

public class BooleanValue implements IValue{
    boolean val;

    public BooleanValue(boolean v){
        val=v;
    }

    public boolean getVal() {
        return val;
    }
    @Override
    public String toString() {
        return val + "";
    }

    @Override
    public IType getLocationType() {
        return null;
    }

    @Override
    public IType get_type() {
        return new BooleanType();
    }

    @Override
    public boolean equals(IValue other){
        BooleanValue v = (BooleanValue)other;
        return this.val == v.val;
    }


}
