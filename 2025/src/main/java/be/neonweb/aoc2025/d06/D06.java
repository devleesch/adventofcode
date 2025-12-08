package be.neonweb.aoc2025.d06;

import be.neonweb.aoc2025.Solver;
import be.neonweb.aoc2025.utils.Tuple;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class D06 extends Solver {

    private static final Pattern PATTERN_VALUES = Pattern.compile("[0-9]+");
    private static final Pattern PATTERN_SIGNS = Pattern.compile("[*+]");

    private List<List<BigDecimal>> values;
    private List<List<BigDecimal>> values2;
    private List<String> signs;

    @Override
    public void parseFile() throws Exception {
        readFile(null);

        List<List<BigDecimal>> temps = lines.subList(0, lines.size() - 1)
                                            .stream()
                                            .map(line -> {
                                                Matcher matcher = PATTERN_VALUES.matcher(line);
                                                List<BigDecimal> values = new ArrayList<>();
                                                while (matcher.find()) {
                                                    values.add(new BigDecimal(matcher.group()));
                                                }
                                                return values;
                                            })
                                            .toList();

        values = new ArrayList<>();
        for (int i = 0; i < temps.getFirst()
                                 .size(); i++) {
            List<BigDecimal> col = new ArrayList<>();
            for (int j = 0; j < temps.size(); j++) {
                col.add(temps.get(j)
                             .get(i));
            }
            values.add(col);
        }

        signs = new ArrayList<>();
        Matcher matcher = PATTERN_SIGNS.matcher(lines.getLast());
        while (matcher.find()) {
            signs.add(matcher.group());
        }
    }

    @Override
    public Object part1() {
        return IntStream.range(0, values.size())
                        .mapToObj(i -> new Tuple<>(values.get(i), signs.get(i)))
                        .map(tuple -> applyOperator(tuple.getLeft(), tuple.getRight()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal applyOperator(List<BigDecimal> values, String operatorStr) {
        BinaryOperator<BigDecimal> operator = operatorStr.equals("+") ? BigDecimal::add : BigDecimal::multiply;
        BigDecimal identity = operatorStr.equals("+") ? BigDecimal.ZERO : BigDecimal.ONE;
        return values.stream()
                     .reduce(identity, operator);
    }

    @Override
    public Object part2() {
        values2 = new ArrayList<>();
        String signStr = lines.getLast();

        List<Integer> indexes = IntStream.range(0, signStr.length())
                                         .filter(index -> signStr.charAt(index) != ' ')
                                         .boxed()
                                         .collect(Collectors.toList());

        Integer length = lines.stream()
                              .map(String::length)
                              .max(Integer::compareTo)
                              .orElseThrow();

        indexes.add(length + 1);

        for (int i = 1; i < indexes.size(); i++) {
            int start = indexes.get(i - 1);
            int end = indexes.get(i);
            List<String> list = lines.subList(0, lines.size() - 1)
                                     .stream()
                                     .map(l -> l.substring(start, Math.min(end - 1, l.length())))
                                     .toList();
            List<BigDecimal> values = new ArrayList<>();
            for (int j = 0; j < list.getFirst()
                                    .length(); j++) {
                StringBuilder nbrStr = new StringBuilder();
                for (int k = 0; k < list.size(); k++) {
                    nbrStr.append(list.get(k)
                                      .charAt(j));
                }
                values.add(new BigDecimal(nbrStr.toString()
                                                .trim()));
            }
            values2.add(values);
        }

        return IntStream.range(0, values2.size())
                        .mapToObj(i -> new Tuple<>(values2.get(i), signs.get(i)))
                        .map(tuple -> applyOperator(tuple.getLeft(), tuple.getRight()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
