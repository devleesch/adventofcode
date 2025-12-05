package be.neonweb.aoc2025.d05;

import be.neonweb.aoc2025.Solver;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class D05 extends Solver {

    private List<Interval> intervals;
    private List<Long> ids;

    @Override
    public void parseFile() throws Exception {
        readFile(null);

        int separatorIndex = lines.indexOf("");

        intervals = lines.subList(0, separatorIndex)
                         .stream()
                         .map(Interval::of)
                         .toList();

        ids = lines.subList(separatorIndex + 1, lines.size())
                   .stream()
                   .map(Long::parseLong)
                   .toList();
    }

    @Override
    public Object part1() {
        return ids.stream()
                  .filter(id -> intervals.stream()
                                         .anyMatch(interval -> interval.include(id)))
                  .count();
    }

    @Override
    public Object part2() {
        List<Interval> results = new ArrayList<>();
        for (Interval interval : intervals) {
            List<Interval> includes = results.stream()
                                             .filter(it -> interval.include(it) || it.include(interval))
                                             .collect(Collectors.toList());
            if (!includes.isEmpty()) {
                results.removeAll(includes);
            }
            includes.add(interval);
            results.add(Interval.merge(includes));
            results.sort(Comparator.comparing(Interval::getStart));
        }
        return results.stream()
                      .map(interval -> interval.getEnd() - interval.getStart() + 1)
                      .reduce(0L, Long::sum);
    }

    @Getter
    @Setter
    @ToString
    public static class Interval {
        private final long start;
        private final long end;

        public Interval(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return start == interval.start && end == interval.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        public boolean include(long id) {
            return id >= start && id <= end;
        }

        public boolean include(Interval interval) {
            return include(interval.start) || include(interval.end);
        }

        public static Interval merge(List<Interval> includes) {
            Long start = includes.stream()
                                 .map(Interval::getStart)
                                 .min(Long::compareTo)
                                 .orElseThrow();
            Long end = includes.stream()
                               .map(Interval::getEnd)
                               .max(Long::compareTo)
                               .orElseThrow();
            return new Interval(start, end);
        }

        public static Interval of(String line) {
            String[] split = line.split("-");
            return new Interval(Long.parseLong(split[0]), Long.parseLong(split[1]));
        }
    }
}
