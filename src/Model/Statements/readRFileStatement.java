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
    public Expression expression;
    String variableName;

    public readRFileStatement(Expression _expression,String _variableName){
        this.expression = _expression;
        this.variableName = _variableName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, IValue> heapTable = state.getHeap();
        if(!symbolTable.isDefined(this.variableName)){
            throw new MyException("Variable is not defined!");
        }
        IValue val = symbolTable.get(this.variableName);
        if(!val.get_type().equals(new IntegerType())){
            throw new MyException("Variable should be ann integer!");
        }
        IValue expressionValue = this.expression.eval(symbolTable,heapTable);
        if(!expressionValue.get_type().equals(new StringType())){
            throw new MyException("Expression should be string type");
        }
        StringValue stringValue = (StringValue)expressionValue;
        if(!fileTable.isDefined(stringValue)){
            throw new MyException("File : " + stringValue.getValue() + ", is not open");
        }
        BufferedReader reader = fileTable.get(stringValue);
        try{
            String line = reader.readLine();
            if(line == null)
                throw new MyException("File is empty! " + stringValue.getValue());
            if(line.equals(""))
                symbolTable.update(this.variableName, new IntegerValue(0));
            else{
                int newValue = Integer.parseInt(line);
                symbolTable.update(this.variableName, new IntegerValue(newValue));
            }
        }
        catch(EOFException exception){
            throw new MyException("Not enough data in file " + stringValue.getValue());
        }
        catch (IOException exception){
            throw new MyException( "IO Exception: " + exception);
        }
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        return null;
    }

    @Override
    public String toString(){
        return "read from -> " + this.expression.toString() + " into -> " + this.variableName;
    }
}
