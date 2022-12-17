package be.neonweb.aoc.solvers.d14;

import be.neonweb.aoc.utils.Position;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Data
public class Item {

    private String display;

    private Position position;

    public Item(String display, Position position) {
        this.display = display;
        this.position = position;
    }

    public Optional<Item> getAt(Position position, Set<Item> map) {
        return map.stream()
                  .filter(item -> item.getPosition()
                                      .equals(position))
                  .findFirst();
    }
}
