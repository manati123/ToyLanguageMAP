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
        if(this.expression.eval(table,heap).get_type() instanceof RefType){
            RefValue refValue = (RefValue) this.expression.eval(table,heap);
            int address = refValue.getAddr();
            System.out.println(address);
            if(!heap.isDefined(address))
                throw new MyException("Undefined address! -> line 24 ReadHeapExpression");
            return heap.get(address);
        }
        else
            throw new MyException("Not ref type! -> line 26 ReadHeapExpression");
        /*try{
            Object obj = new Object();
            obj = this.expression.eval(table,heap);
            System.out.println(obj.getClass());
        return heap.get(((RefValue) this.expression.eval(table,heap)).getAddr());
        }catch(ClassCastException exc){
            throw new MyException(exc.getMessage());
        }*/
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
