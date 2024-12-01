package be.neonweb.adventofcode.d01;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class D01 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input/d01/input.txt"));

        List<Long> left = new ArrayList<>();
        List<Long> right = new ArrayList<>();

        lines.stream()
             .map(Pair::from)
             .forEach(pair -> {
                 left.add(pair.getLeft());
                 right.add(pair.getRight());
             });

        Stack<Long> stackLeft = new Stack<>();
        stackLeft.addAll(left.stream()
                             .sorted(Comparator.reverseOrder())
                             .toList());

        Stack<Long> stackRight = new Stack<>();
        stackRight.addAll(right.stream()
                               .sorted(Comparator.reverseOrder())
                               .toList());

        List<Pair> sortedPairs = new ArrayList<>();
        while (!stackLeft.isEmpty()) {
            Long l = stackLeft.pop();
            Long r = stackRight.pop();
            sortedPairs.add(new Pair(l, r));
        }

        sortedPairs.stream()
                   .map(Pair::distance)
                   .reduce(Long::sum)
                   .ifPresent(System.out::println);

        left.stream()
            .map(l -> right.stream()
                           .filter(r -> r.equals(l))
                           .count() * l)
            .reduce(Long::sum)
            .ifPresent(System.out::println);
    }

    @Getter
    @Setter
    @ToString
    public static class Pair {
        private Long left;
        private Long right;

        public Pair(Long left, Long right) {
            this.left = left;
            this.right = right;
        }

        public static Pair from(String line) {
            String[] split = line.split(" {3}");
            return new Pair(Long.parseLong(split[0]), Long.parseLong(split[1]));
        }

        public Long distance() {
            return Math.abs(this.left - this.right);
        }
    }

}
