package Controller;

import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repo.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HopefullyWorkingController {
    IRepo repo;
    ExecutorService executor;
    public ArrayList<ProgramState> completedProgramStates;

    public HopefullyWorkingController(IRepo repo){
        this.repo = repo;
        this.completedProgramStates = new ArrayList<>();
    }

    public List<ProgramState> getCompletedProgramStates(List<ProgramState> programStateList){
        return programStateList.stream()
                .filter(Predicate.not(ProgramState::BooleanIsNotCompleted))
                .collect(Collectors.toList());

    }

    public List<ProgramState> removeCompletedProgramStates(List<ProgramState> programStateList){
        return programStateList.stream()
                .filter(ProgramState::BooleanIsNotCompleted)
                .collect(Collectors.toList());
    }

    void oneStepForAll(List<ProgramState> programStateList) throws InterruptedException, MyException {
        programStateList.forEach(p -> {
            try {
                this.repo.logProgramStateExecute(p);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        List<Callable<ProgramState>> callableList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramStatesList;

        try{
            newProgramStatesList = this.executor.invokeAll(callableList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            try {
                                throw new MyException(e.getMessage());
                        } catch (MyException myException) {
                                myException.printStackTrace();
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }catch(Exception e){
            throw new MyException(e.getMessage());
        }

        programStateList.addAll(newProgramStatesList);
        programStateList.forEach(p -> {
            try {
                this.repo.logProgramStateExecute(p);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        this.repo.setProgramStatesList(programStateList);

    }

    public void oneStepExecution() throws MyException, InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedProgramStates(this.repo.getProgramStatesList());
        if(programStateList.size() > 0){
            var heap = programStateList.get(0).getHeap();
            heap.setContent(safeGarbageCollection(getHeapAddressesFromSymbolTable(programStateList.get(0).getSymbolTable().getAll().values()), programStateList.get(0).getHeap().getContent()));

        this.oneStepForAll(programStateList);
        completedProgramStates.addAll(this.getCompletedProgramStates(this.repo.getProgramStatesList()));
        }
        this.executor.shutdown();
        this.repo.setProgramStatesList(programStateList);
    }

    public void allStepExecution() throws MyException, InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedProgramStates(this.repo.getProgramStatesList());

        while(programStateList.size() > 0){
            var heap = programStateList.get(0).getHeap();
            heap.setContent(safeGarbageCollection(getHeapAddressesFromSymbolTable(programStateList.get(0).getSymbolTable().getAll().values()), programStateList.get(0).getHeap().getContent()));
            this.oneStepForAll(programStateList);
            programStateList = removeCompletedProgramStates(this.repo.getProgramStatesList());
        }

        this.executor.shutdown();
        this.repo.setProgramStatesList(programStateList);
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



//    private Map<Integer, IValue> unsafeGarbageCollector(java.util.List<Integer> symbolTableAddresses,
//                                                        Map<Integer, IValue> heap) {
//        return heap.entrySet().stream()
//                .filter(e -> symbolTableAddresses.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }


}
