package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapWriteStatement implements IStatement{
    String variableName;
    Expression expression;

    public HeapWriteStatement(String _var, Expression _exp){
        this.variableName = _var;
        this.expression = _exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();

        if(!symbolTable.isDefined(this.variableName))
            throw new MyException("Variable not defined!");

        var value = symbolTable.get(this.variableName);
        if(!(value.get_type() instanceof RefType))
            throw new MyException("Not matching variable types!");
        var heapAddr = ((RefValue) symbolTable.get(this.variableName)).getAddr();
        if(!heap.isDefined(heapAddr))
            throw new MyException("Invalid head address!");

        var expressionValue = this.expression.eval(symbolTable,heap);
        if(!expressionValue.get_type().equals(((RefType) value.get_type()).getInner()))
            throw new MyException("Variable types not matching!");

        heap.update(heapAddr, expressionValue);
        return null;

    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.get(this.variableName);
        IType typeExp = this.expression.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("Heap write stmt: error");
    }

    @Override
    public String toString(){
        return "writeHeap(" + this.variableName + ", " + this.expression + ")";
    }
}
