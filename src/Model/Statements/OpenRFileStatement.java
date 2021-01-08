package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{
    public Expression expression;

    public OpenRFileStatement(Expression _expression){
        this.expression = _expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StringValue value = StringValueGetter.run(state,this.expression);
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        //IHeap<Integer, IValue> heap = state.getHeap();
        if(value.get_type().equals(new StringType()))
        {
            StringValue stringVal = (StringValue)value;
            if(fileTable.get(stringVal) == null){
                try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader(stringVal.getValue().replaceAll("\\s+","")));
                fileTable.add(stringVal, bufferedReader);
                }catch(FileNotFoundException exception){
                    throw new MyException(exception.getMessage());
                }
        }
        if(fileTable.isDefined(value)){
            throw new MyException("Value is already defined!");
        }

        return null;
        }
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType typeExp = this.expression.typecheck(typeEnv);
        if(typeExp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("Expression not going stringy!");
    }


    @Override
    public String toString(){
        return "open -> " + this.expression.toString();
    }
}
