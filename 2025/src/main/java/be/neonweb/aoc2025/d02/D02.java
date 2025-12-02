package be.neonweb.aoc2025.d02;

import be.neonweb.aoc2025.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D02 extends Solver {

    private List<Interval> intervals;

    @Override
    public void parseFile() throws Exception {
        readFile(null);
        String[] split = lines.getFirst()
                              .split(",");
        intervals = Arrays.stream(split)
                          .map(Interval::of)
                          .toList();
    }

    @Override
    public Object part1() {
        return intervals.stream()
                        .map(Interval::invalidIds)
                        .flatMap(List::stream)
                        .reduce(0L, Long::sum);
    }

    @Override
    public Object part2() {
        return intervals.stream()
                        .map(Interval::invalidIds2)
                        .flatMap(List::stream)
                        .reduce(0L, Long::sum);
    }


    public static class Interval {
        private final long start;
        private final long end;

        public Interval(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public static Interval of(String line) {
            String[] parts = line.split("-");
            return new Interval(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }

        public List<Long> invalidIds() {
            List<Long> invalidIds = new ArrayList<>();

            for (long i = start; i <= end; i++) {
                String s = String.valueOf(i);
                if (s.length() % 2 != 0) {
                    continue;
                }

                String left = s.substring(0, s.length() / 2);
                String right = s.substring(s.length() / 2);
                if (left.equals(right)) {
                    invalidIds.add(i);
                }
            }

            return invalidIds;
        }

        public List<Long> invalidIds2() {
            List<Long> invalidIds = new ArrayList<>();

            for (long i = start; i <= end; i++) {
                String s = String.valueOf(i);
                for (int j = 1; j <= s.length() / 2; j++) {
                    if (s.length() % j == 0) {
                        String part = s.substring(0, j);
                        StringBuilder repeat = new StringBuilder();
                        repeat.append(part.repeat(s.length() / j));
                        if (s.contentEquals(repeat)) {
                            invalidIds.add(i);
                            break;
                        }
                    }
                }
            }

            return invalidIds;
        }
    }
}
