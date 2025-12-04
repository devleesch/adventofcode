package be.neonweb.aoc2025.d04;

import be.neonweb.aoc2025.Solver;
import be.neonweb.aoc2025.utils.Position;
import be.neonweb.aoc2025.utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class D04 extends Solver {

    private List<Position<Boolean>> grid;

    @Override
    public void parseFile() throws Exception {
        readFile(null);

        grid = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid.add(new Position<>(x, y, line.charAt(x) == '@'));
            }
        }
    }

    @Override
    public Object part1() {
        return grid.stream()
                   .filter(Position::getValue)
                   .map(p -> new Tuple<>(p, getAdjacents(p)))
                   .filter(t -> t.getRight()
                                 .stream()
                                 .filter(Position::getValue)
                                 .count() < 4)
                   .count();
    }

    @Override
    public Object part2() {
        List<Tuple<Position<Boolean>, List<Position<Boolean>>>> toRemoves = new ArrayList<>();
        int count = 0;
        do {
            toRemoves.forEach(t -> grid.stream()
                                       .filter(p -> p.getX() == t.getLeft()
                                                                 .getX() && p.getY() == t.getLeft()
                                                                                         .getY())
                                       .findFirst()
                                       .ifPresent(p -> p.setValue(false)));

            toRemoves = grid.stream()
                            .filter(Position::getValue)
                            .map(p -> new Tuple<>(p, getAdjacents(p)))
                            .filter(t -> t.getRight()
                                          .stream()
                                          .filter(Position::getValue)
                                          .count() < 4)
                            .toList();

            count += toRemoves.size();

        } while (!toRemoves.isEmpty());

        return count;
    }

    public List<Position<Boolean>> getAdjacents(Position<Boolean> position) {
        List<Position<Boolean>> adjacents = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                int pX = position.getX() + x;
                int pY = position.getY() + y;
                grid.stream()
                    .filter(p -> p.getX() == pX && p.getY() == pY)
                    .findFirst()
                    .ifPresent(adjacents::add);
            }
        }

        return adjacents;
    }

}
