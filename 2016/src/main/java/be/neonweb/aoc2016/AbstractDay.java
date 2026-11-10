package be.neonweb.aoc2016;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractDay {

    protected List<String> lines;

    public void run() throws IOException {
        Instant runSW = Instant.now();
        log.info("starting {}", this.getClass()
                                    .getName());

        File file = loadInput();
        lines = Files.readAllLines(file.toPath());

        List<Supplier<Object>> parts = List.of(this::part1, this::part2);
        AtomicInteger index = new AtomicInteger();
        parts.forEach(part -> {
            int i = index.incrementAndGet();
            Instant partSW = Instant.now();
            log.info("starting part{}", i);
            log.info("part{} = {}", index, part.get());
            log.info("end part{} - {}ms", i, Duration.between(partSW, Instant.now()));
        });

        log.info("end - {}ms", Duration.between(runSW, Instant.now()));
    }

    private File loadInput() {
        return new File("src/main/resources/" + this.getClass()
                                                    .getSimpleName()
                                                    .toLowerCase() + "/" + this.getFileName());
    }

    public String getFileName() {
        return "input.txt";
    }

    public abstract Object part1();

    public abstract Object part2();

}
