package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.Stack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IValue;

public class ForkStatement implements IStatement{
    private final IStatement statement;
    public ForkStatement(IStatement stmt){
        this.statement = stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Stack<IStatement> execStackc = new Stack<IStatement>();
        return new ProgramState(execStackc, ((Dict<String, IValue>)state.getSymbolTable()).deepClone(), state.getOutput(),statement, state.getFileTable(),state.getHeap());
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType typeExp = (IType) this.statement.typecheck((Dict<String, IType>) typeEnv.deepClone());
        return typeEnv;
    }

    @Override
    public String toString(){
        return "fork(" + this.statement + ")";
    }
}
