package be.neonweb.adventofcode.aoc2023.d10;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Direction {

    VERTICAL('|'),
    HORIZONTAL('-'),
    NORTH_EAST('L'),
    NORTH_WEST('J'),
    SOUTH_WEST('7'),
    SOUTH_EAST('F'),
    START('S');

    private Character c;

    Direction(Character c) {
        this.c = c;
    }

    public static Direction of(Character c) {
        return Arrays.stream(Direction.values())
                     .filter(direction -> direction.getC()
                                                   .equals(c))
                     .findFirst()
                     .orElseThrow();
    }

}
