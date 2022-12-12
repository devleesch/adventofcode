package be.neonweb.aoc.solvers.d11;

import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Operation {

    private static final Pattern operationPattern = Pattern.compile("Operation: new = old ([+*]) (.+)");

    private String sign;

    private String value;

    private Function<Item, Item> function;

    public BigDecimal getNumericValue(Item item) {
        if ("old".equals(value)) {
            return item.getWorryLevel();
        } else {
            return new BigDecimal(value);
        }
    }

    public static Operation of(String s) {
        Operation operation = new Operation();

        Matcher matcher = operationPattern.matcher(s.trim());
        matcher.matches();
        operation.sign = matcher.group(1);
        operation.value = matcher.group(2);
        operation.function = operationOf(operation.sign, operation.value);

        return operation;
    }

    private static Function<Item, Item> operationOf(String sign, String value) {
        if ("old".equals(value)) {
            if ("+".equals(sign)) {
                return item -> new Item(item.getWorryLevel()
                                            .add(item.getWorryLevel()));
            } else if ("*".equals(sign)) {
                return item -> new Item(item.getWorryLevel()
                                            .multiply(item.getWorryLevel()));
            }
        } else {
            if ("+".equals(sign)) {
                return item -> new Item(item.getWorryLevel()
                                            .add(new BigDecimal(value)));
            } else if ("*".equals(sign)) {
                return item -> new Item(item.getWorryLevel()
                                            .multiply(new BigDecimal(value)));
            }
        }
        throw new IllegalArgumentException();
    }

}
