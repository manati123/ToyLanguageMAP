package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.IntegerType;
import Model.Types.StringType;
import Model.Value.IValue;
import Model.Value.IntegerValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

public class readRFileStatement implements IStatement{
    Expression expression;
    String variableName;

    public readRFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!symbolTable.isDefined(variableName))
            throw new MyException("Variable does not exist");

        var variable = symbolTable.get(variableName);

        if (!variable.get_type().equals(new IntegerType()))
            throw new MyException("Variable is not of int type");

        IValue value = expression.eval(symbolTable, state.getHeap());

        if (!value.get_type().equals(new StringType()))
            throw new MyException("Expression is not of string type");

        if (!fileTable.isDefined((StringValue)value))
            throw new MyException("There is no file descriptor associated to " + ((StringValue) value).getValue());

        BufferedReader fileDescriptor = fileTable.get((StringValue) value);
        String line;
        try {
            line = fileDescriptor.readLine();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        int number;
        try {
            number = Integer.parseInt(line);
        } catch (Exception e) {
            number = 0;
        }

        symbolTable.update(variableName, new IntegerValue(number));

        return null;

    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException{
        if(!expression.typecheck(typeEnv).equals(new StringType()))
            throw new MyException("ERROR: ReadFile requires a string as expression parameter");
        if(!typeEnv.get(variableName).equals(new IntegerType()))
            throw new MyException("ERROR: ReadFile requires an int as variable parameter");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "readFile("+expression.toString()+")";
    }
}
