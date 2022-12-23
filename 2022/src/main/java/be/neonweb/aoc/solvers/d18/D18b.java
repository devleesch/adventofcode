package be.neonweb.aoc.solvers.d18;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class D18b extends D18a {

    @Override
    public void parse() throws Exception {
        cubes = Files.readAllLines(Path.of("src/main/resources/d18/test1.txt"))
                     .stream()
                     .map(Cube::of)
                     .collect(Collectors.toSet());
    }

    @Override
    public Object solve() throws Exception {
        Set<Cube> air = new HashSet<>();
        for (Cube cube : cubes) {
            for (Cube next : cube.next()) {
                if (!cubes.contains(next)) {
                    air.add(next);
                }
            }
        }

        return cubes.size() * 6L - getCommons(cubes);
    }

    private Integer getCommons(Set<Cube> cubes) {
        return cubes.stream()
                    .flatMap(cube -> cubes.stream()
                                          .map(cube::touch))
                    .reduce(Integer::sum)
                    .get();
    }
}
