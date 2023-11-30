package be.neonweb.adventofcode.aoc2023;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.List;

@Slf4j
public class Aoc01 extends Solver {

    public Aoc01(Path inputPath) {
        super(inputPath);
    }

    @Override
    public void part1(List<String> inputLines) {
        log.info("size: " + inputLines.size());
    }

    @Override
    public void part2(List<String> inputLines) {
        inputLines.forEach(log::info);
    }

    public static void main(String[] args) {
        Aoc01 aoc01 = new Aoc01(Path.of("src/main/resources/01/input.txt"));
        Solver.run(aoc01);
    }

}
