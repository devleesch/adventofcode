package be.neonweb.aoc2015.d02;

import be.neonweb.aoc2015.AbstractDay;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class D02 extends AbstractDay {
    @Override
    public Object part1() {
        return lines.stream()
                    .map(Present::of)
                    .map(Present::wrapping)
                    .reduce(Integer::sum)
                    .orElseThrow();
    }

    @Override
    public Object part2() {
        return lines.stream()
                    .map(Present::of)
                    .map(Present::ribbon)
                    .reduce(Integer::sum)
                    .orElseThrow();
    }
}
