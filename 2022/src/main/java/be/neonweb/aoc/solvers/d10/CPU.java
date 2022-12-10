package be.neonweb.aoc.solvers.d10;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Data
public class CPU {

    private int cycle;

    private Map<String, Integer> vars = new HashMap<>();

    private Queue<Operation> operations;

    public CPU() {
        this.vars.put("X", 1);
    }

    public void run(Map<Integer, Integer> cycles) {
        while (!operations.isEmpty()) {
            Operation operation = operations.poll();

            for(int c = 0; c < operation.cycle; c++) {
                cycle++;
                if (cycles.containsKey(cycle)) {
                    cycles.put(cycle, vars.get("X") * cycle);
                }
                operation.apply(c + 1, vars);
            }
        }
    }

    public void run(CRT crt) {
        while (!operations.isEmpty()) {
            Operation operation = operations.poll();

            for(int c = 0; c < operation.cycle; c++) {
                cycle++;
                crt.draw(cycle, vars.get("X"));
                operation.apply(c + 1, vars);
            }
        }
    }

}
