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
import Model.Value.IntegerValue;

import java.util.Arrays;
import java.util.List;

public class RelationalExpression implements Expression{
    Expression e1;
    Expression e2;
    String operation;
    private static final List<String> operators = Arrays.asList("<", "<=", "==", "!=", ">", ">=");

    public RelationalExpression(Expression e1, Expression e2, String op){
        this.e1 = e1;
        this.e2 = e2;
        this.operation = op;
    }

    @Override
    public IValue eval(IDict<String, IValue> tbl, IHeap<Integer, IValue> heap) throws MyException {
        if (!operators.contains(this.operation)){
            throw new MyException("Invalid operator");
        }
        IValue v1,v2;
        v1= e1.eval(tbl,heap);
        if (!v1.get_type().equals(new IntegerType())) {
            throw new MyException("first hand operand is not an integer");
        }
        v2 = e2.eval(tbl,heap);
        if (!v2.get_type().equals(new IntegerType())) {
            throw new MyException("second hand operand is not an integer");
        }
        IntegerValue i1 = (IntegerValue) v1;
        IntegerValue i2 = (IntegerValue) v2;
        int n1, n2;
        n1 = i1.getValue();
        n2 = i2.getValue();
        return switch (this.operation) {
            case "<" -> new BooleanValue(n1 < n2);
            case "<=" -> new BooleanValue(n1 <= n2);
            case "==" -> new BooleanValue(n1 == n2);
            case "!=" -> new BooleanValue(n1 != n2);
            case ">" -> new BooleanValue(n1 > n2);
            case ">=" -> new BooleanValue(n1 >= n2);
            default -> throw new MyException("bad operator");
        };
    }

    @Override
    public IType typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType t1,t2;
        t1 = this.e1.typecheck(typeEnv);
        t2 = this.e2.typecheck(typeEnv);
        if(t1.equals(new IntegerType())){
            if(t2.equals(new IntegerType()))
                return new BooleanType();
            else throw new MyException("Second op is not an int");
        }
        else throw new MyException("First op is not an int");
    }

    @Override
    public String toString() {
        return this.e1 + this.operation + this.e2;
    }
}
