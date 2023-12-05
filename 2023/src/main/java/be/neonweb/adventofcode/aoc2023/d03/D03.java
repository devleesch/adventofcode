package be.neonweb.adventofcode.aoc2023.d03;

import be.neonweb.adventofcode.aoc2023.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class D03 extends Solver {

    private int maxX;
    private int maxY;
    private List<Item> items = new ArrayList<>();
    private List<Symbol> symbols = new ArrayList<>();
    private Set<Position> symbolPositions;

    public D03(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);

        return items.stream()
                    .filter(this::isPart)
                    .map(Item::getNumber)
                    .reduce(0L, Long::sum);
    }

    @Override
    public Object part2(List<String> inputLines) {
        return symbols.stream()
                      .filter(symbol -> symbol.getValue() == '*')
                      .map(this::getGear)
                      .filter(l -> l.size() == 2)
                      .map(gear -> gear.stream()
                                       .map(Item::getNumber)
                                       .reduce(1L, (a, b) -> a * b))
                      .reduce(0L, Long::sum);
    }

    private List<Item> getGear(Symbol symbol) {
        return items.stream()
                    .filter(item -> item.around(0, maxX, 0, maxY)
                                        .contains(symbol.getPosition()))
                    .toList();
    }

    private void parse(List<String> inputLines) {
        maxX = inputLines.get(0)
                         .length() - 1;
        maxY = inputLines.size() - 1;

        int y = 0;
        for (String inputLine : inputLines) {
            String part = "";
            int x = 0;
            for (char c : inputLine.toCharArray()) {
                if (Character.isDigit(c)) {
                    part += c;
                } else {
                    if (!part.isEmpty()) {
                        Item item = new Item();
                        item.setNumber(Long.parseLong(part));
                        item.setStart(new Position(x - part.length(), y));
                        item.setEnd(new Position(x - 1, y));
                        items.add(item);
                        part = "";
                    }

                    if (c != '.') {
                        Symbol symbol = new Symbol();
                        symbol.setValue(c);
                        symbol.setPosition(new Position(x, y));
                        symbols.add(symbol);
                    }
                }
                x++;
            }

            if (!part.isEmpty()) {
                Item item = new Item();
                item.setNumber(Long.parseLong(part));
                item.setStart(new Position(x - part.length(), y));
                item.setEnd(new Position(x - 1, y));
                items.add(item);
            }

            y++;
        }

        symbolPositions = symbols.stream()
                                 .map(Symbol::getPosition)
                                 .collect(Collectors.toSet());
    }

    private boolean isPart(Item item) {
        return item.around(0, maxX, 0, maxY)
                   .stream()
                   .anyMatch(symbolPositions::contains);
    }

    public static void main(String[] args) {
        D03 d03 = new D03("src/main/resources/03/input.txt");
        Solver.run(d03);
    }
}
