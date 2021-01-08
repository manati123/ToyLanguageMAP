package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.IntegerType;
import Model.Types.RefType;
import Model.Value.*;

public class VariableDeclarationStatement implements IStatement{
    final private String name;
    final private IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IValue val;
        switch(type.toString()){
            case "boolean" -> val = new BooleanValue(false);
            case "int" -> val= new IntegerValue(0);
            case "string" -> val = new StringValue("");
            case "ref" -> val = new RefValue(1,new IntegerType());
            case "ref int" -> val = new RefValue(1,new IntegerType());
            case "ref ref int" -> val = new RefValue(1,new RefType(new IntegerType()));
            default -> throw new MyException("Invalid type/not implemented yet");
        }
        if(symbolTable.isDefined(this.name))
            throw new MyException("Variable "+ this.name + "already exists");
        symbolTable.add(this.name, val);
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        typeEnv.add(this.name,this.type);
        return typeEnv;
    }

    @Override
    public String toString(){
        return this.type + " " +this.name;
    }
}
