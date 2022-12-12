package be.neonweb.aoc.solvers.d11;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Monkey {
    private List<Item> items = new ArrayList<>();
    private Operation operation;
    private Test test;
    private Long inspectedItems = 0L;

    public void inspect(List<Monkey> monkeys) {
        List<Item> toInspects = items.stream()
                                     .map(item -> new Item(item.getWorryLevel()))
                                     .toList();

        toInspects.forEach(item -> {
            inspectedItems++;
            Item newItem = operation.getFunction()
                                    .apply(item);
            newItem.setWorryLevel(newItem.getWorryLevel()
                                         .divide(new BigDecimal(3), 0, RoundingMode.FLOOR));
            Integer newMonkeyIndex = test.getFunction()
                                         .apply(newItem);
            monkeys.get(newMonkeyIndex)
                   .getItems()
                   .add(newItem);
        });

        items.removeAll(toInspects);
    }

    public void inspect2(List<Monkey> monkeys, BigDecimal reducer) {
        List<Item> toInspects = items.stream()
                                     .map(item -> new Item(item.getWorryLevel()))
                                     .toList();

        toInspects.forEach(item -> {
            inspectedItems++;

            Item temp = new Item(item.getWorryLevel());
            //BigDecimal squareMod = new BigDecimal(test.getDivisibleBy()).pow(2);
            temp.setWorryLevel(temp.getWorryLevel()
                                   .remainder(reducer));

            Item newItem = operation.getFunction()
                                    .apply(temp);
            Integer newMonkeyIndex = test.getFunction()
                                         .apply(newItem);

            /*
            if ("*".equals(operation.getSign())) {
                Monkey nextMonkey = monkeys.get(newMonkeyIndex);
                BigDecimal reduce = nextMonkey.getOperation()
                                              .getFunction()
                                              .apply(newItem)
                                              .getWorryLevel()
                                              .remainder(new BigDecimal(nextMonkey.getTest()
                                                                                  .getDivisibleBy()));
                if ("+".equals(nextMonkey.getOperation()
                                         .getSign())) {
                    while (reduce.compareTo(new BigDecimal(nextMonkey.getOperation()
                                                                     .getValue())) < 1) {
                        reduce = reduce.add(new BigDecimal(nextMonkey.getTest()
                                                                     .getDivisibleBy()));
                    }

                    newItem.setWorryLevel(reduce.subtract(new BigDecimal(nextMonkey.getOperation()
                                                                                   .getValue())));
                }
            }

             */
            monkeys.get(newMonkeyIndex)
                   .getItems()
                   .add(newItem);
        });

        items.removeAll(toInspects);
    }

    public static Monkey of(List<String> lines) {
        Monkey monkey = new Monkey();
        monkey.setItems(itemsOf(lines.get(1)));
        monkey.setOperation(Operation.of(lines.get(2)));
        monkey.setTest(Test.of(lines.subList(3, lines.size())));
        return monkey;
    }

    private static List<Item> itemsOf(String line) {
        return Arrays.stream(line.split(":")[1].replaceAll(" ", "")
                                               .split(","))
                     .map(s -> new Item(new BigDecimal(s)))
                     .collect(Collectors.toList());
    }

}
