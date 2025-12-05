package be.neonweb.aoc2025.d05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class D05Test {

    @Test
    void test1() {
        D05.Interval interval = new D05.Interval(1, 4);
        D05.Interval interval2 = new D05.Interval(2, 3);

        D05.Interval actual = D05.Interval.merge(List.of(interval, interval2));
        D05.Interval expected = new D05.Interval(1, 4);

        Assertions.assertEquals(expected, actual);
    }

}
