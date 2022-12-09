package be.neonweb.aoc.solvers.d09;

import lombok.Data;

@Data
public class Move {

    private Direction direction;

    private int distance;

    public static Move of(String s) {
        Move move = new Move();
        String[] split = s.split(" ");
        move.direction = Direction.of(split[0]);
        move.distance = Integer.parseInt(split[1]);
        return move;
    }
}
