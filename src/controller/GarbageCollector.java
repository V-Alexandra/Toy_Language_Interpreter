package controller;

import model.value.IValue;
import model.value.RefValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {
    public GarbageCollector() {
    }

//    public Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
//        return heap.entrySet().stream()
//                .filter(e -> symTableAddr.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

    //filter the heap and only the (k,v) pairs whose address is found within the
    // allReachableAddresses set are kep
    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        Set<Integer> allReachableAddresses = getReachableAddresses(symTableAddr, heap);

        return heap.entrySet().stream()
                .filter(e -> allReachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Set<Integer> getReachableAddresses(List<Integer> rootAddresses, Map<Integer, IValue> heap) {
        Set<Integer> reachable = rootAddresses.stream()
                .filter(addr -> addr != 0) //exclude null references
                .collect(Collectors.toSet());

        Set<Integer> worklist = reachable.stream().collect(Collectors.toSet());

        //find reachable addresses
        while (!worklist.isEmpty()) {
            Set<Integer> nextWorklist = worklist.stream()
                    .filter(heap::containsKey)
                    .map(heap::get)
                    .flatMap(this::getAddressesFromValue)
                    .filter(addr -> !reachable.contains(addr))
                    .collect(Collectors.toSet());

            reachable.addAll(nextWorklist);
            worklist = nextWorklist;
        }
        return reachable;
    }

    private Stream<Integer> getAddressesFromValue(IValue value) {
        if (value instanceof RefValue refValue) {
            return Stream.of(refValue.getAddress());
        }
        return Stream.empty();
    }

    //scan and extract all the memory addresses they currently hold
    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .flatMap(this::getAddressesFromValue)
                .collect(Collectors.toList());
    }
}