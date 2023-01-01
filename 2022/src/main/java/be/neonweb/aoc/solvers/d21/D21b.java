package be.neonweb.aoc.solvers.d21;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class D21b extends D21a {

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        monkeys = Files.readAllLines(Path.of("src/main/resources/d21/input.txt"))
                       .stream()
                       .map(Monkey::of)
                       .collect(Collectors.toMap(Monkey::getName, Function.identity()));
    }

    @Override
    public Object solve() throws Exception {
        long i = 3848300000000L;

        while (true) {
            try {
                boolean evaluate = evaluate(i);
                if (evaluate) {
                    return i;
                }
                i += 7203;
            } catch (Exception e) {
                i++;
            }
        }
    }

    private boolean evaluate(long i) {
        BigDecimal o2Value = monkeys.get(monkeys.get("root")
                                                .getO2())
                                    .compute(monkeys);

        Map<String, Monkey> copy = monkeys.values()
                                          .stream()
                                          .map(Monkey::copy)
                                          .collect(Collectors.toMap(Monkey::getName, Function.identity()));

        Monkey root = copy.get("root");

        Monkey me = copy.get("humn");
        me.setValue(new BigDecimal(i));

        BigDecimal o1Value = copy.get(root.getO1())
                                 .compute(copy);

        log.debug(i + " ?= ");
        log.debug("" + o1Value.setScale(0));
        log.debug("" + o2Value);
        System.out.println();

        if (o1Value.equals(o2Value)) {
            return true;
        }

        return false;
    }
}
