package Model.Types;

import Model.Value.IValue;
import Model.Value.RefValue;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RefType implements IType{
    IType inner;
    public RefType(IType inner){this.inner = inner;}
    @Override
    public IType getInner(){
        /*//ArrayList<IType> typeList = new ArrayList<IType>();
        var currentInner = this.inner;
        if(this.inner.getInner() != null)
            while(currentInner.getInner().getInner() != null)
                currentInner = currentInner.getInner();
        else
            return this.inner;
        return currentInner;*/
        return this.inner;
    }
    @Override
    public boolean equals(IType other) {
        if(other instanceof RefType)
            return inner.equals(other.getInner());
        else return false;

    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0,this.inner);
    }
    @Override
    public String toString() {return "ref " + this.inner.toString();}
}
