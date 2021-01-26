package Model.Statements;

import Model.ADT.Dict;
import Model.ADT.IDict;
import Model.ADT.ILockTable;
import Model.ADT.LockTable;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.IType;
import Model.Value.IValue;
import Model.Value.IntegerValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements IStatement{
    String variable;
    private Lock lock = new ReentrantLock();

    public NewLockStatement(String variable){
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        ILockTable<Integer , Integer> lockTable = state.getLockTable();
        IDict<String, IValue> symbolTable = state.getSymbolTable();

        Integer location = lockTable.getLockAddress();
        lockTable.put(location,-1);
        if(symbolTable.isDefined(variable))
            symbolTable.update(variable, new IntegerValue(location));
        else
            symbolTable.add(variable,new IntegerValue(location));
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
}
