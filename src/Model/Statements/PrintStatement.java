package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.ADT.IList;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IValue;

public class PrintStatement implements IStatement{
    final private Expression expression;
    public PrintStatement(Expression expresision) {
        this.expression = expresision;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IList<IValue> out = state.getOutput();
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<Integer, IValue> heapTable = state.getHeap();
        out.add(this.expression.eval(symbolTable,heapTable));
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        this.expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){return "print(" + this.expression.toString() + ")";}
}
