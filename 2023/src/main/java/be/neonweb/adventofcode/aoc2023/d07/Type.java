package be.neonweb.adventofcode.aoc2023.d07;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public enum Type {

    FIVE(6, List.of(5)),
    FOUR(5, List.of(1, 4)),
    FULL(4, List.of(2, 3)),
    THREE(3, List.of(1, 1, 3)),
    TWO_PAIR(2, List.of(1, 2, 2)),
    PAIR(1, List.of(1, 1, 1, 2)),
    HIGH(0, List.of(1, 1, 1, 1, 1));

    private Integer index;
    private List<Integer> repartiton;

    Type(Integer index, List<Integer> repartition) {
        this.index = index;
        this.repartiton = repartition;
    }

    public static Type of(List<Integer> repartition) {
        for (Type type : Type.values()) {
            if (type.repartiton.equals(repartition)) {
                return type;
            }
        }
        throw new RuntimeException("No type found !");
    }
}
