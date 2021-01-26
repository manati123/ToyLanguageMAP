package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.BooleanType;
import Model.Types.IType;
import Model.Value.BooleanValue;
import Model.Value.IValue;

public class WhileStatement implements IStatement {

    Expression expression;
    IStatement exec;

    public WhileStatement(Expression expression, IStatement exec) {
        this.expression = expression;
        this.exec = exec;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue conditionValue = this.expression.eval(state.getSymbolTable(), state.getHeap());

        if (!(conditionValue.get_type() instanceof BooleanType))
            throw new MyException(this.expression + "is not of BoolType");

        if (((BooleanValue) conditionValue).getVal()) {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(this.exec);
        }

        return null;
    }



    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException{
        IType typeExpression = this.expression.typecheck(typeEnv);

        if(!(typeExpression.equals(new BooleanType()))){
            //System.out.println("Problem klan!");
            throw new MyException("The condition of WHILE has not the type bool");

        }
        this.exec.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "(while(||||" + this.expression + "|||||)" + this.exec + "||||)";
    }
}
