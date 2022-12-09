package be.neonweb.aoc.solvers.d09;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class D09 extends Solver {

    List<Move> moves;

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        moves = Files.readAllLines(Path.of("src/main/resources/d09/input.txt"))
                     .stream()
                     .map(Move::of)
                     .toList();
    }

    @Override
    public Object solve() throws Exception {

        Set<Coordinate> visited = new HashSet<>();

        Knot previous = null;
        List<Knot> knots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Knot knot = new Knot(new Coordinate(0, 0));
            knot.setPrevious(previous);
            if (Objects.nonNull(previous)) {
                previous.setNext(knot);
            }
            knots.add(knot);
            previous = knot;
        }

        moves.forEach(move -> {
            IntStream.range(0, move.getDistance())
                     .forEach(i -> {
                         Knot current = knots.get(0);
                         current.move(move.getDirection());
                         while (Objects.nonNull(current.getNext())) {
                             current = current.getNext();
                             for (Direction direction : current.getDirection(current.getPrevious())) {
                                current.move(direction);
                             }
                         }
                         visited.add(knots.get(knots.size() - 1).getCoordinate());
                     });
        });

        return visited.size();
    }
}
