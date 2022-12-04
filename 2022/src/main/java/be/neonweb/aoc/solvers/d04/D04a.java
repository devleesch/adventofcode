package be.neonweb.aoc.solvers.d04;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class D04a extends Solver {

    protected List<Pair> pairs = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        pairs = Files.readAllLines(Path.of("src/main/resources/d04/input.txt"))
                     .stream()
                     .map(Pair::of)
                     .collect(Collectors.toList());
    }

    @Override
    public Object solve() throws Exception {
        return pairs.stream()
                    .filter(Pair::contains)
                    .count();

    }
}
