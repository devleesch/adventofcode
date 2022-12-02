package be.neonweb.aoc.solvers.d02;

import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Path;

@Data
public class D02b extends D02a {

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        Files.readAllLines(Path.of("src/main/resources/d02/input.txt"))
             .forEach(line -> games.add(Game.of2(line)));
    }
}
