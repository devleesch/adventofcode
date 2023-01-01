package be.neonweb.aoc.solvers.d21;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class Monkey {

    private static final Pattern OPERATION = Pattern.compile("([a-z]+): ([a-z]+) ([+\\-*/]) ([a-z]+)");
    private static final Pattern VALUE = Pattern.compile("([a-z]+): ([0-9]+)");

    private String name;
    private String o1;
    private String sign;
    private String o2;
    private BigDecimal value;

    public BigDecimal compute(Map<String, Monkey> monkeys) {

        if (Objects.isNull(value)) {
            this.value = switch (sign) {
                case "+" -> monkeys.get(o1)
                                   .compute(monkeys).add(monkeys.get(o2)
                                                              .compute(monkeys));
                case "-" -> monkeys.get(o1)
                                   .compute(monkeys).subtract(monkeys.get(o2)
                                                              .compute(monkeys));
                case "*" -> monkeys.get(o1)
                                   .compute(monkeys).multiply(monkeys.get(o2)
                                                              .compute(monkeys));
                case "/" -> monkeys.get(o1)
                                   .compute(monkeys).divide(monkeys.get(o2)
                                                              .compute(monkeys));
                default -> throw new IllegalArgumentException();
            };
        }

        return value;
    }

    public static Monkey of(String s) {
        Monkey monkey = new Monkey();

        Matcher operationMatcher = OPERATION.matcher(s);
        Matcher valueMatcher = VALUE.matcher(s);

        if (operationMatcher.matches()) {
            monkey.setName(operationMatcher.group(1));
            monkey.setO1(operationMatcher.group(2));
            monkey.setSign(operationMatcher.group(3));
            monkey.setO2(operationMatcher.group(4));
        } else if (valueMatcher.matches()) {
            monkey.setName(valueMatcher.group(1));
            monkey.setValue(new BigDecimal(valueMatcher.group(2)));
        } else {
            throw new IllegalArgumentException();
        }

        return monkey;
    }

    public Monkey copy() {
        Monkey copy = new Monkey();
        copy.name = name;
        copy.o1 = o1;
        copy.sign = sign;
        copy.o2 = o2;
        copy.value = value;
        return copy;
    }

}
