package be.neonweb.aoc.solvers.d17;

import be.neonweb.aoc.utils.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
@Setter
public class Chamber {

    private int width;

    private int turn;

    private Rock base = new Rock();
    private Set<Rock> rocks = new HashSet<>();

    public Chamber(int width) {
        this.width = width;

        // base
        rocks.add(base);
        for (int i = 0; i < width; i++) {
            base.getPieces()
                .add(new Position(i + 1, 0));
        }
        for (int i = 0; i < 4; i++) {
            base.getPieces()
                .add(new Position(0, i));
            base.getPieces()
                .add(new Position(width - 1, i));
        }
    }

    public void turn(List<Direction> jets) {
        Rock rock = Rock.ROCKS.get((rocks.size() - 1) % Rock.ROCKS.size())
                              .copy();

        append(rock);

        do {
            Direction direction = jets.get(turn % jets.size());
            rock.move(direction, rocks);
            turn++;
        } while (rock.move(Direction.DOWN, rocks));

        rocks.add(rock);
        if ((rocks.size() - 1) % 100 == 0) {
            log.info("Rock " + (rocks.size() - 1));
        }
        //display();
    }

    public void append(Rock rock) {
        int height = getHeight();
        int chamberHeight = getChamberHeight();

        rock.init(new Position(3, height + 3));

        for (int y = 0; y < height + 4 - chamberHeight; y++) {
            base.getPieces()
                .add(new Position(0, chamberHeight + y));
            base.getPieces()
                .add(new Position(width - 1, chamberHeight + y));
        }
    }

    public void display() {

        int maxY = rocks.stream()
                        .map(Rock::getPieces)
                        .flatMap(Collection::stream)
                        .map(Position::getY)
                        .max(Integer::compareTo)
                        .get();

        for (int y = 0; y < maxY + 1; y++) {
            for (int x = 0; x < width; x++) {
                int finalX = x;
                int finalY = maxY - y;
                Optional<Position> first = rocks.stream()
                                                .map(Rock::getPieces)
                                                .flatMap(Collection::stream)
                                                .filter(position -> position.getX() == finalX && position.getY() == finalY)
                                                .findFirst();

                if (first.isPresent()) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public int getHeight() {
        return rocks.stream()
                    .map(Rock::getPieces)
                    .flatMap(Collection::stream)
                    .filter(position -> position.getX() > 0 && position.getX() < this.width - 1)
                    .map(Position::getY)
                    .max(Integer::compareTo)
                    .get() + 1;
    }

    public int getChamberHeight() {
        return rocks.stream()
                    .map(Rock::getPieces)
                    .flatMap(Collection::stream)
                    .filter(position -> position.getX() == 0)
                    .map(Position::getY)
                    .max(Integer::compareTo)
                    .get() + 1;
    }
}
