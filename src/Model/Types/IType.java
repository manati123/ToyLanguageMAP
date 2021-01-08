package Model.Types;

import Model.Value.IValue;

import java.io.Serializable;

public interface IType extends Serializable {
    boolean equals(IType other);
    IValue defaultValue();

    IType getInner();
}
