package be.neonweb.aoc.solvers.d22;

import java.util.function.Function;

public enum Facing {
    LEFT(2, me -> new Me(me.getX() - 1, me.getY(), me.getFacing())),
    RIGHT(0, me -> new Me(me.getX() + 1, me.getY(), me.getFacing())),
    BOTTOM(1, me -> new Me(me.getX(), me.getY() + 1, me.getFacing())),
    TOP(3, me -> new Me(me.getX(), me.getY() - 1, me.getFacing()));

    private int value;
    private Function<Me, Me> forward;

    Facing(int value, Function<Me, Me> forward) {
        this.value = value;
        this.forward = forward;
    }

    public Me move(Me me) {
        return forward.apply(me);
    }

    public Facing turnLeft() {
        return switch (this) {
            case LEFT -> BOTTOM;
            case RIGHT -> TOP;
            case BOTTOM -> RIGHT;
            case TOP -> LEFT;
        };
    }

    public Facing turnRight() {
        return switch (this) {
            case LEFT -> TOP;
            case RIGHT -> BOTTOM;
            case BOTTOM -> LEFT;
            case TOP -> RIGHT;
        };
    }

    public int getValue() {
        return value;
    }

}
