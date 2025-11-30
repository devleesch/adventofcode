package be.neonweb.aoc2015.d01;

import be.neonweb.aoc2015.AbstractDay;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class D01 extends AbstractDay {

    @Override
    public Object part1() {
        return lines.stream()
                    .flatMap(s -> Arrays.stream(s.split("")))
                    .map(c -> switch (c) {
                        case "(" -> 1;
                        case ")" -> -1;
                        default -> throw new IllegalStateException("Unexpected value: " + c);
                    })
                    .reduce(0, Integer::sum);
    }

    @Override
    public Object part2() {
        int floor = 0;
        int i = 0;
        for (String s : lines.get(0)
                             .split("")) {
            i++;

            floor += switch (s) {
                case "(" -> 1;
                case ")" -> -1;
                default -> throw new IllegalStateException("Unexpected value: " + s);
            };

            if (floor < 0) {
                return i;
            }
        }

        throw new IllegalStateException();
    }

}
