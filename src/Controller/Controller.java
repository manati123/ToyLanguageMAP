package Controller;

import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repo.IRepo;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    ExecutorService executor =
            new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
    IRepo repo;
    boolean debugFlag;
    public Controller(IRepo _r,boolean debugFrag){
        //this.executor = new Executor();
        this.repo = _r;
        this.debugFlag = debugFrag;}

    public Controller(IRepo repo){
        this(repo,false);
    }

    public ProgramState oneStep(ProgramState state) throws MyException{
        IStack<IStatement> executionStack = state.getExecutionStack();
        if(executionStack.empty())
            throw new MyException("Execution stack is empty");
        IStatement statement = executionStack.pop();
        return statement.execute(state);
    }

    public void allStep() throws MyException, InterruptedException {
        /*ProgramState program = this.repo.getCurrentProgramState();
        try{
            Files.deleteIfExists(FileSystems.getDefault().getPath(repo.getLogFilePath()));
        }catch(IOException exception){
            throw new MyException("Given path is a directory! -> " + repo.getLogFilePath());
        }
        if(this.debugFlag)
            repo.logProgramStateExecute();

        System.out.println(this.repo.getCurrentProgramState());//I still like seeing it fill the console
        while(!program.getExecutionStack().empty()){
            this.oneStep(program);
            System.out.println("#########################################");
            System.out.println(this.repo.getCurrentProgramState());
            if(this.debugFlag)
                this.repo.logProgramStateExecute();
            program.getHeap().setContent(safeGarbageCollection(getHeapAddressesFromSymbolTable(program.getSymbolTable().getAll().values()), program.getHeap().getContent()));
        }
        program.reset();*/
        //this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStatesList = removeCompletedProgramStates(repo.getProgramStatesList());
        while(programStatesList.size() > 0){
            programStatesList.get(0).getHeap().setContent(safeGarbageCollection(getHeapAddressesFromSymbolTable(programStatesList.get(0).getSymbolTable().getAll().values()), programStatesList.get(0).getHeap().getContent()));
           /* programStatesList.forEach(program -> {
                program.getHeap().setContent(safeGarbageCollection(getHeapAddressesFromSymbolTable(program.getSymbolTable().getAll().values()), program.getHeap().getContent()));
            });*/
            this.oneStepForAllPrograms(programStatesList);
            programStatesList=removeCompletedProgramStates(repo.getProgramStatesList());
        }
        executor.shutdownNow();
        repo.setProgramStatesList(programStatesList);
    }

    public Map<Integer, IValue> garbageCollector(Set<Integer> symTableAddr, Map<Integer, IValue> heap){
        return heap.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    private Map<Integer, IValue> safeGarbageCollection(List<Integer> symbolTableAddresses, Map<Integer, IValue> heap) {
        var heapReferencedAddresses = heap.values().stream()
                .filter(e -> e instanceof RefValue)
                .map(e -> ((RefValue) e).getAddr())
                .collect(Collectors.toList());
        return heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()) || heapReferencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //public ProgramState getMainProgramState(){return this.repo.getCurrentProgramState();}


    private List<Integer> getHeapAddressesFromSymbolTable(Collection<IValue> symbolTableValues) {
        return symbolTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .collect(Collectors.toList());
    }

    List<ProgramState> removeCompletedProgramStates(List<ProgramState> inProgramStateList){
        return inProgramStateList.stream()
                .filter(ProgramState::BooleanIsNotCompleted)
                .collect(Collectors.toList());
    }

    void oneStepForAllPrograms(List<ProgramState> programStateList) throws InterruptedException {
        programStateList.forEach(prg -> {
            try {
                this.repo.logProgramStateExecute(prg);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        List<Callable<ProgramState>> callList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramsList = this.executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStateList.addAll(newProgramsList);
        programStateList.forEach(p -> {
            try {
                repo.logProgramStateExecute(p);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        this.repo.setProgramStatesList(programStateList);
    }

}