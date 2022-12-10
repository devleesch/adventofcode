package be.neonweb.aoc.solvers.d10;

import lombok.Data;

import java.util.Map;

@Data
public class Noop extends Operation {

    public Noop() {
        super(1);
    }

    @Override
    public void apply(int cycle, Map<String, Integer> vars) {}

}
