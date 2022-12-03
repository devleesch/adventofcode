package be.neonweb.aoc.solvers.d03;

import lombok.Data;
import org.eclipse.collections.impl.factory.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Rucksack {

    private List<String> compartments = new ArrayList<>();

    public char common() {
        return common(compartments.get(0), compartments.get(1))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    public static Set<Character> common(String s1, String s2) {
        return Sets.intersect(
                s1.chars()
                  .mapToObj(i -> (char) i)
                  .collect(Collectors.toSet()),
                s2.chars()
                  .mapToObj(i -> (char) i)
                  .collect(Collectors.toSet()));
    }

    public static char badge(List<Rucksack> rucksacks) {
        return rucksacks.stream()
                        .map(rucksack -> String.join("", rucksack.getCompartments()))
                        .map(compartment -> compartment.chars()
                                                       .mapToObj(i -> (char) i)
                                                       .collect(Collectors.toSet()))
                        .reduce(String.join("", rucksacks.get(0).compartments)
                                      .chars()
                                      .mapToObj(i -> (char) i)
                                      .collect(Collectors.toSet()), Sets::intersect)
                        .stream()
                        .findFirst()
                        .orElseThrow();
    }

    public static Rucksack of(String line) {
        Rucksack rucksack = new Rucksack();
        rucksack.compartments.add(line.substring(0, line.length() / 2));
        rucksack.compartments.add(line.substring(line.length() / 2));
        return rucksack;
    }

    public static int point(char c) {
        if (Character.isLowerCase(c)) {
            return c - 96;
        } else {
            return c - 64 + 26;
        }
    }

}
