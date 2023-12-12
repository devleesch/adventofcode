package be.neonweb.adventofcode.aoc2023.d08;

import be.neonweb.adventofcode.aoc2023.Solver;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

        return null;
    }

    @Override
    public Object part2(List<String> inputLines) {
        return null;
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
        D08 d08 = new D08("src/main/resources/08/test1.txt");
        Solver.run(d08);
    }

}
