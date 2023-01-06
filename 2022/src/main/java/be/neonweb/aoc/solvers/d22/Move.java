package be.neonweb.aoc.solvers.d22;

import lombok.Data;

@Data
public class Move {

    private Integer forward;

    private String rotate;

    public Move(Integer forward, String rotate) {
        this.forward = forward;
        this.rotate = rotate;
    }
}
