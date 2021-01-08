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

public class HeapAllocationStatement implements IStatement{

    final String variableName;
    final Expression expression;

    public HeapAllocationStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();
        if(!symbolTable.isDefined(this.variableName))
            throw new MyException("Undeclared variable!");

        if(!(symbolTable.get(this.variableName).get_type() instanceof RefType))
            throw new MyException("Bad OP!");

     //   System.out.println(((RefType) symbolTable.get(this.variableName).get_type()).getInner().getInner());

        var value = this.expression.eval(symbolTable,heap);
        var refType = (RefValue) symbolTable.get(this.variableName);
        if(!value.get_type().equals(refType.getLocationType()))
            throw new MyException("Types not matching!");


        //var refType = (RefValue) symbolTable.get(this.variableName).get_type();
        //var value = this.expression.eval(symbolTable,heap);
        //if(!(value.get_type().toString().equals(refType.getInner().toString())))
          //  throw new MyException("Types not matching!");

        heap.autoAdd(value);
        var addr = heap.getAddr()-1;
        var newRefValue = new RefValue(addr,refType.getLocationType());

        symbolTable.update(this.variableName,newRefValue);
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.get(this.variableName);
        IType typexep = this.expression.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexep)))
            return typeEnv;
        else
            throw new MyException("HEAP ALLOC STMT: right side and left side have different types");
    }

    @Override
    public String toString(){
        return "new(" + this.variableName + ", " + this.expression + ")";
    }
}
