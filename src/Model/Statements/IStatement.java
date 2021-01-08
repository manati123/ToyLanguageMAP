package Model.Statements;
import Model.ADT.Dict;
import Model.Exceptions.*;
import Model.ProgramState;
import Model.Types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    Dict<String, IType> typecheck(Dict<String,IType> typeEnv) throws MyException;
}
