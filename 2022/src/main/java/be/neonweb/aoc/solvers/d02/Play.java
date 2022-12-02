package be.neonweb.aoc.solvers.d02;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum Play {

    ROCK(1),
    PAPER(2),
    SCISSOR(3);

    private static final Map<Play, List<Play>> WINS;

    static {
        WINS = new HashMap<>();
        WINS.put(ROCK, Arrays.asList(SCISSOR));
        WINS.put(PAPER, Arrays.asList(ROCK));
        WINS.put(SCISSOR, Arrays.asList(PAPER));
    }

    private int point;

    Play(int point) {
        this.point = point;
    }

    public static Play of(String value) {
        return switch (value) {
            case "A", "X" -> ROCK;
            case "B", "Y" -> PAPER;
            case "C", "Z" -> SCISSOR;
            default -> throw new IllegalArgumentException(value + " is not allowed");
        };
    }

    public static Play of2(String value, Play player1) {
        return switch (value) {
            case "X" -> WINS.get(player1)
                            .get(0);
            case "Y" -> player1;
            case "Z" -> WINS.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue()
                                                  .contains(player1))
                            .findFirst()
                            .map(Map.Entry::getKey)
                            .orElseThrow(IllegalArgumentException::new);
            default -> throw new IllegalArgumentException(value + " is not allowed");
        };
    }

    public int wins(Play other) {
        if (this.equals(other)) {
            return 0;
        }

        if (WINS.get(this)
                .contains(other)) {
            return 1;
        } else {
            return -1;
        }
    }

}
