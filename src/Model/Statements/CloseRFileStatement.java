package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    public Expression expression;

    public CloseRFileStatement(Expression _expression){
        this.expression = _expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StringValue value = StringValueGetter.run(state, this.expression);
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        if(!fileTable.isDefined(value)){
            throw new MyException("File is not opened!");
        }
        BufferedReader reader = fileTable.get(value);
        try{
            reader.close();
        }
        catch(IOException exception){
            throw new MyException("IO Exception -> " + exception);
        }
        finally{
            fileTable.remove(value);
        }
        return state;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        this.expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "closed -> " + this.expression.toString();
    }
}
