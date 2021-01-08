package Model.Expressions;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IType;
import Model.Value.IValue;

public class ValueExpression implements Expression {
    IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        return value;
    }

    @Override
    public IType typecheck(Dict<String, IType> typeEnv) throws MyException {
        return this.value.get_type();
    }

    @Override
    public String toString(){
        return this.value.toString();
    }
}
