package be.neonweb.aoc.solvers.d11;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class D11b extends D11a {

    @Override
    public Object solve() throws Exception {

        List<Integer> rounds = Arrays.asList(1, 20, 1000);

        Integer reducer = monkeys.stream()
                                 .map(monkey -> monkey.getTest()
                                                      .getDivisibleBy())
                                 .reduce(1, (x, y) -> x * y);

        for (int round = 0; round < 10_000; round++) {
            monkeys.forEach(monkey -> monkey.inspect2(monkeys, new BigDecimal(reducer)));

            if (rounds.contains(round + 1)) {
                log.info("== After round " + (round + 1) + " ==");
                for (int i = 0; i < monkeys.size(); i++) {
                    log.info("Monkey " + i + " inspected items " + monkeys.get(i)
                                                                          .getInspectedItems() + " times.");
                }
            }
        }

        return monkeys.stream()
                      .sorted(Comparator.comparingLong(Monkey::getInspectedItems)
                                        .reversed())
                      .limit(2)
                      .map(Monkey::getInspectedItems)
                      .reduce(1L, (x, y) -> x * y);
    }
}
