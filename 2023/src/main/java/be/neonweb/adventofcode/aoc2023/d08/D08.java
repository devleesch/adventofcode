package be.neonweb.adventofcode.aoc2023.d08;

import be.neonweb.adventofcode.aoc2023.Solver;
import be.neonweb.adventofcode.aoc2023.util.MathUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class D08 extends Solver {

    private List<Instruction> instructions;
    private Map<String, Node> nodes;

    public D08(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);
        return stepsToZ(nodes.get("AAA"));
    }

    @Override
    public Object part2(List<String> inputLines) {
        parse(inputLines);

        List<Long> loop = nodes.values()
                               .stream()
                               .filter(node -> node.getElement()
                                                   .endsWith("A"))
                               .map(this::stepsToEndZ)
                               .toList();

        return MathUtil.ppcm(loop)
                       .longValue();
    }

    public long stepsToZ(Node current) {
        int i = 0;
        while (!current.getElement()
                       .equals("ZZZ")) {
            current = instructions.get(i % instructions.size())
                                  .next(current);
            i++;
        }

        return i;
    }

    public long stepsToEndZ(Node current) {
        int i = 0;
        while (!current.getElement()
                       .endsWith("Z")) {
            current = instructions.get(i % instructions.size())
                                  .next(current);
            i++;
        }

        return i;
    }

    public void parse(List<String> inputLines) {
        instructions = inputLines.get(0)
                                 .chars()
                                 .mapToObj(c -> String.valueOf((char) c))
                                 .map(Instruction::of)
                                 .toList();

        nodes = inputLines.stream()
                          .skip(2)
                          .map(Node::of)
                          .collect(Collectors.toMap(Node::getElement, Function.identity()));

        nodes.values()
             .forEach(node -> {
                 node.setLeft(nodes.get(node.getLeftStr()));
                 node.setRight(nodes.get(node.getRightStr()));
             });
    }

    public static void main(String[] args) {
        D08 d08 = new D08("src/main/resources/08/input.txt");
        Solver.run(d08, false, true);
    }

}
