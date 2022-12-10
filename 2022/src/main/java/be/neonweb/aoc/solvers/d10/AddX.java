package be.neonweb.aoc.solvers.d10;

import lombok.Data;

import java.util.Map;

@Data
public class AddX extends Operation {

    private int value;

    public AddX(int value) {
        super(2);
        this.value = value;
    }

    @Override
    public void apply(int cycle, Map<String, Integer> vars) {
        if (cycle == this.cycle) {
            vars.put("X", vars.get("X") + value);
        }
    }
}
