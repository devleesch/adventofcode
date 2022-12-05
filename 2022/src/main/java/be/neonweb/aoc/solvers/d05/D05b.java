package be.neonweb.aoc.solvers.d05;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Slf4j
public class D05b extends D05a {

    @Override
    public Object solve() throws Exception {
        moves.forEach(move -> move.applyTo2(stacks));
        return stacks.stream()
                .map(Stack::peek)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
