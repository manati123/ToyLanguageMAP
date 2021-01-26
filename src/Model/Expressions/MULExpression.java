package Model.Expressions;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Statements.CompStatement;
import Model.Statements.IStatement;
import Model.Types.BooleanType;
import Model.Types.IType;
import Model.Types.IntegerType;
import Model.Value.IValue;
import Model.Value.IntegerValue;

public class MULExpression implements Expression{
    Expression exp1,exp2;

    public MULExpression(Expression exp1, Expression exp2){
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public IValue eval(IDict<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        IValue val1,val2;
        val1 = this.exp1.eval(table,heap);
        if(!val1.get_type().equals(new IntegerType()))
            throw new MyException("First value is not integer type");
        val2 = this.exp2.eval(table,heap);
        if(!val2.get_type().equals(new IntegerType()))
            throw new MyException("Second value is not integer type");

        //IStatement s = new CompStatement(new ValueExpression())


        return null;
    }

    @Override
    public IType typecheck(Dict<String, IType> typeEnv) throws MyException {
        return null;
    }

    @Override
    public String toString(){
        return "MUL("+ exp1.toString() + "," + exp2.toString() + ")";
    }
}
