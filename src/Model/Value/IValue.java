package Model.Value;

import Model.Types.IType;

import java.io.Serializable;

public interface IValue extends Serializable {
    IType get_type();
    boolean equals(IValue other);
    String toString();
    public IType getLocationType();
  //  IValue getValue();
}
