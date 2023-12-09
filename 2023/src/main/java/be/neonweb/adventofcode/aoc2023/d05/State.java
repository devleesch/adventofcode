package be.neonweb.adventofcode.aoc2023.d05;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class State {
    private String name;
    private Long value;

    public void apply(Mapper mapper) {
        name = mapper.getTo();
        value = mapper.convert(value);
    }
}
