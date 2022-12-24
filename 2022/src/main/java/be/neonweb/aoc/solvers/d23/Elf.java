package be.neonweb.aoc.solvers.d23;

import be.neonweb.aoc.utils.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Elf extends Position {

    private int index;

    public Elf(int x, int y, int index) {
        super(x, y);
        this.index = index;
    }

    public boolean hasNeighboors(Collection<Elf> elves) {
        return Arrays.stream(Direction.values())
                     .map(direction -> direction.apply(this))
                     .anyMatch(elf -> elves.stream()
                                           .anyMatch(other -> other.getX() == elf.getX() && other.getY() == elf.getY()));
    }

    public Elf propose(Queue<List<Direction>> stack, Collection<Elf> others) {
        for (List<Direction> directions : stack) {
            Set<Elf> collect = directions.stream()
                                         .map(direction -> direction.apply(this))
                                         .collect(Collectors.toSet());

            boolean directionFree = others.stream()
                                          .noneMatch(other -> collect.stream()
                                                                     .anyMatch(direction -> other.getX() == direction.getX() && other.getY() == direction.getY()));

            if (directionFree) {
                return directions.get(1)
                                 .apply(this);
            }
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Elf elf = (Elf) o;
        return index == elf.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), index);
    }
}
