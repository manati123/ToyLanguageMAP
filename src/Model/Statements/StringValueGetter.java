package Model.Statements;

import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;
import com.sun.jdi.Value;

public interface StringValueGetter {
    static StringValue run(ProgramState state, Expression expression) throws MyException{
        IDict<String, IValue> table = state.getSymbolTable();
        IHeap<Integer, IValue> heapTable = state.getHeap();
        IValue expressionValue = expression.eval(table,heapTable);
        if(!expressionValue.get_type().equals(new StringType())){
            throw new MyException("Expression should be of string type!");
        }
        return (StringValue)expressionValue;
    }
}
