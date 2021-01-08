package Model.Value;

import Model.Types.IType;
import Model.Types.IntegerType;

public class IntegerValue implements IValue {
    int val;

    public IntegerValue(int _val){this.val=_val;}
    @Override
    public IType get_type(){return new IntegerType();
    }

    @Override
    public boolean equals(IValue other){
        IntegerValue v = (IntegerValue)other;
        return this.val == v.val;
    }

    @Override
    public String toString(){
        return this.val + "";
    }

    @Override
    public IType getLocationType() {
        return null;
    }


    public int getValue() {
        return this.val;
    }
}
