package be.neonweb.aoc.solvers.d10;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class D10a extends Solver {

    Queue<Operation> operations;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        operations = Files.readAllLines(Path.of("src/main/resources/d10/input.txt"))
                          .stream()
                          .map(Operation::of)
                          .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Object solve() throws Exception {
        Map<Integer, Integer> cycles = new HashMap<>();
        cycles.put(20, null);
        cycles.put(60, null);
        cycles.put(100, null);
        cycles.put(140, null);
        cycles.put(180, null);
        cycles.put(220, null);

        CPU cpu = new CPU();
        cpu.setOperations(operations);
        cpu.run(cycles);

        return cycles.values()
                     .stream()
                     .reduce(0, Integer::sum);
    }
}
