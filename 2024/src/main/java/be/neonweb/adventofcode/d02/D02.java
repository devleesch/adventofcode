package be.neonweb.adventofcode.d02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D02 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input/d02/input.txt"));
        long count = lines.stream()
                          .map(s -> Arrays.asList(s.split(" ")))
                          .map(lString -> lString.stream()
                                                 .map(Integer::valueOf)
                                                 .toList())
                          .filter(D02::isValid)
                          .count();

        System.out.println("part1: " + count);

        long count2 = lines.stream()
                          .map(s -> Arrays.asList(s.split(" ")))
                          .map(lString -> lString.stream()
                                                 .map(Integer::valueOf)
                                                 .toList())
                          .filter(D02::isValid2)
                          .count();

        System.out.println("part2: " + count2);
    }

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
                ArrayList subL = new ArrayList(l);
                subL.remove(i);
                if (isValid(subL)) {
                    return true;
                }
            }
        }

        return valid;
    }

}
