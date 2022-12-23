package be.neonweb.aoc.solvers.d18;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class D18a extends Solver {

    Set<Cube> cubes;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        cubes = Files.readAllLines(Path.of("src/main/resources/d18/input.txt"))
                     .stream()
                     .map(Cube::of)
                     .collect(Collectors.toSet());
    }

    @Override
    public Object solve() throws Exception {
        int result = cubes.size() * 6;

        Integer commons = cubes.stream()
                               .flatMap(cube -> cubes.stream()
                                                     .map(cube::touch))
                               .reduce(Integer::sum)
                               .get();

        return result - commons;
    }
}
