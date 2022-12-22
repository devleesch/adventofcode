package be.neonweb.aoc.solvers.d17;

import be.neonweb.aoc.utils.Position;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.collections.impl.factory.Sets;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Rock {

    public static List<Rock> ROCKS;
    private Set<Position> pieces = new HashSet<>();

    public void init(Position position) {
        pieces.forEach(p -> {
            p.setX(p.getX() + position.getX());
            p.setY(p.getY() + position.getY());
        });
    }

    public int height() {
        int min = pieces.stream()
                        .map(Position::getY)
                        .min(Integer::compareTo)
                        .get();
        int max = pieces.stream()
                        .map(Position::getY)
                        .max(Integer::compareTo)
                        .get();

        return max - min + 1;
    }

    public boolean move(Direction direction, Set<Rock> others) {
        Set<Position> positions = others.stream()
                                        .map(Rock::getPieces)
                                        .flatMap(Collection::stream)
                                        .collect(Collectors.toSet());
        return switch (direction) {
            case LEFT -> left(positions);
            case RIGHT -> right(positions);
            case DOWN -> down(positions);
        };
    }

    public Rock copy() {
        Rock rock = new Rock();

        rock.setPieces(pieces.stream()
                             .map(Position::copy)
                             .collect(Collectors.toSet()));

        return rock;
    }

    private boolean down(Set<Position> others) {
        Set<Position> newPieces = pieces.stream()
                                      .map(position -> new Position(position.getX(), position.getY() - 1))
                                      .collect(Collectors.toSet());
        if (Sets.intersect(newPieces, others)
                .size() > 0) {
            return false;
        }

        pieces = newPieces;
        return true;
    }

    private boolean left(Set<Position> others) {
        Set<Position> newPieces = pieces.stream()
                                      .map(Position::left)
                                      .collect(Collectors.toSet());

        if (Sets.intersect(newPieces, others).size() == 0) {
            pieces = newPieces;
        }

        return true;
    }

    private boolean right(Set<Position> others) {
        Set<Position> newPieces = pieces.stream()
                                        .map(Position::right)
                                        .collect(Collectors.toSet());

        if (Sets.intersect(newPieces, others).size() == 0) {
            pieces = newPieces;
        }

        return true;
    }

    static {
        ROCKS = new ArrayList<>();

        // ####
        Rock r1 = new Rock();
        ROCKS.add(r1);
        for (int x = 0; x < 4; x++) {
            r1.getPieces()
              .add(new Position(x, 0));
        }

        // .#.
        // ###
        // .#.
        Rock r2 = new Rock();
        ROCKS.add(r2);
        r2.getPieces()
          .add(new Position(1, 2));
        for (int x = 0; x < 3; x++) {
            r2.getPieces()
              .add(new Position(x, 1));
        }
        r2.getPieces()
          .add(new Position(1, 0));

        // ..#
        // ..#
        // ###
        Rock r3 = new Rock();
        ROCKS.add(r3);
        r3.getPieces()
          .add(new Position(2, 2));
        r3.getPieces()
          .add(new Position(2, 1));
        for (int x = 0; x < 3; x++) {
            r3.getPieces()
              .add(new Position(x, 0));
        }

        // #
        // #
        // #
        // #
        Rock r4 = new Rock();
        ROCKS.add(r4);
        for (int y = 0; y < 4; y++) {
            r4.getPieces()
              .add(new Position(0, y));
        }

        // ##
        // ##
        Rock r5 = new Rock();
        ROCKS.add(r5);
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                r5.getPieces()
                  .add(new Position(x, y));
            }
        }
    }
}
