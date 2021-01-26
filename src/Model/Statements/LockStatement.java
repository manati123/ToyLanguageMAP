package Model.Statements;


import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.ILockTable;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Types.IType;
import Model.Value.IValue;
import Model.Value.IntegerValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStatement implements IStatement {
    String variable;
    private Lock lock = new ReentrantLock();

    public LockStatement(String variable){
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws  MyException {
        lock.lock();
        IDict<String, IValue> SymbolsTable = state.getSymbolTable();
        ILockTable<Integer, Integer> LockTable = state.getLockTable();
        IStack<IStatement> ExecutionStack = state.getExecutionStack();

        if(!SymbolsTable.isDefined(variable)) {
            throw new MyException("Undefined variable in Symbols Table" + variable.toString());
        }

        IValue foundIndex = SymbolsTable.get(variable);

        foundIndex = (IntegerValue) foundIndex;

        if(!LockTable.isDefined(((IntegerValue) foundIndex).getValue())){
            throw new MyException("Undefined variable in LockTable" + foundIndex.toString());
        }
        else if(LockTable.get(((IntegerValue) foundIndex).getValue()) == -1){
            LockTable.put(((IntegerValue) foundIndex).getValue(), state.getId());
        }
        else ExecutionStack.push(this);
        lock.unlock();
        return null;
    }

    @Override
    public Dict<String, IType> typecheck(Dict<String, IType> typeEnv) throws MyException {
        if(typeEnv.get(this.variable) instanceof IntegerValue)
            return typeEnv;
        else
            throw new MyException("Variable is not int");
    }

    @Override
    public String toString(){
        return "Lock( " + variable + ")";
    }


}
