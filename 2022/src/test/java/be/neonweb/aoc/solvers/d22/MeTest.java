package be.neonweb.aoc.solvers.d22;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MeTest {

    @Test
    void move2_1_right() throws IOException {
        Set<Tile> tiles = getTiles();

        Me me = new Me(11, 1, Facing.RIGHT);
        Me dest = me.move2(tiles);

        assertEquals(15, dest.getX());
        assertEquals(10, dest.getY());
    }

    @Test
    void move2_1_left() throws IOException {
        Set<Tile> tiles = getTiles();

        Me me = new Me(8, 1, Facing.LEFT);
        Me dest = me.move2(tiles);

        assertEquals(5, dest.getX());
        assertEquals(4, dest.getY());
    }

    private Set<Tile> getTiles() throws IOException {
        Set<Tile> tiles = new HashSet<>();

        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d22/test1.txt"));

        for (int y = 0; y < lines.size() - 2; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.split("").length; x++) {
                if (line.charAt(x) != ' ') {
                    tiles.add(new Tile(x, y, line.charAt(x) == '#'));
                }
            }
        }

        return tiles;
    }
}