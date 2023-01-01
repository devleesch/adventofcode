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
        jets = Files.readAllLines(Path.of("src/main/resources/d17/test1.txt.txt"))
                    .stream()
                    .map(line -> line.split(""))
                    .flatMap(Arrays::stream)
                    .map(Direction::of)
                    .collect(Collectors.toList());

    }

    @Override
    public Object solve() throws Exception {
        Chamber chamber = new Chamber(9);

        for (int i = 0; i < 4000; i++) {
            chamber.turn(jets);

            //log.info(i + " = " + chamber.getHeight());

            int height = chamber.getHeight() - 1;
            if (i > 2000 && height % 2 == 0) {
                //chamber.display();
                Map<Integer, List<Position>> collect = chamber.getRocks()
                                                              .stream()
                                                              .map(Rock::getPieces)
                                                              .flatMap(Collection::stream)
                                                              .filter(position -> position.getY() > 0)
                                                              .filter(position -> position.getX() != 0 && position.getX() != chamber.getWidth() - 1)
                                                              .collect(Collectors.groupingBy(position -> position.getY() <= chamber.getHeight() / 2 ? 0 : 1));
                if (collect.get(0).size() == collect.get(1).size()) {
                    HashSet<Position> left = new HashSet<>(collect.get(0));
                    Set<Position> right = collect.get(1)
                                                 .stream()
                                                 .map(position -> new Position(position.getX(), position.getY() - (height / 2)))
                                                 .collect(Collectors.toSet());
                    if (left.equals(right)) {
                        log.info("REPEAT: " + i);
                    }
                }
            }
        }

        return chamber.getHeight() - 1;
    }
}
