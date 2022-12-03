package be.neonweb.aoc.solvers.d03;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class D03a extends Solver {

    protected List<Rucksack> rucksacks = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        Files.readAllLines(Path.of("src/main/resources/d03/input.txt"))
             .forEach(line -> rucksacks.add(Rucksack.of(line)));
    }

    @Override
    public Object solve() throws Exception {
        return rucksacks.stream()
                        .map(Rucksack::common)
                        .map(Rucksack::point)
                        .reduce(0, Integer::sum);
    }
}
