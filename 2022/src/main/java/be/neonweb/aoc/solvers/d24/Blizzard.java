package be.neonweb.aoc.solvers.d24;

import be.neonweb.aoc.utils.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Blizzard extends Item {

    private Direction direction;

    public Blizzard(Position position, String s) {
        super(position);
        this.direction = Direction.of(s);
    }

    @Override
    public Position positionAt(int minute, int width, int height) {
        return switch (direction) {
            case UP -> new Position(this.getPosition()
                                        .getX(), Math.abs(this.getPosition()
                                                              .getY() - minute) % width);
            case DOWN -> new Position(this.getPosition()
                                          .getX(), Math.abs(this.getPosition()
                                                                .getY() + minute) % width);
            case LEFT -> new Position(Math.abs(this.getPosition()
                                                   .getX() - minute) % height, this.getPosition()
                                                                                   .getY());
            case RIGHT -> new Position(Math.abs(this.getPosition()
                                                    .getX() + minute) % height, this.getPosition()
                                                                                    .getY());
        };
    }
}
