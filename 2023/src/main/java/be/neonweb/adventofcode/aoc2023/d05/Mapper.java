package be.neonweb.adventofcode.aoc2023.d05;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Mapper {
    public String from;
    public String to;
    public List<Range> ranges = new ArrayList<>();

    public Long convert(Long value) {
        Optional<Range> optionalRange = ranges.stream()
                                              .filter(range -> range.isBetween(value))
                                              .findFirst();
        if (optionalRange.isPresent()) {
            return optionalRange.get().convert(value);
        }
        return value;
    }
}
