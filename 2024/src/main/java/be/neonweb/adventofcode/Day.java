package be.neonweb.adventofcode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public abstract class Day {

    public List<String> read(Path path) throws IOException {
        log.info("Reading file {}", path);
        return Files.readAllLines(path);
    }

    public void run() throws IOException {
        run("input.txt");
    }

    public void run(String fileName) throws IOException {
        run(Path.of("input/" + getDay() + "/" + fileName));
    }

    public void run(Path input) throws IOException {
        log.info("Starting {}", getDay());
        List<String> lines = read(input);

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        log.info("Running part1...");
        String part1 = part1(lines);
        log.info("part 1: {}", part1);
        stopWatch.stop();
        log.info("part 1 done in {}", stopWatch);


        stopWatch.reset();

        stopWatch.start();
        log.info("Running part2...");
        String part2 = part2(lines);
        log.info("part 2: {}", part2);
        stopWatch.stop();
        log.info("part 2 done in {}", stopWatch);

        log.info("End {}", getDay());
    }

    public String getDay() {
        return this.getClass()
                   .getSimpleName()
                   .toLowerCase();
    }

    public abstract String part1(List<String> lines);

    public abstract String part2(List<String> lines);

}
