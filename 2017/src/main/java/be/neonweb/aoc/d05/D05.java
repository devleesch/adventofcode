package be.neonweb.aoc.d05;

import java.util.List;
import java.util.stream.Collectors;

import be.neonweb.aoc.AbstractDay;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class D05 extends AbstractDay {

    public static void main(String[] args) throws Exception {
        D05 d05 = new D05();
        d05.run();
    }

    @Override
    public Object part1() {
        List<Integer> stack = lines.stream()
            .map(l -> Integer.valueOf(l))
            .collect(Collectors.toList());
        
        int i = 0;
        int c = 0;
        while (i < stack.size()) {
            int current = stack.get(i);
            stack.set(i, current + 1);

            i += current;

            c++;
        }

        return c;
    }

    @Override
    public Object part2() {
        List<Integer> stack = lines.stream()
            .map(l -> Integer.valueOf(l))
            .collect(Collectors.toList());
        
        int i = 0;
        int c = 0;
        while (i < stack.size()) {
            int current = stack.get(i);

            if (current >= 3) {
                stack.set(i, current - 1);
            } else {
                stack.set(i, current + 1);
            }
            i += current;

            c++;
        }

        return c;
    }

}
