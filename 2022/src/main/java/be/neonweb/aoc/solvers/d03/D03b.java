package be.neonweb.aoc.solvers.d03;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class D03b extends D03a {

    @Override
    public Object solve() throws Exception {

        List<List<Rucksack>> groupRucksacks = new ArrayList<>();

        int i = 0;
        List<Rucksack> group = new ArrayList<>();
        for (Rucksack rucksack : rucksacks) {
            if (i % 3 == 0) {
                group = new ArrayList<>();
                groupRucksacks.add(group);
            }
            group.add(rucksack);

            i++;
        }

        return groupRucksacks.stream()
                             .map(Rucksack::badge)
                             .map(Rucksack::point)
                             .reduce(0, Integer::sum);
    }
}
