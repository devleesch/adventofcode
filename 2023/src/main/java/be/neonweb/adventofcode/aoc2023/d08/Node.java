package be.neonweb.adventofcode.aoc2023.d08;

import lombok.Data;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Node {

    private static final Pattern PATTERN = Pattern.compile("(?<element>[A-Z0-9]{3}) = \\((?<left>[A-Z0-9]{3}), (?<right>[A-Z0-9]{3})\\)");

    private String element;

    private String leftStr;
    @ToString.Exclude
    private Node left;

    private String rightStr;
    @ToString.Exclude
    private Node right;

    public static Node of(String line) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.matches()) {
            Node node = new Node();
            node.element = matcher.group("element");
            node.leftStr = matcher.group("left");
            node.rightStr = matcher.group("right");
            return node;
        } else {
            throw new RuntimeException();
        }
    }

}
