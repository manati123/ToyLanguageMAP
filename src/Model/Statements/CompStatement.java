package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.IType;

public class CompStatement implements IStatement{
    final private IStatement first, second;

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        return this.second.typecheck(this.first.typecheck(typeEnv));
    }

    @Override
    public String toString(){return "(" + this.first.toString() + ";" + this.second.toString() + ")";}
}
