package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IntegerValue;

public class  ForStatemtnt implements IStatement{
    String variable;
    Expression exp1, exp2, exp3;
    IStatement statement;
    public ForStatemtnt(String variable, Expression ex1, Expression ex2, Expression ex3, IStatement statement) throws Exception, MyException {
        this.variable = variable;
        this.exp1 = ex1;
        this.exp2 = ex2;
        this.exp3 = ex3;
        this.statement = statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
//        if(!((exp1.eval(state.getSymbolTable(), state.getHeap())) instanceof IntegerValue))
//            throw new MyException("Error");
//        if(!((exp1.eval(state.getSymbolTable(), state.getHeap())) instanceof IntegerValue))
//            throw new MyException("Error");
//        if(!((exp3.eval(state.getSymbolTable(), state.getHeap())) instanceof IntegerValue))
//            throw new MyException("Error");
        IStack<IStatement> stack = state.getExecutionStack();
        IStatement newStatement = new CompStatement(new AssignStatement("v", exp1),
                new WhileStatement( exp2, new CompStatement(this.statement,
                        new AssignStatement("v", exp3))));
//        IStatement newStatement = new CompStatement(new AssignStatement("v",exp1),new WhileStatement(new RelationalExpression(new VariableExpression("v"),exp1,"!="),
//                new CompStatement(this.statement,new AssignStatement("v",this.exp3))));
        stack.push(newStatement);
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
    
    @Override
    public String toString(){
        return "for(" + variable + "= " + exp1.toString() + "; " +  exp2.toString() + "; " + variable + "= " + exp3.toString() + ") " +
                this.statement.toString();
    }


}
