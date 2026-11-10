package be.neonweb.aoc2016.d01;

import be.neonweb.aoc2016.AbstractDay;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class D01 extends AbstractDay {

    @Override
    public Object part1() {
        Me me = new Me(0, 0, Direction.UP);

        List<Move> moves = lines.stream()
                                .flatMap(lines -> Arrays.stream(lines.split(", ")))
                                .map(Move::of)
                                .toList();

        List<Me> mes = new ArrayList<>();
        mes.add(me);
        for (Move move : moves) {
            Me newMe = me.move(move)
                         .getLast();
            mes.add(newMe);
            log.info("{} - {}", move, newMe);
            me = newMe;
        }

        return distance(me);
    }

    private static int distance(Me me) {
        return Math.abs(me.x) + Math.abs(me.y);
    }

    @Override
    public Object part2() {
        Me me = new Me(0, 0, Direction.UP);

        List<Move> moves = lines.stream()
                                .flatMap(lines -> Arrays.stream(lines.split(", ")))
                                .map(Move::of)
                                .toList();

        List<Me> mes = new ArrayList<>();
        mes.add(me);
        for (Move move : moves) {
            mes.addAll(me.move(move));
            me = mes.getLast();
        }

        return mes.stream()
                  .filter(me1 -> mes.stream()
                                    .filter(me2 -> me2 != me1)
                                    .anyMatch(me2 -> samePosition(me1, me2)))
                  .findFirst()
                  .map(D01::distance);
    }

    private static boolean samePosition(Me me1, Me me2) {
        return me1.x == me2.x && me1.y == me2.y;
    }

    @Getter
    @Setter
    @ToString
    public static class Me {
        private int x;
        private int y;
        private Direction direction;

        public Me(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public List<Me> move(Move move) {
            Direction direction = move.turn.turn(this.direction);

            List<Me> mes = new ArrayList<>();
            int x = this.x;
            int y = this.y;
            for (int i = 0; i < move.distance; i++) {
                x += direction.deltaX;
                y += direction.deltaY;
                mes.add(new Me(x, y, direction));
            }

            return mes;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Move {
        private Turn turn;
        private int distance;

        public static Move of(String value) {
            Move move = new Move();
            move.setTurn(Turn.of(value.substring(0, 1)));
            move.setDistance(Integer.parseInt(value.substring(1)));
            return move;
        }
    }

    public enum Turn {
        LEFT,
        RIGHT;

        public static Turn of(String turn) {
            return switch (turn) {
                case "L" -> LEFT;
                case "R" -> RIGHT;
                default -> throw new IllegalArgumentException();
            };
        }

        public Direction turn(Direction direction) {
            return switch (this) {
                case LEFT -> direction.left();
                case RIGHT -> direction.right();
            };
        }
    }

    public enum Direction {
        UP(0, 1),
        DOWN(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private int deltaX;
        private int deltaY;

        Direction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public Direction left() {
            return switch (this) {
                case UP -> LEFT;
                case DOWN -> RIGHT;
                case LEFT -> DOWN;
                case RIGHT -> UP;
            };
        }

        public Direction right() {
            return switch (this) {
                case UP -> RIGHT;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case RIGHT -> DOWN;
            };
        }
    }
}
