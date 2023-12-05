package be.neonweb.adventofcode.aoc2023.util;

import java.util.Objects;

public class StringUtil {

    public static boolean isNullOrEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

}
