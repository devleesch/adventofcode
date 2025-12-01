package be.neonweb.aoc2025;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class Solver {

    protected List<String> lines;

    public abstract void parseFile() throws Exception;

    public abstract Object part1();

    public abstract Object part2();

    public void readFile(String fileName) throws IOException {
        if (Objects.isNull(fileName)) {
            fileName = "input.txt";
        }

        this.lines = Files.readAllLines(Path.of("src/main/resources",
                this.getClass()
                    .getSimpleName()
                    .toLowerCase(),
                fileName));
    }

    public void run() throws Exception {
        Instant start = Instant.now();

        parseFile();

        Instant start1 = Instant.now();
        Object result1 = part1();
        log.info("part1 = {}", result1);
        log.info("part1 done in {}", Duration.between(start1, Instant.now()));

        Instant start2 = Instant.now();
        Object result2 = part2();
        log.info("part2 = {}", result2);
        log.info("part2 done in {}", Duration.between(start2, Instant.now()));

        log.info("done in {}", Duration.between(start, Instant.now()));
    }
}
