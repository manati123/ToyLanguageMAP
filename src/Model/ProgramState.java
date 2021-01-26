package Model;

import Model.ADT.*;
import Model.CyclicBarrier.Pair;
import Model.Exceptions.MyException;
import Model.Statements.IStatement;
import Model.Types.IType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.*;

public class ProgramState {
    private static int id = 0;
    private int localId;
    ILockTable<Integer, Integer> LockTable;
    private static final Object lock = new Object();
    IStack<IStatement> executionStack;
    IDict<String, IValue> symbolTable;
    IList<IValue> out;
    ILockTable<Integer, Integer> lockTable;
   // ICyclicBarrier<Integer, Pair<IType,IType>> cyclicBarrier;
    public IStatement originalProgramState;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap<Integer, IValue> heap;
    private static synchronized  int getNextId(){
        synchronized (lock){
            return id++;
        }
    }



    public ProgramState(IStatement originalProgramState) {
        localId = getNextId();
        //this.cyclicBarrier = new CyclicBarrier<>();
        //System.out.println("uses dis");
        //System.out.println(id);
        this.executionStack = new Stack<>();
        this.symbolTable = new Dict<>();
        this.heap = new Heap<>();
        this.out = new List<>();
        this.fileTable = new Dict<StringValue, BufferedReader>();
        this.originalProgramState = originalProgramState;

        this.executionStack.push(originalProgramState);
    }

    public ProgramState(IStack<IStatement> executionStack, IDict<String, IValue> symbolTable, IList<IValue> out,
                        IStatement originalProgramState, IDict<StringValue, BufferedReader> _fileTable,
                        IHeap<Integer, IValue> heap,ILockTable<Integer, Integer> LockTable) {
        this.executionStack = executionStack;
        //System.out.println("No u");
        localId = getNextId();
        this.heap = heap;
        this.symbolTable = symbolTable;
        this.out = out;
        this.originalProgramState = originalProgramState;
        this.fileTable = _fileTable;
        this.lockTable = LockTable;
        //this.cyclicBarrier = cyclicBarrier;
        if(originalProgramState != null)
            this.executionStack.push(originalProgramState);
    }


//    public ProgramState(IStack<IStatement> executionStack, IDict<String, IValue> symbolTable, IList<IValue> out,
//                        IStatement originalProgramState, IDict<StringValue, BufferedReader> _fileTable,
//                        IHeap<Integer, IValue> heap,ICyclicBarrier<Integer, Pair<IType,IType>> cyclicBarrier) {
//        this.executionStack = executionStack;
//        //System.out.println("No u");
//        localId = getNextId();
//        this.heap = heap;
//        this.symbolTable = symbolTable;
//        this.out = out;
//        this.originalProgramState = originalProgramState;
//        this.fileTable = _fileTable;
//        //this.cyclicBarrier = cyclicBarrier;
//        if(originalProgramState != null)
//            this.executionStack.push(originalProgramState);
//    }

//    public ProgramState(IStack<IStatement> executionStack, IDict<String, IValue> symbolTable, IList<IValue> out, IStatement originalProgramState, IDict<StringValue, BufferedReader> _fileTable, IHeap<Integer, IValue> heap) {
//        this.executionStack = executionStack;
//        //System.out.println("No u");
//        localId = getNextId();
//        this.heap = heap;
//        this.symbolTable = symbolTable;
//        this.out = out;
//        this.originalProgramState = originalProgramState;
//        this.fileTable = _fileTable;
//        if(originalProgramState != null)
//            this.executionStack.push(originalProgramState);
//    }

    public ILockTable<Integer , Integer> getLockTable(){
        return this.lockTable;
    }
    //public ICyclicBarrier<Integer, Pair<IType,IType>> getCyclicBarrierTable(){return this.cyclicBarrier;}
    public IStack<IStatement> getExecutionStack(){return this.executionStack;}
    public IList<IValue>  getOutput() { return this.out;}
    public IDict<String, IValue> getSymbolTable(){return this.symbolTable;}
    public IDict<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }
    public IHeap<Integer, IValue> getHeap(){return this.heap;}
    @Override
    public String toString(){
        return "ID = " + String.valueOf(this.getId()) + "\n" +
                "executionStack = " + this.executionStack + "\n"+
                "symbolTable = " + this.symbolTable + "\n"+
                "output = " + this.out+ "\n" +
                "Heap table = " + this.heap + "\n" +
                "Lock Table = " + this.lockTable;
    }



    public void reset(){
        this.executionStack.clr();
        this.executionStack.push(this.originalProgramState);
        this.symbolTable.clr();
        this.fileTable.clr();
        this.out.empty();
    }

    public boolean BooleanIsNotCompleted(){
        return !this.executionStack.empty();
    }

    public ProgramState oneStep() throws MyException{
        if (executionStack.empty())
            throw new MyException("program state stack is empty");
        try {
            return executionStack.pop().execute(this);
        } catch (MyException exception) {
            executionStack.clr();
            throw exception;
        }
    }

    public int getId(){
        return this.localId;
    }




}
