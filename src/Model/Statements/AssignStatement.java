package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IValue;

public class AssignStatement implements  IStatement {
    final private String ID;
    final private Expression expression;

    public AssignStatement(String ID, Expression expression) {
        this.ID = ID;
        this.expression = expression;
    }
    // final private Exp exp;

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> statementIStack =  state.getExecutionStack();
        IDict<String, IValue> table = state.getSymbolTable();
        IHeap<Integer, IValue> heapTable = state.getHeap();
        //IValue val = this.expression.eval(symbolTable,heapTable);
        if(table.isDefined(this.ID)){
            IValue val = this.expression.eval(table,heapTable);
            IType type = table.get(this.ID).get_type();
            if(val.get_type().equals(type))
                table.update(this.ID, val);
            else
                throw new MyException("Type of variable " + this.ID + " and type of assigned expr don't match");
        }
        else throw new MyException("Variable " +this.ID + " was not declared");
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType typeVariable = typeEnv.get(this.ID);
        IType typeExpression = this.expression.typecheck(typeEnv);
        if(typeVariable.equals(typeExpression))
            return typeEnv;
        else
            throw new MyException("Right and left have different types");
    }

    @Override
    public String toString() {return this.ID + " = " + this.expression.toString();}
}
