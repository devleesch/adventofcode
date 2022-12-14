package be.neonweb.aoc.solvers.d13;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Pair {

    private Packet left;

    private Packet right;

    public int check() {
        int check = left.compareTo(right);
        log.info((check == -1) + " = " + left + " - " + right);
        return check;
    }

}
