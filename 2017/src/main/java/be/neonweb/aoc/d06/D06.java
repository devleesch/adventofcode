package be.neonweb.aoc.d06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.neonweb.aoc.AbstractDay;

public class D06 extends AbstractDay {

    private Set<List<Integer>> memory = new HashSet<>();
    private List<Integer> target;

    public static void main(String[] args) throws Exception {
        D06 d06 = new D06();
        d06.run();
    }

    @Override
    public Object part1() {
        List<Integer> current = Arrays.stream(lines.get(0).split("\\t"))
            .map(Integer::valueOf)
            .toList();

        while (!memory.contains(current)) {
            memory.add(current);
            current = redist(current);
        }
        
        target = current;

        return memory.size();
    }

    @Override
    public Object part2() {
        memory.clear();

        List<Integer> current = new ArrayList<>(target);
        while(!memory.contains(target)) {
            current = redist(current);
            memory.add(current);
        }

        return memory.size();
    }

    private List<Integer> redist(List<Integer> current) {
        int max = current.stream()
                .max(Integer::compare)
                .orElseThrow();

        int i = current.indexOf(max);

        ArrayList<Integer> redist = new ArrayList<>(current);
        redist.set(i, 0);
        while (max > 0) {
            i++;
            int ci = i % redist.size();
            redist.set(ci, redist.get(ci) + 1);
            max--;
        }
        return redist;
    }

}
