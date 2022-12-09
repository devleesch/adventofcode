package be.neonweb.aoc.solvers.d09;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Knot {

    private Coordinate coordinate;

    @ToString.Exclude
    private Knot previous;

    @ToString.Exclude
    private Knot next;

    public Knot(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void move(Direction direction) {
        this.coordinate = direction.apply(this.coordinate);
    }

    public List<Direction> getDirection(Knot other) {
        List<Direction> directions = new ArrayList<>();
        if (!this.coordinate.adjacent(other.coordinate)) {
            if (this.coordinate.getX() < other.coordinate.getX()) {
                directions.add(Direction.RIGHT);
            } else if(this.coordinate.getX() > other.coordinate.getX()) {
                directions.add(Direction.LEFT);
            }

            if (this.coordinate.getY() < other.coordinate.getY()) {
                directions.add(Direction.UP);
            } else if (this.coordinate.getY() > other.coordinate.getY()) {
                directions.add(Direction.DOWN);
            }
        }
        return directions;
    }
}
