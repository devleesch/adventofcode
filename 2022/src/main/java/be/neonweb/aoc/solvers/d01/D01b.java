package be.neonweb.aoc.solvers.d01;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@Slf4j
public class D01b extends D01a {

    @Override
    public void config() throws Exception {
    }

    @Override
    public Long solve() throws Exception {
        Long top3 = elves.stream()
                         .sorted(Comparator.comparing(Elf::calories)
                                           .reversed())
                         .limit(3)
                         .map(Elf::calories)
                         .reduce(0L, Long::sum);
        return top3;
    }
}
