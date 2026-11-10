package be.neonweb.aoc.model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class Mover {
    
    private Point point;
    private Direction direction;

    public Mover(Point point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }

    public void forward() {
        this.point = new Point(this.point.getX() + direction.dx, this.point.getY() + direction.getDy());
    }

    public void turnLeft() {
        this.direction = direction.turnLeft();
    }

    public void turnRight() {
        this.direction = direction.turnRight();
    }

    @Getter 
    public enum Direction {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, 1),
        DOWN(0, -1);

        private int dx;
        private int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public Direction turnLeft() {
            return switch (this) {
                case LEFT -> DOWN;
                case RIGHT -> UP;
                case UP -> LEFT;
                case DOWN -> RIGHT;
            };
        }

        public Direction turnRight() {
            return switch (this) {
                case LEFT -> UP;
                case RIGHT -> DOWN;
                case UP -> RIGHT;
                case DOWN -> LEFT;
            };
        }
    }

}
