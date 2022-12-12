package be.neonweb.aoc.solvers.d11;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Test {

    private static final Pattern testPattern = Pattern.compile("Test: divisible by ([0-9]+)");
    private static final Pattern testBoolPattern = Pattern.compile("If (true|false): throw to monkey ([0-9]+)");

    private Integer divisibleBy;

    private Function<Item, Integer> function;

    public static Test of(List<String> lines) {
        Test test = new Test();

        Matcher matcher = testPattern.matcher(lines.get(0)
                                                   .trim());
        matcher.matches();

        test.divisibleBy = Integer.parseInt(matcher.group(1));
        test.function = testOf(test.divisibleBy, lines);

        return test;
    }

    private static Function<Item, Integer> testOf(Integer divisibleBy, List<String> lines) {
        Matcher trueMatcher = testBoolPattern.matcher(lines.get(1)
                                                           .trim());
        trueMatcher.matches();
        int trueIndex = Integer.parseInt(trueMatcher.group(2));

        Matcher falseMatcher = testBoolPattern.matcher(lines.get(2)
                                                            .trim());
        falseMatcher.matches();
        int falseIndex = Integer.parseInt(falseMatcher.group(2));

        return item -> item.getWorryLevel()
                           .remainder(new BigDecimal(divisibleBy))
                           .equals(BigDecimal.ZERO) ? trueIndex : falseIndex;
    }

}
