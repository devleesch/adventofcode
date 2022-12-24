package be.neonweb.aoc.solvers.d23;

import java.util.function.Function;

public enum Direction {
    N(elf -> new Elf(elf.getX(), elf.getY() - 1, elf.getIndex())),
    E(elf -> new Elf(elf.getX() + 1, elf.getY(), elf.getIndex())),
    S(elf -> new Elf(elf.getX(), elf.getY() + 1, elf.getIndex())),
    W(elf -> new Elf(elf.getX() - 1, elf.getY(), elf.getIndex())),
    NW(elf -> W.apply(N.apply(elf))),
    NE(elf -> E.apply(N.apply(elf))),
    SW(elf -> W.apply(S.apply(elf))),
    SE(elf -> E.apply(S.apply(elf)));

    private Function<Elf, Elf> move;

    public Elf apply(Elf elf) {
        return this.move.apply(elf);
    }

    Direction(Function<Elf, Elf> move) {
        this.move = move;
    }
}
