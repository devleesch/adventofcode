package be.neonweb.aoc.solvers.d17;

import be.neonweb.aoc.utils.Position;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class D17b extends D17a {

    @Override
    public void parse() throws Exception {
        jets = Files.readAllLines(Path.of("src/main/resources/d17/test1.txt"))
                    .stream()
                    .map(line -> line.split(""))
                    .flatMap(Arrays::stream)
                    .map(Direction::of)
                    .collect(Collectors.toList());

    }

    @Override
    public Object solve() throws Exception {
        Chamber chamber = new Chamber(9);

        for (long i = 0; i < 1000000000000L; i++) {
            chamber.turn(jets);
        }

        return chamber.getHeight() - 1;
    }
}
