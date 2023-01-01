package be.neonweb.aoc.solvers.d24;

import be.neonweb.aoc.utils.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public abstract class Item {

    private Position position;

    public Item(Position position) {
        this.position = position;
    }

    public abstract Position positionAt(int minute, int width, int height);

    public static Optional<Item> of(Position position, String s) {
        return Optional.ofNullable(
                switch (s) {
                    case "." -> null;
                    case "#" -> new Wall(position);
                    default -> new Blizzard(position, s);
                }
        );
    }

}
