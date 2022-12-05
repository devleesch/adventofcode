package be.neonweb.aoc.solvers.d05;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Slf4j
public class D05a extends Solver {

    private static final int NB_STACKS = 9;

    List<Stack<Character>> stacks = new ArrayList<>();
    List<Move> moves = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d05/input.txt"));

        for (int i = 0; i < NB_STACKS; i++) {
            stacks.add(new Stack<>());
        }

        int i = 0;

        // Crates
        String line;
        while (i < 8) {
            line = lines.get(i);
            for (int j = 0; j < NB_STACKS; j++) {
                char c = line.charAt(1 + j * 4);
                if (c != ' ') {
                    stacks.get(j)
                          .push(c);
                }
            }
            i++;
        }
        stacks = stacks.stream()
                       .map(D05a::reverse)
                       .collect(Collectors.toList());

        // skip 2 lines
        i += 2;

        // Moves
        while (i < lines.size()) {
            moves.add(Move.of(lines.get(i)));
            i++;
        }
    }

    @Override
    public Object solve() throws Exception {
        moves.forEach(move -> move.applyTo(stacks));
        return stacks.stream()
                .map(Stack::peek)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static Stack<Character> reverse(Stack<Character> stack) {
        Stack<Character> reversed = new Stack<>();
        while (!stack.isEmpty()) {
            reversed.push(stack.pop());
        }
        return reversed;
    }
}
