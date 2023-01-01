package be.neonweb.aoc.solvers.d21;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class D21a extends Solver {

    Map<String, Monkey> monkeys;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        monkeys = Files.readAllLines(Path.of("src/main/resources/d21/input.txt"))
                       .stream()
                       .map(Monkey::of)
                       .collect(Collectors.toMap(Monkey::getName, Function.identity()));
    }

    @Override
    public Object solve() throws Exception {
        return monkeys.get("root")
                      .compute(monkeys);
    }
}
