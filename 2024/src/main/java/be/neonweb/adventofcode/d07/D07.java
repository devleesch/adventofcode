package be.neonweb.adventofcode.d07;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.paukov.combinatorics3.Generator;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class D07 extends Day {
    @Override
    public String getDay() {
        return "07";
    }

    @Override
    public String part1(List<String> lines) {
        return lines.stream()
                    .filter(StringUtils::isNotBlank)
                    .map(Check::from)
                    .filter(D07::exists)
                    .map(check -> check.expected)
                    .reduce(Long::sum)
                    .map(Objects::toString)
                    .orElse("-");
    }

    public static boolean exists(Check check) {
        return Generator.permutation(Operator.ADD, Operator.MUL)
                        .withRepetitions(check.values.size() - 1)
                        .stream()
                        .anyMatch(check::isValid);
    }

    public static boolean exists2(Check check) {
        return Generator.permutation(Operator.ADD, Operator.MUL, Operator.CONCAT)
                        .withRepetitions(check.values.size() - 1)
                        .stream()
                        .anyMatch(check::isValid);
    }

    @Override
    public String part2(List<String> lines) {
        return lines.stream()
                    .filter(StringUtils::isNotBlank)
                    .map(Check::from)
                    .filter(D07::exists2)
                    .map(check -> check.expected)
                    .reduce(Long::sum)
                    .map(Objects::toString)
                    .orElse("-");
    }

    @Getter
    @Setter
    @ToString
    public static class Check {
        private Long expected;
        private List<Long> values;

        public static Check from(String line) {
            String[] split = line.split(":");

            Check check = new Check();
            check.expected = Long.parseLong(split[0]);
            check.values = Stream.of(split[1].split(" "))
                                 .filter(StringUtils::isNotBlank)
                                 .map(Long::parseLong)
                                 .toList();
            return check;
        }

        public boolean isValid(List<Operator> operators) {
            Long result = values.getFirst();
            for (int i = 1; i < values.size(); i++) {
                result = operators.get(i - 1).compute.apply(result, values.get(i));
            }
            return result.equals(expected);
        }
    }

    public enum Operator {
        ADD("+", Long::sum),
        MUL("*", (a, b) -> a * b),
        CONCAT("||", (a, b) -> Long.parseLong(a.toString() + b.toString()));

        private String sign;
        private BiFunction<Long, Long, Long> compute;

        Operator(String sign, BiFunction<Long, Long, Long> compute) {
            this.sign = sign;
            this.compute = compute;
        }
    }
}
