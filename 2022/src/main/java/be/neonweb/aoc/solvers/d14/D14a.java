package be.neonweb.aoc.solvers.d14;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Position;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class D14a extends Solver {

    Set<Item> map;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        map = Files.readAllLines(Path.of("src/main/resources/d14/input.txt"))
                   .stream()
                   .map(Rock::of)
                   .flatMap(Collection::stream)
                   .collect(Collectors.toSet());

        map.add(new Source(new Position(500, 0)));
    }

    @Override
    public Object solve() throws Exception {
        boolean run = true;
        int maxY = map.stream()
                      .map(item -> item.getPosition()
                                       .getY())
                      .max(Integer::compareTo)
                      .get();

        int count = 0;
        while (run) {
            count++;
            //display();
            Sand sand = new Sand(new Position(500, 0));
            run = sand.fall(map, maxY);
            map.add(sand);
        }

        display();

        return count - 1; // minus the one that falls
    }

    public void display() {
        int minX = map.stream()
                      .map(item -> item.getPosition()
                                       .getX())
                      .min(Integer::compareTo)
                      .get();
        int maxX = map.stream()
                      .map(item -> item.getPosition()
                                       .getX())
                      .max(Integer::compareTo)
                      .get();
        int minY = map.stream()
                      .map(item -> item.getPosition()
                                       .getY())
                      .min(Integer::compareTo)
                      .get();
        int maxY = map.stream()
                      .map(item -> item.getPosition()
                                       .getY())
                      .max(Integer::compareTo)
                      .get();

        for (int y = minY - 1; y <= maxY; y++) {
            for (int x = minX - 1; x <= maxX + 1; x++) {
                int finalX = x;
                int finalY = y;
                String display = map.stream()
                                    .filter(item -> item.getPosition()
                                                        .equals(new Position(finalX, finalY)))
                                    .findFirst()
                                    .map(Item::getDisplay)
                                    .orElse(".");
                System.out.print(display);
            }
            System.out.println();
        }

        for (int x = minX - 1; x <= maxX + 1; x++) {
            System.out.print("#");
        }
        System.out.println();
    }
}
