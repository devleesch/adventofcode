package be.neonweb.aoc.solvers.d06;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class D06b extends D06a {

    @Override
    public Object solve() throws Exception {
        int skip = 0;
        while(skip < chars.size() - 14) {
            Set<String> collect = chars.stream()
                                       .skip(skip)
                                       .limit(14)
                                       .collect(Collectors.toSet());
            if (collect.size() == 14) {
                return skip + 14;
            }
            skip++;
        }
        return null;
    }
}
