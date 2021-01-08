package Model.Value;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements IValue{
    String val;
    public StringValue(String _v){this.val = _v;}
    @Override
    public IType get_type() {
        return new StringType();
    }

    @Override
    public boolean equals(IValue other) {
        StringValue v = (StringValue)other;
        return this.val == v.val;
    }

    @Override
    public String toString(){
        return this.val + " ";
    }

    @Override
    public IType getLocationType() {
        return null;
    }

    public String getValue() {
        return this.val;
    }
}
