package Model.Expressions;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class ReadHeapExpression implements Expression{
    final Expression expression;

    public ReadHeapExpression(Expression _expression){
        this.expression = _expression;
    }


    @Override
    public IValue eval(IDict<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        IValue value = expression.eval(table, heap);

        if(!(value.get_type() instanceof RefType))
            throw new MyException(value + " is not of Reference type");

        int address = ((RefValue)value).getAddr();

        if(!heap.isDefined(address))
            throw new MyException(value + " is not defined in the heap");

        return heap.get(address);
    }

    @Override
    public IType typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType t = this.expression.typecheck(typeEnv);
        if(t instanceof RefType){
            RefType reft = (RefType) t;
            return reft.getInner();
        }
        else
            throw new MyException("The Read Heap argument is not a Ref Type!");
    }

    @Override
    public String toString(){
        return "readHeap(" + expression + ")";
    }
}
