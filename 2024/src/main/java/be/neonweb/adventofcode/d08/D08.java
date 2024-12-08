package be.neonweb.adventofcode.d08;

import be.neonweb.adventofcode.Day;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class D08 extends Day {

    private Set<Position> positions;
    private int x0;
    private int y0;
    private int x1;
    private int y1;

    @Override
    public String getDay() {
        return "08";
    }

    @Override
    public String part1(List<String> lines) {
        positions = IntStream.range(0, lines.size())
                             .mapToObj(y -> IntStream.range(0, lines.get(y)
                                                                    .length())
                                                     .mapToObj(x -> {
                                                         char c = lines.get(y)
                                                                       .charAt(x);
                                                         return new Position(x, y, c);
                                                     })
                                                     .filter(p -> Objects.nonNull(p.antenna)))
                             .flatMap(positionStream -> positionStream)
                             .collect(Collectors.toSet());

        x0 = 0;
        y0 = 0;
        x1 = lines.getFirst()
                  .length() - 1;
        y1 = lines.size() - 1;

        return positions.stream()
                        .collect(Collectors.groupingBy(p -> p.antenna))
                        .values()
                        .stream()
                        .flatMap(this::antinodes)
                        .distinct()
                        .count() + "";
    }

    private Stream<Position> antinodes(List<Position> antennas) {
        return antennas.stream()
                       .flatMap(a -> antennas.stream()
                                             .filter(b -> !a.equals(b))
                                             .map(b -> Distance.between(a, b))
                                             .map(d -> a.apply(d)
                                                        .apply(d))
                                             .filter(this::isInside));
    }

    private boolean isInside(Position p) {
        return p.x >= x0 && p.y >= y0
                && p.x <= x1 && p.y <= y1;
    }

    @Override
    public String part2(List<String> lines) {
        return positions.stream()
                        .collect(Collectors.groupingBy(p -> p.antenna))
                        .values()
                        .stream()
                        .flatMap(this::antinodesWithResonantHarmonic)
                        .distinct()
                        .count() + "";
    }

    private Stream<Position> antinodesWithResonantHarmonic(List<Position> antennas) {
        return antennas.stream()
                       .flatMap(a -> antennas.stream()
                                             .filter(b -> !a.equals(b))
                                             .map(b -> Distance.between(a, b))
                                             .flatMap(d -> {
                                                 Position current = a.apply(d);
                                                 Set<Position> p = new HashSet<>();
                                                 while (isInside(current)) {
                                                     p.add(current);
                                                     current = current.apply(d);
                                                 }
                                                 return p.stream();
                                             }));
    }

    @ToString
    public static class Position {
        private int x;
        private int y;
        private Character antenna;

        public Position(int x, int y) {
            this(x, y, null);
        }

        public Position(int x, int y, Character antenna) {
            this.x = x;
            this.y = y;
            this.antenna = Objects.nonNull(antenna) && '.' != antenna ? antenna : null;
        }

        public Position apply(Distance distance) {
            return new Position(this.x + distance.x, this.y + distance.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static class Distance {
        private int x;
        private int y;

        public Distance(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Distance between(Position a, Position b) {
            return new Distance(b.x - a.x, b.y - a.y);
        }
    }

    private Optional<Position> get(int x, int y) {
        return positions.stream()
                        .filter(p -> p.x == x)
                        .filter(p -> p.y == y)
                        .findFirst();
    }
}
