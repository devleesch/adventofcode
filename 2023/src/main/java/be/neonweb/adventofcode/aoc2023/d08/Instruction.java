package be.neonweb.adventofcode.aoc2023.d08;

import java.util.function.Function;

public enum Instruction {
    LEFT(Node::getLeft),
    RIGHT(Node::getRight);

    private Function<Node, Node> next;

    Instruction(Function<Node, Node> next) {
        this.next = next;
    }

    public static Instruction of(String s) {
        return switch (s) {
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> throw new RuntimeException();
        };
    }

    public Node next(Node node) {
        return next.apply(node);
    }

}
