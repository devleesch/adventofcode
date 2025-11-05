package be.neonweb.adventofcode.d17;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class D17Test {

    @Test
    void test1() {
        D17.Computer computer = new D17.Computer();
        computer.setRegisterC(9);
        computer.run(List.of(2,6));

        assertEquals(1, computer.getRegisterB());
        System.out.println(computer.getInss());
    }

    @Test
    void test2() {
        D17.Computer computer = new D17.Computer();
        computer.setRegisterA(10);
        computer.run(List.of(5,0,5,1,5,4));

        assertEquals("0,1,2", computer.getOutputsString());
        System.out.println(computer.getInss());
    }

    @Test
    void test3() {
        D17.Computer computer = new D17.Computer();
        computer.setRegisterA(2024);
        computer.run(List.of(0,1,5,4,3,0));

        assertEquals("4,2,5,6,7,7,7,7,3,1,0", computer.getOutputsString());
        assertEquals(0, computer.getRegisterA());
        System.out.println(computer.getInss());
    }

    @Test
    void test4() {
        D17.Computer computer = new D17.Computer();
        computer.setRegisterB(29);
        computer.run(List.of(1,7));

        assertEquals(26, computer.getRegisterB());
        System.out.println(computer.getInss());
    }

    @Test
    void test5() {
        D17.Computer computer = new D17.Computer();
        computer.setRegisterB(2024);
        computer.setRegisterC(43690);
        computer.run(List.of(4,0));

        assertEquals(44354, computer.getRegisterB());
        System.out.println(computer.getInss());
    }

}