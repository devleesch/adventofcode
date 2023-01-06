package be.neonweb.aoc.solvers.d22;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class D22b extends D22a {

    @Override
    public Object solve() throws Exception {
        for (Move move : moves) {
            if (Objects.isNull(move.getRotate())) {
                for (int i = 0; i < move.getForward(); i++) {
                    me = me.move2(tiles);
                }
            } else if ("L".equals(move.getRotate())) {
                me.left();
            } else if ("R".equals(move.getRotate())) {
                me.right();
            }
        }

        return 1000 * (me.getY() + 1) + 4 * (me.getX() + 1) + me.getFacing()
                                                                .getValue();
    }
}
