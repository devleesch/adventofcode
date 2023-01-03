package be.neonweb.aoc.solvers.d25;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class D25a extends Solver {

    List<SNAFU> snafus;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        snafus = Files.readAllLines(Path.of("src/main/resources/d25/input.txt"))
                      .stream()
                      .map(SNAFU::of)
                      .collect(Collectors.toList());
    }

    @Override
    public Object solve() throws Exception {
        Optional<Double> sum = snafus.stream()
                                     .map(SNAFU::toDecimal)
                                     .reduce(Double::sum);

        return sum.map(value -> SNAFU.fromDecimal(value)
                                     .getValue())
                  .orElseThrow();
    }
}
