package be.neonweb.aoc.solvers.d17;

import java.util.Arrays;

public enum Direction {

    LEFT("<"),
    RIGHT(">"),
    DOWN("v");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public static Direction of(String s) {
        return Arrays.stream(Direction.values())
                     .filter(direction -> direction.value.equals(s))
                     .findFirst()
                     .orElseThrow();
    }

}
