package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IntegerValue;

import java.util.ArrayList;

public class SwitchStatement implements IStatement{
    Expression exp, exp1, exp2;
    IStatement stm1, stm2, stm3;
    public SwitchStatement(Expression exp, Expression exp1, Expression exp2, IStatement stm1, IStatement stm2, IStatement stm3){
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stm1 = stm1;
        this.stm2 = stm2;
        this.stm3 = stm3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(!(this.exp.eval(state.getSymbolTable(),state.getHeap()) instanceof IntegerValue))
            throw new MyException("Error");
        if(!(this.exp1.eval(state.getSymbolTable(),state.getHeap()) instanceof IntegerValue))
            throw new MyException("Error");
        if(!(this.exp2.eval(state.getSymbolTable(),state.getHeap()) instanceof IntegerValue))
            throw new MyException("Error");
        IStack<IStatement> stack = state.getExecutionStack();
        IStatement newStmt = new IfStatement(new RelationalExpression(exp,exp1,"=="),stm1,new IfStatement(new RelationalExpression(exp,exp2,"=="),stm2,stm3));
        stack.push(newStmt);
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        return null;
    }
}
