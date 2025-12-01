package be.neonweb.aoc2025.utils;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Position {

    private int x;
    private int y;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
