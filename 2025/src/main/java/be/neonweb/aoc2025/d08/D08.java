package be.neonweb.aoc2025.d08;

import be.neonweb.aoc2025.Solver;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class D08 extends Solver {

    private List<Box> boxes;
    private Set<Set<Box>> circuits = new HashSet<>();

    @Override
    public void parseFile() throws Exception {
        readFile(null);
        boxes = lines.stream()
                     .map(Box::of)
                     .toList();
    }

    @Override
    public Object part1() {
        List<BoxDistance> distances = boxes.stream()
                                           .map(box -> boxes.stream()
                                                            .filter(box1 -> !box1.equals(box))
                                                            .map(box1 -> new BoxDistance(box, box1, box.distance(box1)))
                                                            .toList())
                                           .flatMap(List::stream)
                                           .distinct()
                                           .sorted(Comparator.comparing(BoxDistance::getDistance)
                                                             .reversed())
                                           .toList();

        Stack<BoxDistance> stack = new Stack<>();
        stack.addAll(distances);

        Set<Box> circuit = null;
        for (int i = 0; i < 10; i++) {
            BoxDistance pop = stack.pop();

            List<Set<Box>> matches = circuits.stream()
                                             .filter(it -> it.contains(pop.getA()) || it.contains(pop.getB()))
                                             .toList();

            if (matches.isEmpty()) {
                circuit = new HashSet<>();
                circuits.add(circuit);
            } else if (matches.size() == 1) {
                circuit = matches.getFirst();
                circuits.remove(circuit);
            } else {
                circuit = matches.stream()
                                 .flatMap(Collection::stream)
                                 .collect(Collectors.toSet());
                matches.forEach(circuits::remove);
            }

            circuit.add(pop.getA());
            circuit.add(pop.getB());
            circuits.add(circuit);
        }

        return circuits.stream()
                       .map(Set::size)
                       .sorted(Comparator.comparing(Integer::intValue)
                                         .reversed())
                       .toList()
                       .subList(0, 3)
                       .stream()
                       .reduce(1, (a, b) -> a * b);
    }

    @Override
    public Object part2() {
        circuits = boxes.stream()
                        .map(Set::of)
                        .collect(Collectors.toSet());

        List<BoxDistance> distances = boxes.stream()
                                           .map(box -> boxes.stream()
                                                            .filter(box1 -> !box1.equals(box))
                                                            .map(box1 -> new BoxDistance(box, box1, box.distance(box1)))
                                                            .toList())
                                           .flatMap(List::stream)
                                           .distinct()
                                           .sorted(Comparator.comparing(BoxDistance::getDistance)
                                                             .reversed())
                                           .toList();

        Stack<BoxDistance> stack = new Stack<>();
        stack.addAll(distances);

        Set<Box> circuit = null;
        BoxDistance last = null;
        while (!stack.isEmpty() && circuits.size() > 1) {
            BoxDistance pop = stack.pop();

            List<Set<Box>> matches = circuits.stream()
                                             .filter(it -> it.contains(pop.getA()) || it.contains(pop.getB()))
                                             .toList();

            if (matches.isEmpty()) {
                circuit = new HashSet<>();
            } else if (matches.size() == 1) {
                circuit = matches.getFirst();
                circuits.remove(circuit);
            } else {
                circuit = matches.stream()
                                 .flatMap(Collection::stream)
                                 .collect(Collectors.toSet());
                matches.forEach(circuits::remove);
            }

            last = pop;
            circuit.add(pop.getA());
            circuit.add(pop.getB());
            circuits.add(circuit);
        }

        return last.getA()
                   .getX() * last.getB()
                                 .getX();
    }

    @Getter
    @Setter
    @ToString
    public static class Box {
        private int x;
        private int y;
        private int z;

        public Box(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public BigDecimal distance(Box other) {
            return BigDecimal.ZERO.add(BigDecimal.valueOf(this.x)
                                                 .subtract(BigDecimal.valueOf(other.x))
                                                 .pow(2))
                                  .add(BigDecimal.valueOf(this.y)
                                                 .subtract(BigDecimal.valueOf(other.y))
                                                 .pow(2))
                                  .add(BigDecimal.valueOf(this.z)
                                                 .subtract(BigDecimal.valueOf(other.z))
                                                 .pow(2))
                                  .sqrt(MathContext.DECIMAL64);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Box box = (Box) o;
            return x == box.x && y == box.y && z == box.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        public static Box of(String l) {
            String[] split = l.split(",");
            return new Box(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
    }

    @Getter
    @Setter
    public static class BoxDistance {
        private Box a;
        private Box b;
        private BigDecimal distance;

        public BoxDistance(Box a, Box b, BigDecimal distance) {
            this.a = a;
            this.b = b;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            BoxDistance that = (BoxDistance) o;
            return (Objects.equals(a, that.a) && Objects.equals(b, that.b)) || (Objects.equals(a, that.b) && Objects.equals(b, that.a));
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b) * Objects.hash(b, a);
        }
    }
}
