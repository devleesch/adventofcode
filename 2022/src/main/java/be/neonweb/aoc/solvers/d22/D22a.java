package be.neonweb.aoc.solvers.d22;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class D22a extends Solver {

    Me me;

    Set<Tile> tiles = new HashSet<>();

    List<Move> moves = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d22/test1.txt"));

        for (int y = 0; y < lines.size() - 2; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.split("").length; x++) {
                if (line.charAt(x) != ' ') {
                    if (Objects.isNull(me)) {
                        me = new Me(x, y, Facing.RIGHT);
                    }
                    tiles.add(new Tile(x, y, line.charAt(x) == '#'));
                }
            }
        }

        String rawMoves = lines.get(lines.size() - 1);
        String current = "";
        for (String s : rawMoves.split("")) {
            if (s.equals("L")) {
                if (!current.isEmpty()) {
                    moves.add(new Move(Integer.valueOf(current), null));
                    current = "";
                }
                moves.add(new Move(null, "L"));
            } else if (s.equals("R")) {
                if (!current.isEmpty()) {
                    moves.add(new Move(Integer.valueOf(current), null));
                    current = "";
                }
                moves.add(new Move(null, "R"));
            } else {
                current += s;
            }
        }
    }

    @Override
    public Object solve() throws Exception {
        for (Move move : moves) {
            if (Objects.isNull(move.getRotate())) {
                for (int i = 0; i < move.getForward(); i++) {
                    me = me.move(tiles);
                }
            } else if ("L".equals(move.getRotate())) {
                me.left();
            } else if ("R".equals(move.getRotate())) {
                me.right();
            }
        }
        return 1000 * (me.getY() + 1) + 4 * (me.getX() + 1) + me.getFacing().getValue();
    }
}
