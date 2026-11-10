package be.neonweb.aoc.d02;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.neonweb.aoc.AbstractDay;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class D02 extends AbstractDay {

    public static void main() throws Exception {
        D02 d02 = new D02();
        d02.run();
    }

    @Override
    public Object part1() {
        return lines.stream()
            .mapToInt(l -> {
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                for (String split : l.split("\t")) {
                    int current = Integer.valueOf(split);
                    if (current < min) {
                        min = current;
                    }
                    if (current > max) {
                        max = current;
                    }
                }
                return max - min;
            }).sum();
    }

    @Override
    public Object part2() {
        return lines.stream()
            .map(l -> {
                List<Integer> values = new ArrayList<>();
                for (String split : l.split("\t")) {
                    values.add(Integer.valueOf(split));
                }
                return values;
            })
            .mapToInt(values -> {
                for (int i = 0; i < values.size(); i++) {
                    for (int j = 0; j < values.size(); j++) {
                        if (i != j) {
                            int left = values.get(i);
                            int right = values.get(j);
                            if (left % right == 0) {
                                return left / right;
                            }
                        }
                    }
                }
                throw new IllegalStateException();
            })
            .sum();
    }

}
