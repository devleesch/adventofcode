package be.neonweb.adventofcode.aoc2023.d10;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Position {

    private int x;
    private int y;

    public Position left() {
        return Position.builder()
                       .x(this.x - 1)
                       .y(this.y)
                       .build();
    }

    public Position right() {
        return Position.builder()
                       .x(this.x + 1)
                       .y(this.y)
                       .build();
    }

    public Position top() {
        return Position.builder()
                       .x(this.x)
                       .y(this.y - 1)
                       .build();
    }

    public Position bottom() {
        return Position.builder()
                       .x(this.x)
                       .y(this.y + 1)
                       .build();
    }

    public List<Position> around() {
        List<Position> positions = new ArrayList<>();
        positions.add(left());
        positions.add(right());
        positions.add(top());
        positions.add(bottom());
        return positions;
    }

    public boolean valid(int minX, int maxX, int minY, int maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
