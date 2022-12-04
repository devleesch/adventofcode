package be.neonweb.aoc;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Main {

    public static final String SOLVERS_PACKAGE = "be.neonweb.aoc.solvers";
    private static final String DAY = "D04";
    private static final Map<String, Solver> SOLVERS = new HashMap<>();

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.start();
        log.debug("AOC 2022");

        addSolvers();

        SOLVERS.entrySet()
               .stream()
               .filter(entry -> entry.getKey()
                                     .matches(SOLVERS_PACKAGE.replaceAll(".", "\\.") + "\\..*\\." + DAY + ".*"))
               .sorted(Map.Entry.comparingByKey())
               .forEach(entry -> entry.getValue()
                                      .run());

        log.info("finished in " + stopwatch.stop());
    }

    private static void addSolvers() {
        Reflections reflections = new Reflections(SOLVERS_PACKAGE);

        reflections.getSubTypesOf(Solver.class)
                   .forEach(solver -> {
                       log.info("Loading Solver: " + solver.getCanonicalName());
                       SOLVERS.put(solver.getCanonicalName(), getSolver(solver));
                   });
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