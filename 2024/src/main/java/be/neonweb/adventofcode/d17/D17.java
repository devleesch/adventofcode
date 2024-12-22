package be.neonweb.adventofcode.d17;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class D17 extends Day {
    @Override
    public String part1(List<String> lines) {

        return "";
    }

    @Override
    public String part2(List<String> lines) {
        return "";
    }

    @Getter
    @Setter
    public static class Computer {
        private Integer registerA;
        private Integer registerB;
        private Integer registerC;
        private Integer pointer;

        public Integer getComboOperand(Integer operand) {
            if (0 <= operand && operand <= 3) {
                return operand;
            } else if (operand == 4) {
                return registerA;
            } else if (operand == 5) {
                return registerB;
            } else if (operand == 6) {
                return registerC;
            } else {
                throw new IllegalStateException();
            }
        }

        public void run() {


        }
    }

    public static abstract class Ins {

        public abstract void compute(Integer operand, Computer computer);

    }

    public static class Adv extends Ins {

        @Override
        public void compute(Integer operand, Computer computer) {
            int value = computer.getRegisterA() / computer.getComboOperand(operand);
            computer.setRegisterA(value);
        }

    }

    public static class Bxl extends Ins {

        @Override
        public void compute(Integer operand, Computer computer) {

        }
    }
}
