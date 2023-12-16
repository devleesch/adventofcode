package be.neonweb.adventofcode.aoc2023.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MathUtil {

    private static final List<Long> PRIMES = new ArrayList<>();

    public static boolean isPrime(Long value) {
        for (int i = 2; i < value; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static Double ppcm(List<Long> values) {
        return values.stream()
                     .map(MathUtil::factorization)
                     .map(longs -> longs.stream()
                                        .collect(Collectors.groupingBy(Function.identity())))
                     .map(Map::entrySet)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toMap(
                             Map.Entry::getKey,
                             entry -> entry.getValue()
                                           .size(),
                             (a, b) -> a >= b ? a : b))
                     .entrySet()
                     .stream()
                     .map(entry -> Math.pow(entry.getKey(), entry.getValue()))
                     .reduce((a, b) -> a * b)
                     .orElseThrow();
    }

    public static List<Long> factorization(Long value) {
        List<Long> factors = new ArrayList<>();
        Long orig = value;

        for (long j = 2; j <= orig; j++) {
            if (isPrime(j)) {
                while (value % j == 0) {
                    factors.add(j);
                    value /= j;
                }
            }
        }
        return factors;
    }

}
