package be.neonweb.aoc.solvers.d11;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {

    private BigDecimal worryLevel;

    public Item(BigDecimal worryLevel) {
        this.worryLevel = worryLevel;
    }
}
