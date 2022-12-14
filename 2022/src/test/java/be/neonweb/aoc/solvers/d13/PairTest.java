package be.neonweb.aoc.solvers.d13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void check1() {
        Pair pair = getPair(
                "[9]",
                "[[8,7,6]]"
        );
        assertNotEquals(0, pair.check());
    }

    @Test
    void check2() {
        Pair pair = getPair(
                "[]",
                "[0]"
        );
        assertEquals(0, pair.check());
    }

    @Test
    void check3() {
        Pair pair = getPair(
                "[[[[3,9],[],[5,9,8,3]],2,5,9,8]]",
                "[[[9,0],4,[9,10],[5,6,10,0,[6]]]]"
        );
        assertNotEquals(0, pair.check());
    }

    @Test
    void check4() {
        Pair pair = getPair(
                "[1,2,3]",
                "[1,2,3,4]"
        );
        assertEquals(0, pair.check());
    }

    @Test
    void check5() {
        Pair pair = getPair(
                "[0]",
                "[7]"
        );
        assertEquals(0, pair.check());
    }

    @Test
    void check6() {
        Pair pair = getPair(
                "[[6],[0,[4,[9,9,0,0,7],[9,10],[7]],6,1],[[6],9,[0,8,[3,5]]],[4],[[[0,9,2,4,1],3,5],3,[3],[7],[[6,0,1],[2],[7,1,7,5,5]]]]",
                "[[9,7],[7,5,[[],[2,0,9,0,0],9,[8,1,3,10]],2]]"
        );
        assertEquals(0, pair.check());
    }

    @Test
    void check7() {
        Pair pair = getPair(
                "[[2]]",
                "[[6]]"
        );
        assertEquals(-1, pair.check());
    }

    private Pair getPair(String p1, String p2) {
        Pair pair = new Pair();
        pair.setLeft(Packet.of(p1));
        pair.setRight(Packet.of(p2));
        return pair;
    }
}
