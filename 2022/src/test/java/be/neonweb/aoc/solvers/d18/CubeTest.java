package be.neonweb.aoc.solvers.d18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CubeTest {

    @Test
    void touch() {
        Cube c1 = Cube.of("1,1,1");
        Cube c2 = Cube.of("2,1,1");

        assertTrue(c1.touch(c2));
    }
}