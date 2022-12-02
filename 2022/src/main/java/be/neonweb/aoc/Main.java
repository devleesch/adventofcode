package be.neonweb.aoc;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Main {

    private static final String DAY = "D03";
    private static final Map<String, Solver> SOLVERS = new HashMap<>();

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.start();
        log.debug("AOC 2022");

        addSolvers();

        SOLVERS.entrySet()
               .stream()
               .filter(entry -> entry.getKey()
                                     .matches(DAY + ".*"))
               .sorted(Map.Entry.comparingByKey())
               .forEach(entry -> entry.getValue()
                                      .run());

        log.info("finished in " + stopwatch.stop());
    }

    private static void addSolvers() {
        Reflections reflections = new Reflections("be.neonweb.aoc.solvers");

        reflections.getSubTypesOf(Solver.class)
                   .forEach(solver -> {
                       log.info("Loading Solver: " + solver.getSimpleName());
                       SOLVERS.put(solver.getSimpleName(), getSolver(solver));
                   });
    }

    private static Integer getIndex(Class<? extends Solver> solver) {
        return Integer.parseInt(solver.getSimpleName()
                                      .substring(1));
    }

    private static Solver getSolver(Class<? extends Solver> solver) {
        try {
            return (Solver) Class.forName(solver.getCanonicalName())
                                 .getConstructor()
                                 .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}