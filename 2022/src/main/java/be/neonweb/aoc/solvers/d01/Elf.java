package be.neonweb.aoc.solvers.d01;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Elf {

    private List<Snack> snacks = new ArrayList<>();

    public Long calories() {
        return snacks.stream()
                     .map(Snack::getCalories)
                     .reduce(0L, Long::sum);
    }

}
