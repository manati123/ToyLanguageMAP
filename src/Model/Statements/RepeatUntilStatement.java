package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.ValueExpression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IntegerValue;

public class RepeatUntilStatement implements IStatement {
    Expression exp2;
    IStatement stm1;
    public RepeatUntilStatement(IStatement stm1, Expression exp2){
        this.stm1 = stm1;
        this.exp2 = exp2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> stack = state.getExecutionStack();
        IStatement newStatement = new CompStatement(stm1, new WhileStatement(new RelationalExpression(exp2, new ValueExpression(new IntegerValue(0)), "!="), stm1));
        stack.push(newStatement);
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        return null;
    }

    @Override
    public String toString(){
        return "(repeat " + this.stm1.toString() + " until "
        + this.exp2.toString();
    }


}
