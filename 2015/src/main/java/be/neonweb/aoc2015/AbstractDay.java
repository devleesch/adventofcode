package be.neonweb.aoc2015;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractDay {

    protected List<String> lines;

    public void run() throws IOException {
        StopWatch runSW = new StopWatch();
        runSW.start();
        log.info("starting {}", this.getClass()
                                    .getName());

        File file = loadInput();
        lines = Files.readAllLines(file.toPath());

        List<Supplier<Object>> parts = List.of(this::part1, this::part2);
        AtomicInteger index = new AtomicInteger();
        parts.forEach(part -> {
            int i = index.incrementAndGet();
            StopWatch partSW = new StopWatch();
            log.info("starting part{}", i);
            partSW.start();
            log.info("part{} = {}", index, part.get());
            partSW.stop();
            log.info("end part{} - {}ms", i, partSW.getTotalTime(TimeUnit.MILLISECONDS));
        });

        runSW.stop();
        log.info("end - {}ms", runSW.getTotalTime(TimeUnit.MILLISECONDS));
    }

    private File loadInput() {
        return new File("src/main/resources/" + this.getClass()
                                                    .getSimpleName()
                                                    .toLowerCase() + "/input.txt");
    }

    public abstract Object part1();

    public abstract Object part2();

}
