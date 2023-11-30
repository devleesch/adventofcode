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

    public Me(Tile tile, Facing facing) {
        this(tile.getX(), tile.getY(), facing);
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
            Me newMe = cubeMove(tiles);
            if (newMe.getPosition(tiles).isWall()) {
                return this;
            } else {
                return newMe;
            }
        }
    }

    public Tile getPosition(Set<Tile> tiles) {
        return tiles.stream()
                    .filter(tile -> tile.getX() == this.getX() && tile.getY() == this.getY())
                    .findFirst()
                    .get();
    }

    public void left() {
        facing = facing.turnLeft();
    }

    public void right() {
        facing = facing.turnRight();
    }

    private Me cubeMove(Set<Tile> tiles) {
        Tile current = tiles.stream()
                            .filter(tile -> tile.getX() == this.getX() && tile.getY() == this.getY())
                            .findFirst()
                            .get();

        return switch (current.getFace()) {
            case 1 -> switch (this.getFacing()) {
                case LEFT -> new Me(get(tiles, this.getY() + D22b.SIZE, D22b.SIZE), Facing.BOTTOM);
                case TOP -> new Me(get(tiles, this.getX(), (D22b.SIZE * 3) - 1), Facing.BOTTOM);
                case RIGHT ->
                        new Me(get(tiles, this.getX() + D22b.SIZE, (3 * D22b.SIZE) - 1 - this.getY()), Facing.LEFT);
                default -> throw new IllegalArgumentException();
            };
            case 2 -> switch (this.getFacing()) {
                case LEFT ->
                        new Me(get(tiles, (4 * D22b.SIZE) - 1 + this.getY() - D22b.SIZE, (3 * D22b.SIZE) - 1), Facing.TOP);
                case TOP -> new Me(get(tiles, (3 * D22b.SIZE) - 1 - this.getX(), 0), Facing.BOTTOM);
                case BOTTOM -> new Me(get(tiles, (3 * D22b.SIZE) - 1 - this.getX(), (3 * D22b.SIZE) - 1), Facing.TOP);
                default -> throw new IllegalArgumentException();
            };
            case 3 -> switch (this.getFacing()) {
                case TOP -> new Me(get(tiles, (2 * D22b.SIZE), this.getX() - D22b.SIZE), Facing.LEFT);
                case BOTTOM -> new Me(get(tiles, (2 * D22b.SIZE), D22b.SIZE + this.getX()), Facing.LEFT);
                default -> throw new IllegalArgumentException();
            };
            case 4 -> switch (this.getFacing()) {
                case RIGHT -> new Me(get(tiles, (4 * D22b.SIZE) - 1 - (this.getY() - D22b.SIZE), D22b.SIZE + this.getY() - 1), Facing.BOTTOM);
                default -> throw new IllegalArgumentException();
            };
            case 5 -> switch (this.getFacing()) {
                case LEFT -> new Me(get(tiles, this.getY() - (2 * D22b.SIZE) - 1, (2 * D22b.SIZE) - 1), Facing.TOP);
                case BOTTOM -> new Me(get(tiles, (3 * D22b.SIZE) - 1 - this.getX(), (2 * D22b.SIZE) - 1), Facing.TOP);
                default -> throw new IllegalArgumentException();
            };
            case 6 -> switch (this.getFacing()) {
                case TOP -> new Me(get(tiles, (3 * D22b.SIZE) - 1, this.getX() - D22b.SIZE - 1), Facing.RIGHT);
                case RIGHT -> new Me(get(tiles, (3 * D22b.SIZE) - 1, (3 * D22b.SIZE) - this.getY() - 1), Facing.LEFT);
                case BOTTOM -> new Me(get(tiles, 0, D22b.SIZE + (4 * D22b.SIZE) - 1 + this.getX()), Facing.RIGHT);
                default -> throw new IllegalArgumentException();
            };
            default -> throw new IllegalArgumentException();
        };
    }

    private Tile get(Set<Tile> tiles, int x, int y) {
        return tiles.stream()
                    .filter(tile -> tile.getX() == x && tile.getY() == y)
                    .findFirst()
                    .get();
    }

}
