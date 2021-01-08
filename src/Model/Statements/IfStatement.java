package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.IHeap;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.BooleanType;
import Model.Types.IType;
import Model.Value.BooleanValue;
import Model.Value.IValue;

public class IfStatement implements IStatement{
    final private Expression expression;
    final private IStatement _then,_else;

    public IfStatement(Expression expression, IStatement _then, IStatement _else) {
        this.expression = expression;
        this._then = _then;
        this._else = _else;
    }



    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> stack = state.getExecutionStack();
        IDict<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<Integer, IValue> heapTable = state.getHeap();
        IValue val = this.expression.eval(symbolTable,heapTable);
        BooleanValue bool = new BooleanValue(true);
        if(val.get_type().equals(new BooleanType()))
        {if(val.equals(bool))
                stack.push(_then);
            else
                stack.push(_else);
        }
        else
            throw new MyException("Expression type is not boolean");
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        IType typeExpression = this.expression.typecheck(typeEnv);
        if(typeExpression.equals(new BooleanType())){
            this._then.typecheck((Dict<String, IType>) typeEnv.deepClone());
            this._else.typecheck((Dict<String, IType>) typeEnv.deepClone());
            return typeEnv;
        }
        else
            throw new MyException("IF does not have BOOL condition");
    }

    @Override
    public String toString(){
        return "(If(" + this.expression.toString() + ") then(" + this._then.toString() + ")else(" + this._else.toString()+ "))";
    }
}
