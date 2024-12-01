package be.neonweb.adventofcode.aoc2023.d10;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pipe {

    private Position position;
    private Direction direction;

    public static Pipe of(Character c, int x, int y) {
        Pipe pipe = new Pipe();

        pipe.setDirection(Direction.of(c));
        pipe.setPosition(Position.builder()
                                 .x(x)
                                 .y(y)
                                 .build());

        return pipe;
    }

}
