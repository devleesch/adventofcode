package be.neonweb.aoc.solvers.d18;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class Cube {

    private int x;
    private int y;
    private int z;

    public static Cube of(String s) {
        Cube cube = new Cube();
        String[] split = s.split(",");
        cube.x = Integer.parseInt(split[0]);
        cube.y = Integer.parseInt(split[1]);
        cube.z = Integer.parseInt(split[2]);
        return cube;
    }

    public Cube() {
    }

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int touch(Cube other) {
        if (this.equals(other)) {
            return 0;
        }

        int touch = 0;
        if (x == other.x && y == other.y && z - other.z == 1) {
            touch++;
        }

        if (x == other.x && y == other.y && z - other.z == -1) {
            touch++;
        }

        if (x == other.x && z == other.z && y - other.y == 1) {
            touch++;
        }

        if (x == other.x && z == other.z && y - other.y == -1) {
            touch++;
        }

        if (y == other.y && z == other.z && x - other.x == 1) {
            touch++;
        }

        if (y == other.y && z == other.z && x - other.x == -1) {
            touch++;
        }

        return touch;
    }

    public Set<Cube> next() {
        HashSet<Cube> next = new HashSet<>();

        next.add(new Cube(this.x + 1, this.y, this.z));
        next.add(new Cube(this.x - 1, this.y, this.z));
        next.add(new Cube(this.x, this.y + 1, this.z));
        next.add(new Cube(this.x, this.y - 1, this.z));
        next.add(new Cube(this.x, this.y, this.z + 1));
        next.add(new Cube(this.x, this.y, this.z - 1));

        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return x == cube.x && y == cube.y && z == cube.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
