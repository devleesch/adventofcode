package be.neonweb.adventofcode.aoc2023.d05;

import lombok.Data;

@Data
public class Range {

    private Long source;
    private Long destination;
    private Long length;

    public boolean isBetween(Long value) {
        return source <= value && value < (source + length);
    }

    public Long convert(Long value) {
        return destination + (value - source);
    }

}
