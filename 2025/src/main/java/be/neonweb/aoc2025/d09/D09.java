package be.neonweb.aoc2025.d09;

import be.neonweb.aoc2025.Solver;
import be.neonweb.aoc2025.utils.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
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

        List<Point> minXs = getBorder(points,
                Point::getX,
                stream -> stream.min(Integer::compareTo),
                Comparator.comparing(Point::getY)
                          .reversed()
        );
        polygon.getPoints()
               .addAll(minXs);

        List<Point> minYs = getBorder(points,
                Point::getY,
                stream -> stream.min(Integer::compareTo),
                Comparator.comparing(Point::getX)
        );

        Point c1 = new Point(minYs.getFirst()
                                  .getX(), minXs.getLast()
                                                .getY());
        polygon.getPoints()
               .add(points.stream()
                          .filter(point -> point.equals(c1))
                          .findFirst()
                          .orElseThrow());

        polygon.getPoints()
               .addAll(minYs);

        List<Point> maxXs = getBorder(points,
                Point::getX,
                stream -> stream.max(Integer::compareTo),
                Comparator.comparing(Point::getY)
        );

        Point c2 = new Point(minYs.getLast()
                                  .getX(), maxXs.getFirst()
                                                .getY());
        polygon.getPoints()
               .add(points.stream()
                          .filter(point -> point.equals(c2))
                          .findFirst()
                          .orElseThrow());

        polygon.getPoints()
               .addAll(maxXs);

        List<Point> maxYs = getBorder(points,
                Point::getY,
                stream -> stream.max(Integer::compareTo),
                Comparator.comparing(Point::getX)
                          .reversed()
        );

        Point c3 = new Point(maxYs.getFirst()
                                  .getX(), maxXs.getLast()
                                                .getY());
        polygon.getPoints()
               .add(points.stream()
                          .filter(point -> point.equals(c3))
                          .findFirst()
                          .orElseThrow());

        polygon.getPoints()
               .addAll(maxYs);

        Point c4 = new Point(maxYs.getLast()
                                  .getX(), minXs.getFirst()
                                                .getY());
        polygon.getPoints()
               .add(points.stream()
                          .filter(point -> point.equals(c4))
                          .findFirst()
                          .orElseThrow());

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
