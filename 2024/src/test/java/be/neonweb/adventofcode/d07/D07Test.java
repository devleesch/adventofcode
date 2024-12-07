package be.neonweb.adventofcode.d07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class D07Test {

    @Test
    public void check1() {
        D07.Check check = new D07.Check();
        check.setExpected(190L);
        check.setValues(List.of(10L, 19L));

        assertTrue(check.isValid(List.of(D07.Operator.MUL)));
    }

    @Test
    public void check2() {
        D07.Check check = new D07.Check();
        check.setExpected(3267L);
        check.setValues(List.of(81L, 40L, 27L));

        assertTrue(check.isValid(List.of(D07.Operator.MUL, D07.Operator.ADD)));
    }

}