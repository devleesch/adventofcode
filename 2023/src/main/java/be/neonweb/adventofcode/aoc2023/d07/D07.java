package be.neonweb.adventofcode.aoc2023.d07;

import be.neonweb.adventofcode.aoc2023.Solver;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

public class D07 extends Solver {

    private List<Hand> hands;

    public D07(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);

        List<Hand> sortedHands = hands.stream()
                                      .sorted()
                                      .toList();

        return IntStream.range(0, sortedHands.size())
                        .mapToObj(i -> IndexedHand.of(i + 1, sortedHands.get(i)))
                        .map(tuple -> tuple.getValue()
                                           .getBid()
                                           .multiply(new BigInteger(tuple.getIndex()
                                                                         .toString())))
                        .reduce(BigInteger.ZERO, BigInteger::add);
    }

    @Override
    public Object part2(List<String> inputLines) {
        return null;
    }

    private void parse(List<String> inputLines) {
        hands = inputLines.stream()
                          .map(Hand::of)
                          .toList();
    }

    public static void main(String[] args) {
        D07 d07 = new D07("src/main/resources/07/input.txt");
        Solver.run(d07);
    }
}
