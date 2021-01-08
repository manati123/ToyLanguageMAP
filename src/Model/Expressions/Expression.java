package Model.Expressions;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IType;
import Model.Value.IValue;

public interface Expression {
    IValue  eval(IDict<String,IValue> table, IHeap<Integer, IValue> heap) throws MyException;
    IType typecheck(Dict<String, IType> typeEnv) throws MyException;
}
