package be.neonweb.adventofcode.d12;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class D12Test {

    @Test
    void lines1() {
        assertEquals(2, D12.lines(List.of(1, 2, 3, 6, 7, 8, 13)));
    }
}