package be.neonweb.aoc.solvers.d09;

import java.util.Arrays;
import java.util.function.Function;

public enum Direction {

    UP("U", coordinate -> new Coordinate(coordinate.getX(), coordinate.getY() + 1)),
    DOWN("D", knot -> new Coordinate(knot.getX(), knot.getY() - 1)),
    LEFT("L", knot -> new Coordinate(knot.getX() - 1, knot.getY())),
    RIGHT("R", knot -> new Coordinate(knot.getX() + 1, knot.getY()));

    private String code;
    private Function<Coordinate, Coordinate> mover;

    Direction(String code, Function<Coordinate, Coordinate> mover) {
        this.code = code;
        this.mover = mover;
    }

    public Coordinate apply(Coordinate coordinate) {
        return mover.apply(coordinate);
    }

    public static Direction of(String code) {
        return Arrays.stream(Direction.values())
                     .filter(direction -> direction.code.equals(code))
                     .findFirst()
                     .orElseThrow();
    }

}
