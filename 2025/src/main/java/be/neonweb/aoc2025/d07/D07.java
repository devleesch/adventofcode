package be.neonweb.aoc2025.d07;

import be.neonweb.aoc2025.Solver;
import be.neonweb.aoc2025.utils.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class D07 extends Solver {

    private Map<Position<Void>, Long> memory = new HashMap<>();

    @Override
    public void parseFile() throws Exception {
        //readFile("test1.txt");
        readFile(null);
    }

    @Override
    public Object part1() {
        int nbSplit = 0;
        for (int y = 1; y < lines.size(); y++) {
            String current = lines.get(y);
            StringBuilder stringBuilder = new StringBuilder(current);
            for (int x = 0; x < lines.get(y)
                                     .length(); x++) {
                char c = current.charAt(x);
                if (lines.get(y - 1)
                         .charAt(x) == 'S' || lines.get(y - 1)
                                                   .charAt(x) == '|') {
                    if (c == '.') {
                        stringBuilder.setCharAt(x, '|');
                    } else if (c == '^') {
                        boolean split = false;
                        if (x - 1 >= 0) {
                            stringBuilder.setCharAt(x - 1, '|');
                            split = true;
                        }

                        if (x + 1 < lines.getFirst()
                                         .length()) {
                            stringBuilder.setCharAt(x + 1, '|');
                            split = true;
                        }

                        nbSplit += split ? 1 : 0;
                    }
                }
            }
            lines.set(y, stringBuilder.toString());
        }

        return nbSplit;
    }

    @Override
    public Object part2() {
        int xS = -1;
        for (int x = 0; x < lines.getFirst()
                                 .length(); x++) {
            if (lines.getFirst()
                     .charAt(x) == 'S') {
                xS = x;
            }
        }

        return getTimelines(xS, 0, lines);
        // 1476578730 -- too low
    }

    private long getTimelines(int x, int y, List<String> lines) {
        if (y >= lines.size()) {
            return 1;
        }

        Position<Void> position = new Position<>(x, y, null);

        if (memory.containsKey(position)) {
            return memory.get(position);
        }

        char current = lines.get(y)
                            .charAt(x);

        if (current == '^') {
            long count = 0;

            if (x - 1 >= 0) {
                count += getTimelines(x - 1, y + 1, lines);
            }

            if (x + 1 < lines.getFirst()
                             .length()) {
                count += getTimelines(x + 1, y + 1, lines);
            }

            memory.put(position, count);
            return count;
        } else if (current == 'S' || current == '|') {
            return getTimelines(x, y + 1, lines);
        }

        throw new IllegalStateException();
    }
}
