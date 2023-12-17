package be.neonweb.adventofcode.aoc2023.d09;

import be.neonweb.adventofcode.aoc2023.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D09 extends Solver {

    private List<List<Integer>> sequences;

    public D09(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);

        return sequences.stream()
                        .map(this::deltas)
                        .map(this::next)
                        .reduce(Integer::sum)
                        .orElseThrow();
    }

    @Override
    public Object part2(List<String> inputLines) {
        parse(inputLines);

        return sequences.stream()
                        .map(this::deltas)
                        .map(this::previous)
                        .reduce(Integer::sum)
                        .orElseThrow();
    }

    private Integer previous(List<List<Integer>> sequences) {
        int previous = 0;
        for (int i = sequences.size() - 1; i >= 0; i--) {
            List<Integer> sequence = sequences.get(i);
            previous = sequence.get(0) - previous;
        }
        return previous;
    }

    private List<List<Integer>> deltas(List<Integer> sequence) {
        List<List<Integer>> sequences = new ArrayList<>();
        List<Integer> current = sequence;
        while (!all0(current)) {
            sequences.add(current);
            current = delta(current);
        }
        return sequences;
    }

    private Integer next(List<List<Integer>> sequences) {
        int next = 0;
        for (int i = sequences.size() - 1; i >= 0; i--) {
            List<Integer> sequence = sequences.get(i);
            next = sequence.get(sequence.size() - 1) + next;
        }
        return next;
    }

    private void parse(List<String> inputLines) {
        sequences = inputLines.stream()
                              .map(s -> Arrays.stream(s.split(" "))
                                              .map(Integer::valueOf)
                                              .toList())
                              .toList();
    }

    private List<Integer> delta(List<Integer> sequence) {
        List<Integer> deltas = new ArrayList<>();
        for (int i = 1; i < sequence.size(); i++) {
            deltas.add(sequence.get(i) - sequence.get(i - 1));
        }
        return deltas;
    }

    private boolean all0(List<Integer> sequence) {
        return sequence.stream()
                       .allMatch(v -> v == 0);
    }

    public static void main(String[] args) {
        D09 d09 = new D09("src/main/resources/09/input.txt");
        Solver.run(d09);
    }
}
