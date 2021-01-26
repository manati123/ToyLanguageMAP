package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    Expression expression;

    public CloseRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = expression.eval(symbolTable, state.getHeap());

        if (!value.get_type().equals(new StringType()))
            throw new MyException("Expresion is not of string type");

        if (!fileTable.isDefined((StringValue)value))
            throw new MyException("There is no file descriptor associated to " + ((StringValue) value).getValue());

        BufferedReader fileDescriptor = fileTable.get((StringValue) value);

        try {
            fileDescriptor.close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }

        fileTable.remove((StringValue) value);

        return null;

    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException{
        if(!expression.typecheck(typeEnv).equals(new StringType()))
            throw new MyException("ERROR: CloseReadFile requires a string expression");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "closeRFile("+expression.toString()+")";
    }

}
