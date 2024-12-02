package be.neonweb.adventofcode.d02;

import be.neonweb.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D02 extends Day {

    public static boolean isValid(List<Integer> l) {
        if (l.size() < 2) {
            return true;
        }

        Integer prev = l.get(0);
        boolean inc = l.get(0) < l.get(1);
        for (int i = 1; i < l.size(); i++) {
            Integer current = l.get(i);

            if (Math.abs(prev - current) < 1 || Math.abs(prev - current) > 3) {
                return false;
            }

            if (inc) {
                if (prev >= current) {
                    return false;
                }
            } else {
                if (prev <= current) {
                    return false;
                }
            }
            prev = current;
        }
        return true;
    }

    public static boolean isValid2(List<Integer> l) {
        boolean valid = isValid(l);

        if (!valid) {
            for (int i = 0; i < l.size(); i++) {
                ArrayList<Integer> subL = new ArrayList<>(l);
                subL.remove(i);
                if (isValid(subL)) {
                    return true;
                }
            }
        }

        return valid;
    }

    @Override
    public String getDay() {
        return "02";
    }

    @Override
    public String part1(List<String> lines) {
        long count = lines.stream()
                          .map(s -> Arrays.asList(s.split(" ")))
                          .map(lString -> lString.stream()
                                                 .map(Integer::valueOf)
                                                 .toList())
                          .filter(D02::isValid)
                          .count();
        return Long.toString(count);
    }

    @Override
    public String part2(List<String> lines) {
        long count = lines.stream()
                          .map(s -> Arrays.asList(s.split(" ")))
                          .map(lString -> lString.stream()
                                                 .map(Integer::valueOf)
                                                 .toList())
                          .filter(D02::isValid2)
                          .count();
        return Long.toString(count);
    }
}
