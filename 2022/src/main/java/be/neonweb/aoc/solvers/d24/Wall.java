package be.neonweb.aoc.solvers.d24;

import be.neonweb.aoc.utils.Position;

public class Wall extends Item {

    public Wall(Position position) {
        super(position);
    }

    @Override
    public Position positionAt(int minute, int width, int height) {
        return this.getPosition();
    }

}
