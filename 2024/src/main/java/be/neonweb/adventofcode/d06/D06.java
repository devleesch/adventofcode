package be.neonweb.adventofcode.d06;

import be.neonweb.adventofcode.Day;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D06 extends Day {

    private static final Logger log = LoggerFactory.getLogger(D06.class);
    private Map<Integer, Map<Integer, Position>> positions;
    private Set<Position> visited;
    private Collector<Position, ?, Map<Integer, Map<Integer, Position>>> positionMapCollector = Collectors.toMap(position -> position.x, position -> {
        HashMap<Integer, Position> yPositions = new HashMap<>();
        yPositions.put(position.y, position);
        return yPositions;
    }, (yPositions1, yPositions2) -> {
        yPositions1.putAll(yPositions2);
        return yPositions1;
    });

    @Override
    public String getDay() {
        return "06";
    }

    @Override
    public String part1(List<String> lines) {
        positions = IntStream.range(0, lines.size())
                             .mapToObj(y -> {
                                 String line = lines.get(y);
                                 return IntStream.range(0, line.length())
                                                 .mapToObj(x -> new Position(x, y, String.valueOf(line.charAt(x))));
                             })
                             .flatMap(positionStream -> positionStream)
                             .collect(positionMapCollector);

        Guard guard = new Guard();
        guard.position = getStart();
        guard.heading = Heading.NORTH;
        visited = new HashSet<>();
        while (true) {
            visited.add(guard.position);
            try {
                guard.move(positions);
            } catch (IllegalStateException e) {
                break;
            }
        }

        return "" + visited.size();
    }

    private Position getStart() {
        return positions.values()
                        .stream()
                        .flatMap(xPositions -> xPositions.values()
                                                         .stream())
                        .filter(position -> "^".equals(position.value))
                        .findFirst()
                        .orElseThrow();
    }

    @Override
    public String part2(List<String> lines) {
        Position start = getStart();

        return "" + visited.stream()
                           .filter(position -> !position.equals(start))
                           .filter(this::isLoop)
                           .count();

    }

    public boolean isLoop(Position position) {

        //log.info("isLoop for " + position);

        Map<Integer, Map<Integer, Position>> newMap = positions.values()
                                                               .stream()
                                                               .flatMap(xPositions -> xPositions.values()
                                                                                                .stream()
                                                                                                .map(Position::copy))
                                                               .collect(positionMapCollector);

        newMap.get(position.x)
              .get(position.y).value = "#";

        Position start = getStart();
        Guard guard = new Guard();
        guard.heading = Heading.NORTH;
        guard.position = start;

        for (int i = 0; i < Math.pow(positions.size(), 2); i++) {
            try {
                guard.move(newMap);
            } catch (IllegalStateException e) {
                return false;
            }
        }

        return true;
    }

    @ToString
    public static class Position {
        private int x;
        private int y;
        private String value;

        public Position(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public Position copy() {
            return new Position(this.x, this.y, this.value);
        }

    }

    public static class Guard {
        private Position position;
        private Heading heading;

        public void move(Map<Integer, Map<Integer, Position>> positions) {
            int nx = this.position.x + heading.dx;
            int ny = this.position.y + heading.dy;

            if (nx < 0 || ny < 0 || nx >= positions.size() || ny >= positions.get(nx)
                                                                             .size()) {
                throw new IllegalStateException();
            }

            Position nPosition = positions.get(nx)
                                          .get(ny);

            //log.info("nx={}, ny={}", nx, ny);

            if ("#".equals(nPosition.value)) {
                this.heading = this.heading.rotate();
            } else {
                this.position = nPosition;
            }
        }
    }

    public enum Heading {
        NORTH(0, -1),
        SOUTH(0, 1),
        WEST(-1, 0),
        EAST(1, 0);

        private int dx;
        private int dy;

        Heading(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public Heading rotate() {
            return switch (this) {
                case NORTH -> EAST;
                case SOUTH -> WEST;
                case WEST -> NORTH;
                case EAST -> SOUTH;
            };
        }
    }
}
