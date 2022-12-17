package be.neonweb.aoc.solvers.d15;

import lombok.Data;

@Data
public class Borne {

    private Integer index;

    private String type;

    public Borne(Integer index, String type) {
        this.index = index;
        this.type = type;
    }
}
