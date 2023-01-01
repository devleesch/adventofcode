package be.neonweb.aoc.solvers.d17;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class D17a extends Solver {

    List<Direction> jets;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        jets = Files.readAllLines(Path.of("src/main/resources/d17/test1.txt.txt"))
                    .stream()
                    .map(line -> line.split(""))
                    .flatMap(Arrays::stream)
                    .map(Direction::of)
                    .collect(Collectors.toList());

    }

    @Override
    public Object solve() throws Exception {
        Chamber chamber = new Chamber(9);

        for (int i = 0; i < 2022; i++) {
            chamber.turn(jets);
        }

        return chamber.getHeight();
    }
}
