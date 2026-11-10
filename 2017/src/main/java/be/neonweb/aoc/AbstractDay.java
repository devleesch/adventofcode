package be.neonweb.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
public abstract class AbstractDay {

    protected List<String> lines;

    public abstract Object part1();
    public abstract Object part2();

    public List<String> readFile(String fileName) throws IOException {
        if (Objects.isNull(fileName)) {
            fileName = "input.txt";
        }

        Path path = Paths.get("src/main/resources/", this.getClass().getSimpleName().toLowerCase(), fileName);

        log.info("read({})", path);

        return Files.readAllLines(path);
    }

    public void run() throws Exception {
        run(null);
    }

    public void run(String fileName) throws Exception {
        lines = readFile(fileName);

        log.info("part1: {}", part1());
        log.info("part2: {}", part2());
    }

}
