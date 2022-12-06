package be.neonweb.aoc.solvers.d05;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Slf4j
public class Move {

    private static final Pattern PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");

    private int quantity;
    private int source;
    private int destination;

    public void applyTo(List<Stack<Character>> stacks) {
        //log.info("applying " + this);
        for (int i = 0; i < quantity; i++) {
            stacks.get(destination - 1)
                  .push(stacks.get(source - 1)
                              .pop());
        }
    }

    public void applyTo2(List<Stack<Character>> stacks) {
        //log.info("applying " + this);
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < quantity; i++) {
            stack.push(stacks.get(source - 1)
                             .pop());
        }

        while (!stack.isEmpty()) {
            stacks.get(destination - 1)
                  .push(stack.pop());
        }
    }

    public static Move of(String s) {
        Matcher matcher = PATTERN.matcher(s);

        if (matcher.matches()) {
            Move move = new Move();
            move.quantity = Integer.parseInt(matcher.group(1));
            move.source = Integer.parseInt(matcher.group(2));
            move.destination = Integer.parseInt(matcher.group(3));
            return move;
        } else {
            throw new IllegalArgumentException("Unable to parse move");
        }
    }
}
