package be.neonweb.adventofcode.d04;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class D04 extends Day {

    @Override
    public String getDay() {
        return "04";
    }

    @Override
    public String part1(List<String> lines) {
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y)
                                     .length(); x++) {
                points.add(new Point(x, y, lines.get(y)
                                                .charAt(x)));
            }
        }

        Set<Point> deltas = Set.of(
                new Point(0, 1),
                new Point(0, -1),

                new Point(1, 0),
                new Point(-1, 0),

                new Point(1, 1),
                new Point(-1, 1),
                new Point(1, -1),
                new Point(-1, -1)
        );

        return points.stream()
                     .filter(point -> point.value == 'X')
                     .flatMap(pointX -> {
                         List<String> words = new ArrayList<>();
                         for (Point delta : deltas) {
                             StringBuilder stringBuilder = new StringBuilder();
                             Point current = pointX;
                             for (int i = 0; i < 4; i++) {
                                 if (Objects.isNull(current)) {
                                     break;
                                 }
                                 stringBuilder.append(current.value);
                                 current = current.get(points, delta.x, delta.y)
                                                  .orElse(null);
                             }
                             String word = stringBuilder.toString();
                             if (word.length() == 4) {
                                 words.add(stringBuilder.toString());
                             }
                         }
                         return words.stream();
                     })
                     .filter("XMAS"::equals)
                     .count() + "";

    }

    @Override
    public String part2(List<String> lines) {
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y)
                                     .length(); x++) {
                points.add(new Point(x, y, lines.get(y)
                                                .charAt(x)));
            }
        }

        return points.stream()
                     .filter(point -> point.value == 'A')
                     .map(point -> {
                         List<String> words = new ArrayList<>();

                         words.add(Stream.of(point.get(points, -1, -1), Optional.of(point), point.get(points, 1, 1))
                                         .flatMap(Optional::stream)
                                         .map(p -> Character.toString(p.value))
                                         .collect(Collectors.joining()));

                         words.add(Stream.of(point.get(points, 1, -1), Optional.of(point), point.get(points, -1, 1))
                                         .flatMap(Optional::stream)
                                         .map(p -> Character.toString(p.value))
                                         .collect(Collectors.joining()));

                         return words;
                     })
                     .filter(words -> words.stream()
                                           .allMatch(word -> word.length() == 3))
                     .filter(words -> words.stream()
                                           .allMatch(word -> "MAS".equals(word) || "SAM".equals(word)))
                     .count() + "";
    }

    @Getter
    @Setter
    @ToString
    public static class Point {
        private int x;
        private int y;
        private Character value;

        public Point(int x, int y) {
            this(x, y, null);
        }

        public Point(int x, int y, Character value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public Optional<Point> get(List<Point> points, int dx, int dy) {
            int nx = this.x + dx;
            if (nx < 0) return Optional.empty();

            int ny = this.y + dy;
            if (ny < 0) return Optional.empty();

            return points.stream()
                         .filter(point -> point.x == nx && point.y == ny)
                         .findFirst();
        }
    }
}
