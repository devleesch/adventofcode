package be.neonweb.aoc2025.d03;

import be.neonweb.aoc2025.Solver;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class D03 extends Solver {

    private List<Pack> packs;

    @Override
    public void parseFile() throws Exception {
        readFile(null);
        packs = lines.stream()
                     .map(Pack::of)
                     .toList();
    }

    @Override
    public Object part1() {
        return packs.stream()
                    .map(pack -> pack.maxJolts(2))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Object part2() {
        return packs.stream()
                    .map(pack -> pack.maxJolts(12))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Getter
    @Setter
    public static class Pack {

        private List<Integer> jolts;

        public BigDecimal maxJolts(int size) {
            int index = 0;
            BigDecimal max = BigDecimal.ZERO;
            for (int i = 0; i < size; i++) {
                List<Integer> subList = jolts.subList(index, jolts.size() - (size - i - 1));
                Integer i1 = subList.stream()
                                    .max(Integer::compareTo)
                                    .orElseThrow();

                index = index + subList.indexOf(i1) + 1;
                BigDecimal v = new BigDecimal(i1);
                max = max.add(v.multiply(BigDecimal.TEN.pow(size - i - 1)));
            }
            return max;
        }

        public static Pack of(String line) {
            Pack pack = new Pack();
            pack.setJolts(Arrays.stream(line.split(""))
                                .map(Integer::valueOf)
                                .toList());
            return pack;
        }

    }


}
