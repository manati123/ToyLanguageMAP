package Model.Expressions;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IType;
import Model.Types.IntegerType;
import Model.Value.IValue;
import Model.Value.IntegerValue;

public class ArithmeticExpression implements Expression {
    Expression e1, e2;
    int operator;

    public ArithmeticExpression(Expression e1, Expression e2, int operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public IValue eval(IDict<String, IValue> table,  IHeap<Integer, IValue> heap) throws MyException {
        IValue v1,v2;
        v1 = this.e1.eval(table, heap);
        if(this.operator > 4 || this.operator < 1){
            throw new MyException("Invalid Op!");
        }
        if(!v1.get_type().equals(new IntegerType()))
            throw new MyException("First val is not an integer");
        v2 = this.e2.eval(table, heap);
        if(!v2.get_type().equals(new IntegerType()))
            throw new MyException("Second val is not an integer");

        IntegerValue val1 = (IntegerValue)v1;
        IntegerValue val2 = (IntegerValue)v2;
        int num1,num2;
        num1=val1.getValue();
        num2 = val2.getValue();
        switch(this.operator){
            case 1:
                return new IntegerValue(num1 + num2);
            case 2:
                return new IntegerValue(num1 - num2);
            case 3:
                return new IntegerValue(num1 * num2);
            case 4:
            if(num2 == 0) throw new MyException("Div by zero exception!");
            else return new IntegerValue(num1/num2);
            default:
                throw new MyException("Invalid OP!");
        }
    }

    @Override
    public IType typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType t1, t2;
        t1 = this.e1.typecheck(typeEnv);
        t2 = this.e2.typecheck(typeEnv);
        if(t1.equals(new IntegerType())){
            if(t2.equals(new IntegerType()))
                return new IntegerType();
            else throw new MyException("Secoond op is not an int");
        }
        else throw new MyException("First op is not an int");
    }


    @Override
    public String toString(){
        return switch(this.operator)
                {
                    case 1 -> this.e1 + "+" + this.e2;
                    case 2 -> this.e1 + "-" + this.e2;
                    case 3 -> this.e1 + "*" + this.e2;
                    case 4 -> this.e1 + "/" + this.e2;
                    default -> "Bad op";
                };
    }
}
