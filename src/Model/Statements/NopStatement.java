package Model.Statements;

import Model.ADT.Dict;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.IType;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
