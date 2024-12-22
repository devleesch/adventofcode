package be.neonweb.adventofcode.d17;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class D17 extends Day {
    @Override
    public String part1(List<String> lines) {
        Computer computer = new Computer();

        computer.setRegisterA(new BigInteger(lines.get(0).split(": ")[1]));
        computer.setRegisterB(new BigInteger(lines.get(1).split(": ")[1]));
        computer.setRegisterC(new BigInteger(lines.get(2).split(": ")[1]));

        List<Integer> program = Arrays.stream(lines.get(4).split(": ")[1].split(","))
                .map(Integer::parseInt)
                .toList();

        computer.run(program);

        return computer.getOutputsString();
    }

    @Override
    public String part2(List<String> lines) {
        Computer computer = new Computer();

        computer.setRegisterA(new BigInteger(lines.get(0).split(": ")[1]));
        computer.setRegisterB(new BigInteger(lines.get(1).split(": ")[1]));
        computer.setRegisterC(new BigInteger(lines.get(2).split(": ")[1]));

        List<Integer> program = Arrays.stream(lines.get(4).split(": ")[1].split(","))
                .map(Integer::parseInt)
                .toList();

        // 2,4,1,5,7,5,0,3,1,6,4,3,5,5,3,0
        //BigInteger i = new BigInteger("100000000000000");
        String t = "000000000000000";

        for (int i = 0; i <= 9; i++) {
            Computer copy = computer.copy();
            String s = t.replaceAll("0", Integer.toString(i));
            copy.setRegisterA(new BigInteger(s));
            copy.run(program);
            log.info("s = {} - output = {}", s, copy.getOutputsString());
        }

        return "";
    }

    @Getter
    @Setter
    @ToString
    public static class Computer {
        private BigInteger registerA;
        private BigInteger registerB;
        private BigInteger registerC;
        private Integer pointer = 0;
        private List<Integer> outputs = new ArrayList<>();

        public BigInteger getComboOperand(Integer operand) {
            if (0 <= operand && operand <= 3) {
                return new BigInteger(String.valueOf(operand));
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

        public void movePointer() {
            movePointer(null);
        }

        public void movePointer(Integer value) {
            if (Objects.nonNull(value)) {
                pointer = value;
            } else {
                pointer += 2;
            }
        }

        public void run(List<Integer> program) {
            boolean run = true;
            while (run) {
                try {
                    Integer opcode = program.get(pointer);
                    Ins ins = Ins.getByOpcode(opcode);
                    //log.info(ins.toString());
                    Integer operand = program.get(pointer + 1);
                    ins.compute.accept(operand, this);
                } catch (Exception e) {
                    //log.error("halt", e);
                    run = false;
                }
            }
        }

        public String getOutputsString() {
            return outputs.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        }

        public Computer copy() {
            Computer computer = new Computer();
            computer.setRegisterA(registerA);
            computer.setRegisterB(registerB);
            computer.setRegisterC(registerC);
            computer.setPointer(pointer);
            computer.setOutputs(new ArrayList<>(outputs));
            return computer;
        }
    }

    public enum Ins {
        ADV((integer, computer) -> {
            BigInteger value = computer.getRegisterA().divide(BigInteger.TWO.pow(computer.getComboOperand(integer).intValue()));
            computer.setRegisterA(value);
            computer.movePointer();
        }), BXL((integer, computer) -> {
            BigInteger value = computer.getRegisterB().xor(new BigInteger(String.valueOf(integer)));
            computer.setRegisterB(value);
            computer.movePointer();
        }), BST((integer, computer) -> {
            BigInteger value = computer.getComboOperand(integer).mod(new BigInteger(String.valueOf(8)));
            computer.setRegisterB(value);
            computer.movePointer();
        }), JNZ((integer, computer) -> {
            if (computer.getRegisterA().compareTo(BigInteger.ZERO) != 0) {
                computer.movePointer(integer);
            } else {
                computer.movePointer();
            }
        }), BXC((integer, computer) -> {
            BigInteger value = computer.getRegisterB().xor(computer.getRegisterC());
            computer.setRegisterB(value);
            computer.movePointer();
        }), OUT((integer, computer) -> {
            BigInteger value = computer.getComboOperand(integer).mod(new BigInteger(String.valueOf(8)));
            computer.getOutputs().add(value.intValue());
            computer.movePointer();
        }), BDV((integer, computer) -> {
            BigInteger value = computer.getRegisterA().divide(BigInteger.TWO.pow(computer.getComboOperand(integer).intValue()));
            computer.setRegisterB(value);
            computer.movePointer();
        }), CDV((integer, computer) -> {
            BigInteger value = computer.getRegisterA().divide(BigInteger.TWO.pow(computer.getComboOperand(integer).intValue()));
            computer.setRegisterC(value);
            computer.movePointer();
        });

        public final BiConsumer<Integer, Computer> compute;

        Ins(BiConsumer<Integer, Computer> compute) {
            this.compute = compute;
        }

        public static Ins getByOpcode(Integer opcode) {
            return Ins.values()[opcode];
        }
    }

}
