package be.neonweb.aoc.utils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Stopwatch {

    private Long start;
    private Long end;

    private Stopwatch() {}

    public static Stopwatch start() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start = System.currentTimeMillis();
        return stopwatch;
    }

    public Stopwatch stop() {
        this.end = System.currentTimeMillis();
        return this;
    }

    public String toString() {
        Long end = Objects.nonNull(this.end) ? this.end : System.currentTimeMillis();
        return Duration.of(end - start, ChronoUnit.MILLIS)
                       .toString();
    }

}
