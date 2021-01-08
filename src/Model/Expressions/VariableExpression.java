package Model.Expressions;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IType;
import Model.Value.IValue;

public class VariableExpression implements Expression{
    String ID;

    public VariableExpression(String ID) {
        this.ID = ID;
    }

    @Override
    public IValue eval(IDict<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        return table.get(this.ID);
    }

    @Override
    public IType typecheck(Dict<String, IType> typeEnv) throws MyException {
        if(typeEnv != null)
            return typeEnv.get(this.ID);
        else
            return null;//ALERT
    }

    @Override
    public String toString(){return this.ID;}
}
