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
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<Integer, IValue> heap = state.getHeap();
        IStack<IStatement> executionStack = state.getExecutionStack();
        IValue val = this.expression.eval(symbolTable,heap);
        if(val.get_type() instanceof BooleanType){
            if(((BooleanValue)val).getVal()){
                executionStack.push(this);
                executionStack.push(this.exec);
            }
        }
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType typeExp = this.expression.typecheck(typeEnv);
        if(typeExp.equals(new BooleanType()))
        {
            this.exec.typecheck((Dict<String, IType>) typeEnv.deepClone());
            return typeEnv;
        }
        else
            throw new MyException("WHILE does not loop based on bool");
    }

    @Override
    public String toString() {
        return "(while(" + this.expression + ")" + this.exec + ")";
    }
}
