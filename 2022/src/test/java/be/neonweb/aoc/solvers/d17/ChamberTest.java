package be.neonweb.aoc.solvers.d17;

import be.neonweb.aoc.utils.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class ChamberTest {

    @Test
    void positionsEquals() {
        HashSet<Position> left = new HashSet<>();
        left.addAll(Rock.ROCKS.get(1).copy().getPieces());
        HashSet<Position> right = new HashSet<>();
        right.addAll(Rock.ROCKS.get(1).copy().getPieces());

        assertEquals(left, right);
    }

}
