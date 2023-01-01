package be.neonweb.aoc.solvers.d24;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Position;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D24a extends Solver {

    List<Item> items = new ArrayList<>();
    Position start;
    Position end;

    Position topLeft = new Position(0, 0);
    Position bottomRight;

    int width;
    int height;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d24/test1.txt.txt"));
        for (int y = 0; y < lines.size(); y++) {
            String[] split = lines.get(y)
                                  .split("");
            for (int x = 0; x < split.length; x++) {
                Item.of(new Position(x, y), split[x])
                    .ifPresent(items::add);

                if (y == 0 && split[x].equals(".")) {
                    start = new Position(x, y);
                } else if (y == lines.size() - 1 && split[x].equals(".")) {
                    end = new Position(x, y);
                }
            }
        }
        bottomRight = new Position(lines.get(0)
                                        .length() - 1, lines.size() - 1);

        width = bottomRight.getX() - topLeft.getX() + 1;
        height = bottomRight.getY() - topLeft.getY() + 1;
    }

    @Override
    public Object solve() throws Exception {
        return explore(start, 0, Integer.MAX_VALUE);
    }

    public int explore(Position start, int minute, int min) {
        if (minute >= min) {
            return Integer.MAX_VALUE;
        }

        List<Position> next = next(start);


        return min;
    }

    public List<Position> next(Position position) {
        List<Position> positions = new ArrayList<>();

        positions.add(position);

        Position up = position.up();
        if (up.getY() > 0) {
            positions.add(up);
        }

        Position down = position.down();
        if (down.getY() < height) {
            positions.add(down);
        }

        Position left = position.left();
        if (left.getX() > 0) {
            positions.add(left);
        }

        Position right = position.right();
        if (right.getX() < width) {
            positions.add(right);
        }

            return positions;
    }

    public List<Item> getItemAtPositionAtMinute(Position position, int minute) {
        return items.stream()
                    .filter(item -> item.getPosition()
                                        .getX() == position.getX() || item.getPosition()
                                                                          .getY() == position.getY())
                    .filter(item -> item.positionAt(minute, width, height)
                                        .equals(position))
                    .collect(Collectors.toList());
    }
}
