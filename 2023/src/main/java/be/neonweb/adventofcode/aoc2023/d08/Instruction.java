package be.neonweb.adventofcode.aoc2023.d08;

public enum Instruction {

    LEFT,
    RIGHT;

    public static Instruction of(String s) {
        return switch (s) {
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> throw new RuntimeException();
        };
    }

}
