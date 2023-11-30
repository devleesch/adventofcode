package be.neonweb.aoc.solvers.d22;

import lombok.Data;

@Data
public class Tile {

    private int x;

    private int y;

    private boolean wall;

    private int face;

    public Tile() {
    }

    public Tile(int x, int y, boolean wall) {
        this.x = x;
        this.y = y;
        this.wall = wall;
        this.face = getFace(x, y);
    }

    private int getFace(int x, int y) {
        if (y < D22b.SIZE) {
            return 1;
        } else if (y < D22b.SIZE * 2) {
            return 2 + x / D22b.SIZE;
        } else {
            return 3 + x / D22b.SIZE;
        }
    }
}
