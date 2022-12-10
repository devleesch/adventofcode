package be.neonweb.aoc.solvers.d10;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class CRT {

    private List<String> screen = new ArrayList<>();

    public CRT() {
        for (int i = 0; i < 6; i++) {
            screen.add("");
        }
    }

    public void draw(int cycle, int x) {
        int rowIndex = (cycle - 1) / 40;
        String row = screen.get(rowIndex);
        List<Integer> pixel = Arrays.asList(x - 1, x, x + 1);
        row += pixel.contains((cycle - 1) % 40) ? "#" : ".";
        screen.set(rowIndex, row);
    }

}
