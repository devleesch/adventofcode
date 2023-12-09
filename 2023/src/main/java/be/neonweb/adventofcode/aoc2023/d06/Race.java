package be.neonweb.adventofcode.aoc2023.d06;

import lombok.Data;

import java.util.stream.LongStream;

@Data
public class Race {

    private Long time;
    private Long distance;

    public Long distance(Long hold) {
        return (time - hold) * hold;
    }

    public Long nbWin() {
        return LongStream.range(0, time)
                         .filter(hold -> distance(hold) > distance)
                         .count();
    }

}
