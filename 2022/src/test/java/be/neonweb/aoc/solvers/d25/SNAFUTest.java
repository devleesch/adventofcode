package be.neonweb.aoc.solvers.d25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SNAFUTest {

    @Test
    void decimal1() {
        SNAFU snafu = SNAFU.of("1");
        assertEquals(1, snafu.toDecimal());
    }

    @Test
    void decimal2() {
        SNAFU snafu = SNAFU.of("2");
        assertEquals(2, snafu.toDecimal());
    }

    @Test
    void decimal3() {
        SNAFU snafu = SNAFU.of("1=");
        assertEquals(3, snafu.toDecimal());
    }

    @Test
    void decimal2022() {
        SNAFU snafu = SNAFU.of("1=11-2");
        assertEquals(2022, snafu.toDecimal());
    }

    @Test
    void from314159265() {
        assertEquals("1121-1110-1=0", SNAFU.fromDecimal(314159265d).getValue());
    }

    @Test
    void from12345() {
        assertEquals("1-0---0", SNAFU.fromDecimal(12345d).getValue());
    }

    @Test
    void from2022() {
        assertEquals("1=11-2", SNAFU.fromDecimal(2022d).getValue());
    }

}