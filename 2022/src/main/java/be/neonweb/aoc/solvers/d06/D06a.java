package be.neonweb.aoc.solvers.d06;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class D06a extends Solver {

    List<String> chars = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        chars = Files.readAllLines(Path.of("src/main/resources/d06/input.txt"))
                     .stream()
                     .flatMap(s -> Arrays.stream(s.split("")))
                     .collect(Collectors.toList());
    }

    @Override
    public Object solve() throws Exception {
        int skip = 0;
        while(skip < chars.size() - 4) {
            Set<String> collect = chars.stream()
                                       .skip(skip)
                                       .limit(4)
                                       .collect(Collectors.toSet());
            if (collect.size() == 4) {
                return skip + 4;
            }
            skip++;
        }
        return null;
    }
}
