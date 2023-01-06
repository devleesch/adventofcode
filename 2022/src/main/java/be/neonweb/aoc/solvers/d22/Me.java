package be.neonweb.aoc.solvers.d22;

import lombok.Data;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Data
public class Me {

    private int x;
    private int y;

    private Facing facing;

    public Me(int x, int y, Facing facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public Me move(Set<Tile> tiles) {
        Me me = facing.move(this);
        Optional<Tile> optionalDestination = tiles.stream()
                                                  .filter(tile -> tile.getX() == me.getX() && tile.getY() == me.getY())
                                                  .findFirst();
        if (optionalDestination.isPresent()) {
            Tile destination = optionalDestination.get();
            if (destination.isWall()) {
                return this;
            } else {
                return me;
            }
        } else {
            Tile destination = switch (facing) {
                case TOP -> tiles.stream()
                                 .filter(tile -> tile.getX() == me.getX())
                                 .max(Comparator.comparing(Tile::getY))
                                 .get();
                case BOTTOM -> tiles.stream()
                                    .filter(tile -> tile.getX() == me.getX())
                                    .min(Comparator.comparing(Tile::getY))
                                    .get();
                case LEFT -> tiles.stream()
                                  .filter(tile -> tile.getY() == me.getY())
                                  .max(Comparator.comparing(Tile::getX))
                                  .get();
                case RIGHT -> tiles.stream()
                                   .filter(tile -> tile.getY() == me.getY())
                                   .min(Comparator.comparing(Tile::getX))
                                   .get();
            };
            if (destination.isWall()) {
                return this;
            } else {
                return new Me(destination.getX(), destination.getY(), me.facing);
            }
        }
    }

    public Me move2(Set<Tile> tiles) {
        Me me = facing.move(this);
        Optional<Tile> optionalDestination = tiles.stream()
                                                  .filter(tile -> tile.getX() == me.getX() && tile.getY() == me.getY())
                                                  .findFirst();
        if (optionalDestination.isPresent()) {
            Tile destination = optionalDestination.get();
            if (destination.isWall()) {
                return this;
            } else {
                return me;
            }
        } else {
            Tile destination = switch (facing) {
                case TOP -> tiles.stream()
                                 .filter(tile -> tile.getX() == me.getX())
                                 .max(Comparator.comparing(Tile::getY))
                                 .get();
                case BOTTOM -> tiles.stream()
                                    .filter(tile -> tile.getX() == me.getX())
                                    .min(Comparator.comparing(Tile::getY))
                                    .get();
                case LEFT -> tiles.stream()
                                  .filter(tile -> tile.getY() == me.getY())
                                  .max(Comparator.comparing(Tile::getX))
                                  .get();
                case RIGHT -> tiles.stream()
                                   .filter(tile -> tile.getY() == me.getY())
                                   .min(Comparator.comparing(Tile::getX))
                                   .get();
            };
            if (destination.isWall()) {
                return this;
            } else {
                return new Me(destination.getX(), destination.getY(), me.facing);
            }
        }
    }

    public void left() {
        facing = facing.turnLeft();
    }

    public void right() {
        facing = facing.turnRight();
    }

}
