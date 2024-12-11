package be.neonweb.adventofcode.d10;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
public class D10 extends Day {
    @Override
    public String part1(List<String> lines) {
        List<Height> heights = IntStream.range(0, lines.size())
                                        .mapToObj(y -> IntStream.range(0, lines.get(y)
                                                                               .length())
                                                                .mapToObj(x -> new Height(x, y, Integer.parseInt(lines.get(y)
                                                                                                                      .substring(x, x + 1)))))
                                        .flatMap(heightStream -> heightStream)
                                        .toList();

        return heights.stream()
                      .filter(height -> height.value == 0)
                      .map(height -> score(height, heights))
                      .reduce(Long::sum)
                      .map(Object::toString)
                      .orElseThrow();
    }

    private long score(Height height, List<Height> heights) {
        List<Height> currents = List.of(height);
        while (!currents.isEmpty() && currents.getFirst().value < 9) {
            int value = currents.getFirst().value;
            currents = currents.stream()
                               .flatMap(h -> h.adjacents(heights)
                                              .stream())
                               .filter(h -> h.value == value + 1)
                               .toList();
        }

        return currents.stream()
                       .distinct()
                       .count();
    }

    @Override
    public String part2(List<String> lines) {
        List<Height> heights = IntStream.range(0, lines.size())
                                        .mapToObj(y -> IntStream.range(0, lines.get(y)
                                                                               .length())
                                                                .mapToObj(x -> new Height(x, y, Integer.parseInt(lines.get(y)
                                                                                                                      .substring(x, x + 1)))))
                                        .flatMap(heightStream -> heightStream)
                                        .toList();

        return heights.stream()
                      .filter(height -> height.value == 0)
                      .map(height -> distinctTrails(height, heights))
                      .reduce(Long::sum)
                      .map(Object::toString)
                      .orElseThrow();
    }

    private long distinctTrails(Height height, List<Height> heights) {
        Set<List<Height>> trails = new HashSet<>();
        trails.add(List.of(height));

        int value = height.value;
        while (value < 9 && trails.stream()
                                  .map(path -> path.stream()
                                                   .map(h -> h.value)
                                                   .max(Integer::compare)
                                                   .orElseThrow())
                                  .findFirst()
                                  .orElseThrow() < 9) {

            int nextValue = value + 1;
            Set<List<Height>> newTrails = new HashSet<>();
            for (List<Height> trail : trails) {
                trail.getLast()
                     .adjacents(heights)
                     .stream()
                     .filter(h -> h.value == nextValue)
                     .forEach(h -> {
                         ArrayList<Height> newTrail = new ArrayList<>(trail);
                         newTrail.add(h);
                         newTrails.add(newTrail);

                     });
            }

            trails = newTrails;
            value = nextValue;
        }

        return trails.stream()
                     .filter(trail -> trail.getLast().value == 9)
                     .count();
    }

    @ToString
    @Getter
    public static class Height {
        private int x;
        private int y;
        private int value;

        public Height(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public Optional<Height> left(List<Height> heights) {
            return get(this.x - 1, this.y, heights);
        }

        public Optional<Height> right(List<Height> heights) {
            return get(this.x + 1, this.y, heights);
        }

        public Optional<Height> top(List<Height> heights) {
            return get(this.x, this.y - 1, heights);
        }

        public Optional<Height> down(List<Height> heights) {
            return get(this.x, this.y + 1, heights);
        }

        public List<Height> adjacents(List<Height> heights) {
            List<Height> adjacents = new ArrayList<>();

            left(heights).ifPresent(adjacents::add);
            right(heights).ifPresent(adjacents::add);
            top(heights).ifPresent(adjacents::add);
            down(heights).ifPresent(adjacents::add);

            return adjacents;
        }

        public Optional<Height> get(int x, int y, List<Height> heights) {
            return heights.stream()
                          .filter(height -> height.x == x)
                          .filter(height -> height.y == y)
                          .findFirst();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Height height = (Height) o;
            return x == height.x && y == height.y && value == height.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, value);
        }
    }
}
