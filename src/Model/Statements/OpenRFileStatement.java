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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement {
    Expression expression;

    public OpenRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = expression.eval(symbolTable, state.getHeap());

        if (value.get_type().equals(new StringType())){
            if (fileTable.isDefined((StringValue) value)){
                throw new MyException("The file is already opened");
            }
            BufferedReader fileDescriptor;
            try{
                String fileName = ((StringValue)value).getValue();
                String path = new File(fileName).getAbsolutePath();
                fileDescriptor = new BufferedReader(new FileReader(path));
            } catch (IOException e) {
                throw new MyException("File does not exist");
            }
            fileTable.add((StringValue)value, fileDescriptor);

            return null;

        }
        else
            throw new MyException("Expression type is not a string");
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException{
        IType type = expression.typecheck(typeEnv);
        if(!type.equals(new StringType()))
            throw new MyException("Expression is not a string");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "openRFile("+expression.toString()+")";
    }
}
