package be.neonweb.aoc.solvers.d12;

import be.neonweb.aoc.utils.Position;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class Square implements Serializable {

    private Position position;

    private Character letter;

    private int elevation;

    private int distance;

    private Square previous;

    public Square(Position position, Character letter) {
        this.position = position;
        this.letter = letter;
        this.elevation = getElevation(letter);
    }

    public List<Square> adjacents(Set<Square> squares) {
        List<Square> adjacents = new ArrayList<>();
        up(squares).ifPresent(adjacents::add);
        down(squares).ifPresent(adjacents::add);
        left(squares).ifPresent(adjacents::add);
        right(squares).ifPresent(adjacents::add);
        return adjacents;
    }

    public void updateDistance(Square start) {
        int currentDistance = start.getDistance() + 1;
        if (currentDistance < distance) {
            distance = currentDistance;
            previous = start;
        }
    }

    public Square copy() {
        return new Square(this.position.copy(), this.letter);
    }

    private Optional<Square> up(Set<Square> squares) {
        return squares.stream()
                      .filter(square -> this.getPosition()
                                            .up()
                                            .equals(square.getPosition()))
                      .filter(this::canGoTo)
                      .findFirst();
    }

    private Optional<Square> down(Set<Square> squares) {
        return squares.stream()
                      .filter(square -> this.getPosition()
                                            .down()
                                            .equals(square.getPosition()))
                      .filter(this::canGoTo)
                      .findFirst();
    }

    private Optional<Square> left(Set<Square> squares) {
        return squares.stream()
                      .filter(square -> this.getPosition()
                                            .left()
                                            .equals(square.getPosition()))
                      .filter(this::canGoTo)
                      .findFirst();
    }

    private Optional<Square> right(Set<Square> squares) {
        return squares.stream()
                      .filter(square -> this.getPosition()
                                            .right()
                                            .equals(square.getPosition()))
                      .filter(this::canGoTo)
                      .findFirst();
    }

    private boolean canGoTo(Square other) {
        return other.getElevation() - 1 <= elevation;
    }

    private int getElevation(Character c) {
        if (c.equals('S')) {
            c = 'a';
        } else if (c.equals('E')) {
            c = 'z';
        }
        return 26 - ('z' - c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(position, square.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
