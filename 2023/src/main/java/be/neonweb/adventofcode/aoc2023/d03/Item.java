package be.neonweb.adventofcode.aoc2023.d03;

import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Item {

    private Position start;
    private Position end;
    private Long number;

    public Set<Position> around(int minX, int maxX, int minY, int maxY) {
        Set<Position> betweenX = start.betweenX(end);
        Set<Position> around = betweenX.stream()
                                       .flatMap(position -> position.around(minX, maxX, minY, maxY)
                                                                    .stream())
                                       .collect(Collectors.toSet());
        around.removeAll(betweenX);
        return around;
    }


}
