package be.neonweb.aoc;

import be.neonweb.aoc.solvers.d01.D01;
import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Main {

    private static final int DAY = 1;
    private static final Map<Integer, Solver> SOLVERS = new HashMap<>();

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.start();
        log.debug("AOC 2022");

        addSolvers();

        SOLVERS.get(DAY)
               .run();

        log.info("finished in " + stopwatch.stop());
    }

    private static void addSolvers() {
        SOLVERS.put(1, new D01());
    }

}