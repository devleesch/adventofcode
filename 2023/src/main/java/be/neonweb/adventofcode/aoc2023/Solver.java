package be.neonweb.adventofcode.aoc2023;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Data
@Slf4j
public abstract class Solver {

    protected Path inputPath;

    public Solver(String inputPath) {
        this.inputPath = Path.of(inputPath);
    }

    public abstract Object part1(List<String> inputLines);

    public abstract Object part2(List<String> inputLines);

    public List<String> readInput() throws IOException {
        return Files.readAllLines(getInputPath(), StandardCharsets.UTF_8);
    }

    public void solve(boolean part1, boolean part2) {
        try {
            List<String> inputLines = readInput();

            long start = System.currentTimeMillis();

            if (part1) {
                log.info("start part1");
                log.info("solution: " + part1(inputLines));
                log.info("end part1 - " + (System.currentTimeMillis() - start) + " ms");

                log.info("");
                log.info("");
            }

            if (part2) {
                start = System.currentTimeMillis();
                log.info("start part2");
                log.info("solution: " + part2(inputLines));
                log.info("end part2 - " + (System.currentTimeMillis() - start) + " ms");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(Solver solver) {
        solver.solve(true, true);
    }

    public static void run(Solver solver, boolean part1, boolean part2) {
        solver.solve(part1, part2);
    }
}
