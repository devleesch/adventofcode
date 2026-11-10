package be.neonweb.aoc.d04;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import be.neonweb.aoc.AbstractDay;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class D04 extends AbstractDay {

    public static void main(String[] args) throws Exception {
        D04 d04 = new D04();
        d04.run();
    }

    @Override
    public Object part1() {
        return lines.stream()
            .mapToInt(l -> {
                List<String> splits = Arrays.asList(l.split(" "));
                Set<String> unique = new HashSet<>(splits);
                return splits.size() == unique.size() ? 1 : 0;
            }).sum();
    }

    @Override
    public Object part2() {
        return lines.stream()
            .mapToInt(l -> {
                List<String> splits = Arrays.asList(l.split(" "));
                splits = splits.stream()
                    .map(w -> {
                        return Arrays.stream(w.split(""))
                            .sorted()
                            .collect(Collectors.joining());
                    })
                    .toList();
                Set<String> unique = new HashSet<>(splits);
                return splits.size() == unique.size() ? 1 : 0;
            }).sum();
    }

}
