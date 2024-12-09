package be.neonweb.adventofcode.d05;

import be.neonweb.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class D05 extends Day {

    private Map<Integer, Set<Integer>> rules;
    private List<List<Integer>> updates;

    @Override
    public String part1(List<String> lines) {
        int split = lines.indexOf("");

        rules = lines.stream()
                     .limit(split)
                     .map(s -> {
                         String[] split1 = s.split("\\|");
                         return Pair.of(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]));
                     })
                     .collect(Collectors.toMap(
                             Pair::getLeft,
                             pair -> Set.of(pair.getRight()),
                             (l1, l2) -> {
                                 Set<Integer> newSet = new HashSet<>();
                                 newSet.addAll(l1);
                                 newSet.addAll(l2);
                                 return newSet;
                             })
                     );

        updates = lines.stream()
                       .skip(split + 1)
                       .map(s -> s.split(","))
                       .map(Arrays::asList)
                       .map(strings -> strings.stream()
                                              .map(Integer::parseInt)
                                              .toList())
                       .toList();


        return updates.stream()
                      .filter(this::isValid)
                      .map(l -> l.get(l.size() / 2))
                      .reduce(Integer::sum)
                      .map(Objects::toString)
                      .orElse("-");

    }

    @Override
    public String part2(List<String> lines) {
        return updates.stream()
                      .filter(update -> !isValid(update))
                      .map(this::sort)
                      .map(l -> l.get(l.size() / 2))
                      .reduce(Integer::sum)
                      .map(Objects::toString)
                      .orElse("-");
    }

    private boolean isValid(List<Integer> update) {
        for (int i = 0; i < update.size(); i++) {
            int current = update.get(i);
            for (Integer before : update.subList(0, i)) {
                if (Objects.nonNull(rules.get(current)) && rules.get(current)
                                                                .contains(before)) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> sort(List<Integer> list) {
        ArrayList<Integer> sorted = new ArrayList<>();
        for (Integer i : list) {
            int j = 0;
            while (j < sorted.size() && (!rules.containsKey(i) || !rules.get(i).contains(sorted.get(j)))) {
                j++;
            }
            sorted.add(j, i);
        }
        return sorted;
    }

}
