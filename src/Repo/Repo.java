package Repo;

import Model.ADT.*;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


interface Writer{
    static void print(PrintWriter logFile, String title, String structure, String regex){
        logFile.println(title);
        if(!title.equals("FileTable:")){
            structure = structure.replaceAll("=","->");
        }
        else{
            structure = structure.replaceAll("=.*([,}])","$1");
        }
        String[] lines = structure.split(regex);
        ArrayList<String> linesArray = new ArrayList<>(Arrays.asList(lines));
        linesArray.stream()
                .filter((line) -> !line.equals("")  && !line.equals("\n"))
                .forEach((line) -> logFile.println(" " + line));
    }
}


public class Repo implements IRepo {
    List<ProgramState> programStateArrayList;
    int currentProgramState;
    String logFilePath;

    public Repo(String logFilePath, ProgramState programState) {
        this.programStateArrayList = new ArrayList<>();
        this.programStateArrayList.add(programState);
        this.currentProgramState = 0;
        this.logFilePath = logFilePath;
    }


    /* public Repo(IStatement program){
         this.programStateArrayList = new ArrayList<>();
         ProgramState mainProgram = new ProgramState(new Stack<>(), new Dict<>(), new List<>(), program);
         this.programStateArrayList.add(mainProgram);
         this.currentProgramState = 0;
     }*/
   // @Override
   // //public ProgramState getCurrentProgramState() { return this.programStateArrayList.get(this.currentProgramState); }

    @Override
    public String getLogFilePath() {
        return this.logFilePath;
    }

    @Override
    public void logProgramStateExecute() throws MyException {
        ProgramState program = this.programStateArrayList.get(this.currentProgramState);
        IStack<IStatement> executionStack = program.getExecutionStack();
        IList<IValue> output = program.getOutput();
        IDict<String, IValue> symbolTable = program.getSymbolTable();
        IDict<StringValue, BufferedReader> fileTable = program.getFileTable();
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            Writer.print(logFile, "ExeStack:", executionStack.toString(), ", |[\\[\\]]");
            Writer.print(logFile, "SymTable:", symbolTable.toString(), ", |[{}\n]");
            Writer.print(logFile, "Out:", output.toString(), ", |[\\[\\]]");
            Writer.print(logFile, "FileTable:", fileTable.toString(), ", |[{}]");
            logFile.println("#########################################");
            logFile.close();
        } catch (IOException e) {
            System.out.println("Error opening or writing to file: " + e);
        }
    }

    @Override
    public void logProgramStateExecute(ProgramState program) throws MyException {
        IStack<IStatement> executionStack = program.getExecutionStack();
        IList<IValue> output = program.getOutput();
        IDict<String, IValue> symbolTable = program.getSymbolTable();
        IDict<StringValue, BufferedReader> fileTable = program.getFileTable();
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            Writer.print(logFile,"PID: ",String.valueOf(program.getId()), "");
            Writer.print(logFile, "ExeStack:", executionStack.toString(), ", |[\\[\\]]");
            Writer.print(logFile, "SymTable:", symbolTable.toString(), ", |[{}\n]");
            Writer.print(logFile, "Out:", output.toString(), ", |[\\[\\]]");
            Writer.print(logFile, "FileTable:", fileTable.toString(), ", |[{}]");
            logFile.println("#########################################");
            logFile.close();
        } catch (IOException e) {
            System.out.println("Error opening or writing to file: " + e);
        }
    }

    @Override
    public List<ProgramState> getProgramStatesList() {
        return this.programStateArrayList;
    }

    @Override
    public void setProgramStatesList(List<ProgramState> newProgramStatesList) {
        this.programStateArrayList = newProgramStatesList;
        this.currentProgramState = 0;
    }
}