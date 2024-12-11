package be.neonweb.adventofcode.d11;

import be.neonweb.adventofcode.Day;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class D11 extends Day {

    private static BigInteger _2024 = BigInteger.valueOf(2024);
    private static final Map<Integer, Map<BigInteger, BigInteger>> MEMORY = new HashMap<>();

    @Override
    public String part1(List<String> lines) {
        List<BigInteger> values = Arrays.stream(lines.getFirst()
                                                     .split(" "))
                                        .map(BigInteger::new)
                                        .toList();

        return values.stream()
                     .map(value -> blink(value, 1, 25))
                     .reduce(BigInteger.ZERO, BigInteger::add)
                     .toString();
    }

    private static List<BigInteger> blinkAll(List<BigInteger> values) {
        return values.stream()
                     .flatMap(D11::blinkOne)
                     .toList();
    }

    private static Stream<BigInteger> blinkOne(BigInteger value) {
        if (BigInteger.ZERO.equals(value)) {
            return Stream.of(BigInteger.ONE);
        } else if (value.toString()
                        .length() % 2 == 0) {
            int length = value.toString()
                              .length();
            BigInteger left = new BigInteger(value.toString()
                                                  .substring(0, length / 2));
            BigInteger right = new BigInteger(value.toString()
                                                   .substring(length / 2, length));
            return Stream.of(left, right);
        } else {
            return Stream.of(value.multiply(_2024));
        }
    }

    @Override
    public String part2(List<String> lines) {
        MEMORY.clear();

        List<BigInteger> values = Arrays.stream(lines.getFirst()
                                                     .split(" "))
                                        .map(BigInteger::new)
                                        .toList();

        return values.stream()
                     .map(value -> blink(value, 1, 75))
                     .reduce(BigInteger.ZERO, BigInteger::add)
                     .toString();
    }

    private static BigInteger blink(BigInteger value, int level, int maxLevel) {
        BigInteger result = MEMORY.getOrDefault(level, new HashMap<>())
                                  .get(value);
        if (Objects.nonNull(result)) {
            return result;
        }

        List<BigInteger> nexts = new ArrayList<>();
        if (BigInteger.ZERO.equals(value)) {
            nexts.add(BigInteger.ONE);
        } else if (value.toString()
                        .length() % 2 == 0) {
            int length = value.toString()
                              .length();
            nexts.add(new BigInteger(value.toString()
                                          .substring(0, length / 2)));
            nexts.add(new BigInteger(value.toString()
                                          .substring(length / 2)));
        } else {
            nexts.add(value.multiply(_2024));
        }

        if (level < maxLevel) {
            BigInteger reduce = nexts.stream()
                                     .map(next -> blink(next, level + 1, maxLevel))
                                     .reduce(BigInteger.ZERO, BigInteger::add);

            MEMORY.putIfAbsent(level, new HashMap<>());
            MEMORY.get(level)
                  .putIfAbsent(value, reduce);

            return reduce;
        } else {
            return new BigInteger(String.valueOf(nexts.size()));
        }
    }
}
