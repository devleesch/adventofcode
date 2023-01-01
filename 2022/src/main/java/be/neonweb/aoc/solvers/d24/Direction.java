package be.neonweb.aoc.solvers.d24;

import be.neonweb.aoc.utils.Position;

import java.util.Arrays;
import java.util.function.Function;

public enum Direction {

    UP("^", position -> new Position(position.getX(), position.getY() - 1)),
    DOWN("v", position -> new Position(position.getX(), position.getY() + 1)),
    LEFT("<", position -> new Position(position.getX() - 1, position.getY())),
    RIGHT(">", position -> new Position(position.getX() + 1, position.getY()));

    private final Function<Position, Position> move;
    private final String repr;

    Direction(String repr, Function<Position, Position> move) {
        this.repr = repr;
        this.move = move;
    }

    public Position move(Position position) {
        return this.move.apply(position);
    }

    public static Direction of(String s) {
        return Arrays.stream(values())
                     .filter(direction -> direction.repr.equals(s))
                     .findFirst()
                     .get();
    }

}
