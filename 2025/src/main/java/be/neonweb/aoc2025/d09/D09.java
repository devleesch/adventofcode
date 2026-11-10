package be.neonweb.aoc2025.d09;

import be.neonweb.aoc2025.Solver;
import be.neonweb.aoc2025.utils.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class D09 extends Solver {

    private List<Point> points;

    @Override
    public void parseFile() throws Exception {
//        readFile("test1.txt");
        readFile(null);

        points = lines.stream()
                      .map(line -> {
                          String[] split = line.split(",");
                          return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                      })
                      .toList();
    }

    @Override
    public Object part1() {
        return points.stream()
                     .map(a -> points.stream()
                                     .filter(b -> !b.equals(a))
                                     .map(b -> new Rectangle(a, b))
                                     .toList())
                     .flatMap(Collection::stream)
                     .map(Rectangle::area)
                     .max(BigDecimal::compareTo)
                     .orElseThrow();
    }

    @Override
    public Object part2() {
        Polygon polygon = new Polygon();

        Map<Integer, List<Point>> collect = points.stream()
                                                  .collect(Collectors.groupingBy(Point::getX));

        return null;
    }

    private static List<Point> getBorder(List<Point> points,
                                         Function<Point, Integer> mapper,
                                         Function<Stream<Integer>, Optional<Integer>> operator,
                                         Comparator<Point> comparator) {
        Stream<Point> stream = points.stream();
        Integer value = operator.apply(stream.map(mapper))
                                .orElseThrow();

        List<Point> values = points.stream()
                                   .filter(point -> mapper.apply(point)
                                                          .equals(value))
                                   .sorted(comparator)
                                   .toList();

        return List.of(values.getFirst(), values.getLast());
    }

    @Getter
    @Setter
    @ToString
    public static class Rectangle {

        private Point a;
        private Point b;

        public Rectangle(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        public BigDecimal area() {
            return BigDecimal.valueOf(a.getX())
                             .subtract(BigDecimal.valueOf(b.getX()))
                             .abs()
                             .add(BigDecimal.ONE)
                             .multiply(BigDecimal.valueOf(a.getY())
                                                 .subtract(BigDecimal.valueOf(b.getY()))
                                                 .abs()
                                                 .add(BigDecimal.ONE));
        }
    }

    @Getter
    @Setter
    public static class Polygon {

        private Set<Point> points = new LinkedHashSet<>();

        public boolean isInside(Rectangle rectangle) {
            return true;
        }

    }
}
