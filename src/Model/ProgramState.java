package Model;

import Model.ADT.*;
import Model.Exceptions.MyException;
import Model.Statements.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.*;

public class ProgramState {
    private static int id = 0;
    private int localId;
    private static final Object lock = new Object();
    IStack<IStatement> executionStack;
    IDict<String, IValue> symbolTable;
    IList<IValue> out;
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

    public ProgramState(IStack<IStatement> executionStack, IDict<String, IValue> symbolTable, IList<IValue> out, IStatement originalProgramState, IDict<StringValue, BufferedReader> _fileTable, IHeap<Integer, IValue> heap) {
        this.executionStack = executionStack;
        //System.out.println("No u");
        localId = getNextId();
        this.heap = heap;
        this.symbolTable = symbolTable;
        this.out = out;
        this.originalProgramState = originalProgramState;
        this.fileTable = _fileTable;
        if(originalProgramState != null)
            this.executionStack.push(originalProgramState);
    }
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
                "Heap table = " + this.heap;
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
        if(!BooleanIsNotCompleted())
            throw new MyException("program state stack is empty!");
        IStatement currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    public int getId(){
        return this.localId;
    }




}
