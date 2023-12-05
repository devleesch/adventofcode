package be.neonweb.adventofcode.aoc2023.d04;

import be.neonweb.adventofcode.aoc2023.Solver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D04 extends Solver {

    private List<Card> cards;

    public D04(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);

        return cards.stream()
                    .mapToDouble(Card::points)
                    .sum();
    }

    @Override
    public Object part2(List<String> inputLines) {
        Map<Long, Card> cardById = cards.stream()
                                           .collect(Collectors.toMap(Card::getId, card -> card));
        Map<Long, Long> repartition = cards.stream()
                                                 .collect(Collectors.toMap(Card::getId, card -> 1L));

        for (long i = 1; i <= cards.size(); i++) {
            for (long m = 1; m <= cardById.get(i)
                                          .matches(); m++) {
                long index = i + m;
                if (index <= cards.size()) {
                    long value = repartition.get(index);
                    long add = repartition.get(i);
                    repartition.put(index, value + add);
                }
            }
        }

        return repartition.values()
                          .stream()
                          .mapToLong(value -> value)
                          .sum();
    }

    private void parse(List<String> inputLines) {
        cards = inputLines.stream()
                          .map(Card::parse)
                          .toList();
    }

    public static void main(String[] args) {
        D04 d04 = new D04("src/main/resources/04/input.txt");
        Solver.run(d04);
    }
}
