package Model.Expressions;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.BooleanType;
import Model.Types.IType;
import Model.Types.IntegerType;
import Model.Value.BooleanValue;
import Model.Value.IValue;

public class LogicExpression implements Expression {
    Expression e1,e2;
    int operator;

    public LogicExpression(Expression e1, Expression e2, int operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public IValue eval(IDict<String, IValue> table, IHeap<Integer, IValue> heap) throws MyException {
        IValue val1, val2;
        val1 = this.e1.eval(table,heap);
        if(this.operator != 2 && this.operator != 1)
            throw new MyException("Invalid OP");
        if(!val1.get_type().equals(new BooleanType()))
            throw new MyException("First value is not booleanType");
        val2 = this.e1.eval(table,heap);
        if(!val2.get_type().equals(new BooleanType()))
            throw new MyException("Second value is not booleanType");
        BooleanValue bool1 = (BooleanValue) val1;
        BooleanValue bool2 = (BooleanValue) val2;
        boolean first, second;
        first = bool1.getVal();
        second = bool2.getVal();
        return switch (this.operator) {
            case 1 -> new BooleanValue(first && second);
            case 2 -> new BooleanValue(first || second);
            default -> throw new MyException("Bad operator");
        };

    }

    @Override
    public IType typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType t1, t2;
        t1 = this.e1.typecheck(typeEnv);
        t2 = this.e2.typecheck(typeEnv);
        if(t1.equals(new BooleanType())){
            if(t2.equals(new BooleanType()))
                return new BooleanType();
            else throw new MyException("Secoond op is not a boolean");
        }
        else throw new MyException("First op is not a boolean");
    }


}
