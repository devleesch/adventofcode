package be.neonweb.aoc.solvers.d02;

import be.neonweb.aoc.solvers.Solver;
import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
public class D02a extends Solver {

    protected List<Game> games = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        Files.readAllLines(Path.of("src/main/resources/d02/input.txt"))
             .forEach(line -> games.add(Game.of(line)));
    }

    @Override
    public Integer solve() throws Exception {
        return games.stream()
                    .map(Game::points)
                    .reduce(0, Integer::sum);
    }
}
