package be.neonweb.aoc.solvers.d02;

import be.neonweb.aoc.solvers.Solver;
import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
public class D02 extends Solver {

    private List<Game> games = new ArrayList<>();
    private List<Game> games2 = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        Files.readAllLines(Path.of("src/main/resources/d02/input.txt"))
             .forEach(line -> games.add(Game.of(line)));

        Files.readAllLines(Path.of("src/main/resources/d02/input.txt"))
             .forEach(line -> games2.add(Game.of2(line)));
    }

    @Override
    public Object part1() throws Exception {
        return games.stream()
                    .map(Game::points)
                    .reduce(0, Integer::sum);
    }

    @Override
    public Object part2() throws Exception {
        return games2.stream()
                     .map(Game::points)
                     .reduce(0, Integer::sum);
    }
}
