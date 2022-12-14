package be.neonweb.aoc.solvers.d13;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class D13a extends Solver {

    List<Pair> pairs = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d13/input.txt"));

        Pair current = new Pair();
        boolean left = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                pairs.add(current);
                current = new Pair();
            } else if (left) {
                current.setLeft(Packet.of(line));
                left = false;
            } else {
                current.setRight(Packet.of(line));
                left = true;
            }
        }
        pairs.add(current);
    }

    @Override
    public Object solve() throws Exception {
        return pairs.stream()
                    .filter(pair -> pair.check() == -1)
                    .map(pair -> pairs.indexOf(pair) + 1)
                    .reduce(Integer::sum)
                    .get();
    }
}
