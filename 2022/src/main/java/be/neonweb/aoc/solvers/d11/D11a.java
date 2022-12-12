package be.neonweb.aoc.solvers.d11;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class D11a extends Solver {

    List<Monkey> monkeys = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        List<String> current = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of("src/main/resources/d11/input.txt"))) {
            if (line.isEmpty()) {
                monkeys.add(Monkey.of(current));
                current = new ArrayList<>();
            } else {
                current.add(line);
            }
        }
        monkeys.add(Monkey.of(current));
    }

    @Override
    public Object solve() throws Exception {
        for (int round = 0; round < 20; round++) {
            monkeys.forEach(monkey -> monkey.inspect(monkeys));
        }

        return monkeys.stream()
                      .sorted(Comparator.comparingLong(Monkey::getInspectedItems)
                                        .reversed())
                      .limit(2)
                      .map(Monkey::getInspectedItems)
                      .reduce(1L, (x, y) -> x * y);
    }
}
