package be.neonweb.aoc.solvers.d04;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Pair {

    private List<Section> sections = new ArrayList<>();

    public static Pair of(String line) {
        Pair pair = new Pair();
        pair.sections = Arrays.stream(line.split(","))
                              .map(Section::of)
                              .collect(Collectors.toList());
        return pair;
    }

    public boolean contains() {
        return sections.get(0)
                       .contains(sections.get(1))
                || sections.get(1)
                           .contains(sections.get(0));
    }

    public boolean overlap() {
        return sections.get(0)
                       .overlap(sections.get(1))
                || sections.get(1)
                           .overlap(sections.get(0));
    }

}
