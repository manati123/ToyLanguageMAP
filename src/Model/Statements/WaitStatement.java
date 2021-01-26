package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.Expression;
import Model.Expressions.ValueExpression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IValue;
import Model.Value.IntegerValue;

public class WaitStatement implements IStatement{
    Expression number;

    public WaitStatement(Expression _number){
        this.number = _number;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(!(this.number.eval(state.getSymbolTable(),state.getHeap()) instanceof IntegerValue))
            throw new MyException("Invalid!------21");
        IValue nr = this.number.eval(state.getSymbolTable(),state.getHeap());
        if(!(nr instanceof IntegerValue))
            throw new MyException("Invalid!------24");
        int value =((IntegerValue)nr).getValue();
        IStack<IStatement> stack = state.getExecutionStack();
        if(value != 0){
            stack.push(new CompStatement(new PrintStatement(number),new WaitStatement(new ArithmeticExpression(number, new ValueExpression(new IntegerValue(1)),2))));
        }
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "wait( " + number.toString() + ")";
    }
}
