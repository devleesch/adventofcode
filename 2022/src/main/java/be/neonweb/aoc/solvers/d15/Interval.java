package be.neonweb.aoc.solvers.d15;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Data
@Builder
@Slf4j
public class Interval {

    private int start;

    private int end;

    public static List<Interval> reduce(List<Interval> intervals) {
        List<Borne> collect = intervals.stream()
                                       .map(interval -> Arrays.asList(new Borne(interval.getStart(), "["), new Borne(interval.getEnd(), "]")))
                                       .flatMap(Collection::stream)
                                       .sorted(Comparator.comparingInt(Borne::getIndex)
                                                         .thenComparing(Borne::getType))
                                       .toList();

        List<Interval> result = new ArrayList<>();
        Stack<Borne> stack = new Stack<>();
        for (Borne borne : collect) {
            if (borne.getType()
                     .equals("[")) {
                stack.add(borne);
            } else {
                Borne pop = stack.pop();
                if (stack.isEmpty()) {
                    result.add(Interval.builder()
                                       .start(pop.getIndex())
                                       .end(borne.getIndex())
                                       .build());
                }
            }
        }
        return result;
    }

    public int size() {
        return end - start + 1;
    }

}
