package be.neonweb.adventofcode.d16;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

@Slf4j
public class D16 extends Day {
    @Override
    public String part1(List<String> lines) {
        List<Item> items = IntStream.range(0, lines.size())
                                    .mapToObj(y -> IntStream.range(0, lines.get(y)
                                                                           .length())
                                                            .filter(x -> lines.get(y)
                                                                              .charAt(x) != '.')
                                                            .mapToObj(x -> Item.from(x, y, lines.get(y)
                                                                                                .charAt(x))))
                                    .flatMap(itemStream -> itemStream)
                                    .toList();

        End end = items.stream()
                       .filter(item -> item instanceof End)
                       .findFirst()
                       .map(End.class::cast)
                       .orElseThrow();

        Reindeer reindeer = items.stream()
                                 .filter(item -> item instanceof Reindeer)
                                 .findFirst()
                                 .map(Reindeer.class::cast)
                                 .orElseThrow();

        List<Wall> walls = items.stream()
                                .filter(item -> item instanceof Wall)
                                .map(Wall.class::cast)
                                .toList();

        List<Path> paths = new ArrayList<>();
        Path path = new Path();
        path.setReindeer(reindeer);

        explore(path, end, walls, paths);

        return paths.stream()
                    .map(Path::score)
                    .min(Integer::compareTo)
                    .map(Object::toString)
                    .orElseThrow();
    }

    @Override
    public String part2(List<String> lines) {
        return "";
    }

    public static void explore(Path path, End end, List<Wall> walls, List<Path> paths) {
        if (!path.isTerminated(end)) {
            path.visited.add(path.getReindeer()
                                 .getPosition());

            Path copy = path.copy();
            copy.getMoves()
                .add(Move.FORWARD);
            copy.getReindeer()
                .move(walls);
            explore(copy, end, walls, paths);

            copy = path.copy();
            copy.getMoves()
                .add(Move.ROTATE);
            copy.getReindeer()
                .setDirection(path.getReindeer()
                                  .getDirection()
                                  .counterclockwise());
            explore(copy, end, walls, paths);

            copy = path.copy();
            copy.getMoves()
                .add(Move.ROTATE);
            copy.getReindeer()
                .setDirection(path.getReindeer()
                                  .getDirection()
                                  .clockwise());
            explore(copy, end, walls, paths);
        }

        if (path.getReindeer()
                .getPosition()
                .equals(end.getPosition())) {
            paths.add(path);
            log.info("paths.size() = {}", paths.size());
        }
    }

    @Getter
    @Setter
    @ToString
    public static abstract class Item {
        private Position position;

        public Item(int x, int y) {
            this.position = new Position(x, y);
        }

        public static Item from(int x, int y, char c) {
            return switch (c) {
                case '#' -> new Wall(x, y);
                case 'S' -> new Reindeer(x, y);
                case 'E' -> new End(x, y);
                default -> throw new RuntimeException();
            };
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Wall extends Item {
        public Wall(int x, int y) {
            super(x, y);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Reindeer extends Item {

        private Direction direction;

        public Reindeer(int x, int y) {
            super(x, y);
            this.direction = Direction.EAST;
        }

        public boolean move(List<Wall> walls) {
            Position nextPosition = this.direction.move.apply(this.getPosition());
            boolean isFree = walls.stream()
                                  .noneMatch(wall -> wall.getPosition()
                                                         .equals(nextPosition));
            if (isFree) {
                this.setPosition(nextPosition);
            }

            return isFree;
        }

        public Reindeer copy() {
            Reindeer reindeer = new Reindeer(this.getPosition().x, this.getPosition().y);
            reindeer.setDirection(this.direction);
            return reindeer;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class End extends Item {
        public End(int x, int y) {
            super(x, y);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
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

    public enum Direction {
        EAST(position -> new Position(position.x + 1, position.y)),
        WEST(position -> new Position(position.x - 1, position.y)),
        NORTH(position -> new Position(position.x, position.y - 1)),
        SOUTH(position -> new Position(position.x, position.y + 1));

        private Function<Position, Position> move;

        Direction(Function<Position, Position> move) {
            this.move = move;
        }

        private Direction clockwise() {
            return switch (this) {
                case EAST -> SOUTH;
                case WEST -> NORTH;
                case NORTH -> EAST;
                case SOUTH -> WEST;
            };
        }

        private Direction counterclockwise() {
            return switch (this) {
                case EAST -> NORTH;
                case WEST -> SOUTH;
                case NORTH -> WEST;
                case SOUTH -> EAST;
            };
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Path {
        private Reindeer reindeer;
        private List<Position> visited = new ArrayList<>();
        private List<Move> moves = new ArrayList<>();

        public Path copy() {
            Path path = new Path();
            path.reindeer = reindeer.copy();
            path.visited = new ArrayList<>(visited);
            path.moves = new ArrayList<>(moves);
            return path;
        }

        public boolean isTerminated(End end) {
            return reindeer.getPosition()
                           .equals(end.getPosition())
                    || (!moves.isEmpty() && moves.getLast()
                                                 .equals(Move.FORWARD) && visited.contains(reindeer.getPosition())
                    || (moves.size() >= 2 && moves.subList(moves.size() - 2, moves.size())
                                                  .stream()
                                                  .allMatch(m -> m.equals(Move.ROTATE))));
        }

        public int score() {
            return moves.stream()
                        .map(Move::getPoint)
                        .reduce(0, Integer::sum);
        }
    }

    @Getter
    public enum Move {
        FORWARD(1),
        ROTATE(1000);

        private int point;

        Move(int point) {
            this.point = point;
        }
    }
}
