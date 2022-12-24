package be.neonweb.aoc.solvers.d23;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Position;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class D23a extends Solver {

    Map<Integer, Elf> elves = new HashMap<>();

    Queue<List<Direction>> queue = new LinkedList<>();

    @Override
    public void config() throws Exception {
    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d23/test1.txt"));
        int index = 0;
        for (int y = 0; y < lines.size(); y++) {
            String[] split = lines.get(y)
                                  .split("");
            for (int x = 0; x < split.length; x++) {
                if (split[x].equals("#")) {
                    elves.put(index, new Elf(x, y, index));
                    index++;
                }
            }
        }

        queue.add(List.of(Direction.NW, Direction.N, Direction.NE));
        queue.add(List.of(Direction.SW, Direction.S, Direction.SE));
        queue.add(List.of(Direction.SW, Direction.W, Direction.NW));
        queue.add(List.of(Direction.SE, Direction.E, Direction.NE));
    }

    @Override
    public Object solve() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("Round " + i);
            List<Elf> moves = new ArrayList<>(elves.values()
                                                   .stream()
                                                   .filter(elf -> elf.hasNeighboors(elves.values()))
                                                   .map(elf -> elf.propose(queue, elves.values()))
                                                   .toList());

            if (moves.isEmpty()) {
                break;
            }

            List<Elf> toRemove = moves.stream()
                                      .flatMap(move -> moves.stream()
                                                            .filter(other -> move.getIndex() != other.getIndex())
                                                            .filter(other -> move.getX() == other.getX() && move.getY() == other.getY()))
                                      .toList();
            moves.removeAll(toRemove);

            moves.forEach(move -> elves.put(move.getIndex(), move));

            queue.add(queue.poll());
        }
        return countEmpty(elves.values());
    }

    public Integer countEmpty(Collection<Elf> elves) {
        Position min = new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Position max = new Position(Integer.MIN_VALUE, Integer.MIN_VALUE);

        elves.forEach(elf -> {
            min.setX(Math.min(min.getX(), elf.getX()));
            min.setY(Math.min(min.getY(), elf.getY()));
            max.setX(Math.max(max.getX(), elf.getX()));
            max.setY(Math.max(max.getY(), elf.getY()));
        });

        return (max.getX() - min.getX() + 1) * (max.getY() - min.getY() + 1) - elves.size();
    }
}
