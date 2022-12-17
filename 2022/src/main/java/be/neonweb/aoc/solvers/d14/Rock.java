package be.neonweb.aoc.solvers.d14;

import be.neonweb.aoc.utils.Position;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rock extends Item {

    public Rock(Position position) {
        super("#", position);
    }

    public static Set<Rock> of(String s) {
        List<Position> positions = Arrays.stream(s.split(" -> "))
                                         .map(split -> {
                                             String[] xy = split.split(",");
                                             return new Position(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                                         })
                                         .toList();

        return IntStream.range(0, positions.size() - 1)
                        .mapToObj(value -> positions.stream()
                                                    .skip(value)
                                                    .limit(2)
                                                    .collect(Collectors.toList()))
                        .map(couple -> getRocks(couple.get(0), couple.get(1)))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
    }

    private static Set<Rock> getRocks(Position start, Position end) {
        Set<Rock> items = new HashSet<>();
        Set<Position> occupied = new HashSet<>();

        for (int x = Math.min(start.getX(), end.getX()); x < Math.max(start.getX(), end.getX()) + 1; x++) {
            for (int y = Math.min(start.getY(), end.getY()); y < Math.max(start.getY(), end.getY()) + 1; y++) {
                Position position = new Position(x, y);
                if (!occupied.contains(position)) {
                    occupied.add(position);
                    items.add(new Rock(position));
                }
            }
        }

        return items;
    }

}
