package be.neonweb.aoc.solvers.d14;

import be.neonweb.aoc.utils.Position;

import java.util.Set;

public class Sand extends Item {

    public Sand(Position position) {
        super("o", position);
    }

    public boolean fall(Set<Item> map, int maxY) {
        Position next;
        do {
            next = new Position(this.getPosition()
                                    .getX(), this.getPosition()
                                                 .getY() + 1);

            if (next.getY() > maxY) {
                return false;
            }

            if (getAt(next, map).isEmpty()) {
                this.setPosition(next);
            } else {
                Position left = new Position(next.getX() - 1, next.getY());
                if (getAt(left, map).isEmpty()) {
                    this.setPosition(left);
                    next = left;
                } else {
                    Position right = new Position(next.getX() + 1, next.getY());
                    if (getAt(right, map).isEmpty()) {
                        this.setPosition(right);
                        next = right;
                    }
                }
            }
        } while (next.equals(this.getPosition()));

        return true;
    }

    public void fall2(Set<Item> map, int maxY) {
        Position next;
        do {
            next = new Position(this.getPosition()
                                    .getX(), this.getPosition()
                                                 .getY() + 1);

            if (next.getY() > maxY) {
                break;
            }

            if (getAt(next, map).isEmpty()) {
                this.setPosition(next);
            } else {
                Position left = new Position(next.getX() - 1, next.getY());
                if (getAt(left, map).isEmpty()) {
                    this.setPosition(left);
                    next = left;
                } else {
                    Position right = new Position(next.getX() + 1, next.getY());
                    if (getAt(right, map).isEmpty()) {
                        this.setPosition(right);
                        next = right;
                    }
                }
            }
        } while (next.equals(this.getPosition()));
    }
}
