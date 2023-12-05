package be.neonweb.adventofcode.aoc2023.d02;

import lombok.Data;

import java.util.List;

@Data
public class Game {

    private int index;
    private List<CubeSet> cubeSets;

    public Long power() {
        CubeSet power = new CubeSet();

        power.setRed(cubeSets.stream()
                             .map(CubeSet::getRed)
                             .max(Long::compareTo)
                             .orElse(0L));

        power.setGreen(cubeSets.stream()
                             .map(CubeSet::getGreen)
                             .max(Long::compareTo)
                             .orElse(0L));

        power.setBlue(cubeSets.stream()
                             .map(CubeSet::getBlue)
                             .max(Long::compareTo)
                             .orElse(0L));

        return power.getRed() * power.getGreen() * power.getBlue();
    }

}
