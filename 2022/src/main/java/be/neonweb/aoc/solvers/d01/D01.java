package be.neonweb.aoc.solvers.d01;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class D01 extends Solver {

    private List<Elf> elves = new ArrayList<>();

    @Override
    public void config() throws Exception {}

    @Override
    public void parse() throws Exception {
        Elf current = new Elf();
        for (String line : Files.readAllLines(Path.of("src/main/resources/d01/input.txt"))) {
            if (line.isEmpty()) {
                this.elves.add(current);
                current = new Elf();
            } else {
                Snack snack = new Snack();
                snack.setCalories(Long.parseLong(line));
                current.getSnacks()
                       .add(snack);
            }
        }
    }

    @Override
    public Object part1() throws Exception {
        Optional<Long> max = elves.stream()
                                  .map(Elf::getSnacks)
                                  .map(snacks -> snacks.stream()
                                                       .map(Snack::getCalories)
                                                       .reduce(0L, Long::sum))
                                  .max(Long::compare);
        return max.orElse(null);
    }

    @Override
    public Object part2() throws Exception {
        Long top3 = elves.stream()
                         .sorted(Comparator.comparing(Elf::calories)
                                           .reversed())
                         .limit(3)
                         .map(Elf::calories)
                         .reduce(0L, Long::sum);
        return top3;
    }
}
