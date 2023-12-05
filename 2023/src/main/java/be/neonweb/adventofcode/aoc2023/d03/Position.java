package be.neonweb.adventofcode.aoc2023.d03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    private int x;
    private int y;

    public Set<Position> betweenX(Position to) {
        Set<Position> positions = new LinkedHashSet<>();
        for (int x = this.getX(); x <= to.getX(); x++) {
            positions.add(new Position(x, this.getY()));
        }
        return positions;
    }

    public Set<Position> around(int minX, int maxX, int minY, int maxY) {
        Set<Position> around = new LinkedHashSet<>();
        for (int dX = -1; dX <= 1; dX++) {
            for (int dY = -1; dY <= 1; dY++) {
                int x = this.getX() + dX;
                int y = this.getY() + dY;
                if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                    around.add(new Position(x, y));
                }
            }
        }
        return around;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
