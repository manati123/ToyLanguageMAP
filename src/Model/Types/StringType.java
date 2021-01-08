package Model.Types;

import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType {
    @Override
    public boolean equals(IType other) {
        return other instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public IType getInner() {
        return null;
    }

    @Override
    public String toString() { return "string";}
}
