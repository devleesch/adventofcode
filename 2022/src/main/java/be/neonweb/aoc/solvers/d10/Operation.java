package be.neonweb.aoc.solvers.d10;

import java.util.Map;

public abstract class Operation {

    protected int cycle;

    public Operation() {}

    public Operation(int cycle) {
        this.cycle = cycle;
    }

    public abstract void apply(int cycle, Map<String, Integer> vars);

    public static Operation of(String s) {
        String[] split = s.split(" ");
        if ("addx".equals(split[0])) {
            return new AddX(Integer.parseInt(split[1]));
        } else if ("noop".equals(split[0])) {
            return new Noop();
        } else {
            throw new IllegalArgumentException();
        }
    }

}
