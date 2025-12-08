package controller;

import com.sun.jdi.Value;
import model.program_state.ProgramState;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    IRepository repo;
    boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
        this.displayFlag = false;
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr,
                                                    Map<Integer, Value>
                                                            heap) {
        return heap.entrySet()
                .stream()
                .filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value>
            heap) {
        return heap.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //one step for each existing PrgState (namely each thread)
    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logProgramStateExecution(prg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        //RUN concurrently one step for each of the existing PrgStates
        //prepare the list of callables
        //se face onestep pe fiecare prgstate deodata
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());
        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        // because of invoke all we need a list of future(that have the one step), then get the result from one step=>list prgstate
        List<ProgramState> newPrgList = executor.invokeAll(callList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {

            try {
                repo.logProgramStateExecution(prg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        //Save the current programs in the repository
        repo.setProgramStates(prgList);

    }

    public void allSteps() throws InterruptedException {

        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> prgList = removeCompletedPrg(repo.getProgramStates());
        while (prgList.size() > 0) {

            repo.getProgramStates()
                    .stream()
                    .forEach(program -> program.getHeap()
                            .setContent((HashMap<Integer, Value>) unsafeGarbageCollector(
                                    getAddrFromSymTable(program.getSymTable()
                                            .getContent()
                                            .values()),
                                    program.getHeap()
                                            .getContent())));

            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList = removeCompletedPrg(repo.getProgramStates());
        }
        executor.shutdownNow();
        //the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method

        // update the repository state
        repo.setProgramStates(prgList);

    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public IRepository getRepository() {
        return repo;
    }
}
