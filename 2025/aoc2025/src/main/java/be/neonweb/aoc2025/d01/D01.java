package be.neonweb.aoc2025.d01;

import be.neonweb.aoc2025.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class D01 extends Solver {

    @Override
    public List<String> readFile() throws Exception {
        return readFile(null);
    }

    @Override
    public Object part1() {
        lines.forEach(log::info);
        return null;
    }

    @Override
    public Object part2() {
        return null;
    }
}
