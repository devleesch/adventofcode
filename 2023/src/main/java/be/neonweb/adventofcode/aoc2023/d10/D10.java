package be.neonweb.adventofcode.aoc2023.d10;

import be.neonweb.adventofcode.aoc2023.Solver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class D10 extends Solver {

    private Pipe start;
    private Map<Position, Pipe> map = new HashMap<>();

    public D10(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);
        return null;
    }

    @Override
    public Object part2(List<String> inputLines) {
        return null;
    }

    public void parse(List<String> inputLines) {
        for (int y = 0; y < inputLines.size(); y++) {
            for (int x = 0; x < inputLines.get(y)
                                          .length(); x++) {
                char c = inputLines.get(x)
                                   .charAt(y);

                if (c == 'S') {
                    start = Pipe.of(c, x, y);
                }
            }
        }

        int maxX = inputLines.get(0)
                             .length();
        int maxY = inputLines.size();

        start.getPosition()
             .around()
             .stream()
             .filter(position -> position.valid(0, maxX, 0, maxY))
             .forEach(System.out::println);
    }

    public static void main(String[] args) {
        D10 d10 = new D10("src/main/resources/10/test.txt");
        Solver.run(d10);
    }
}
