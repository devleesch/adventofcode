package be.neonweb.adventofcode.aoc2023.d02;

import lombok.Data;

@Data
public class CubeSet {

    private long red;
    private long green;
    private long blue;

    public void add(String color, int count) {
        switch (color) {
            case "red":
                red = count;
                break;
            case "green":
                green = count;
                break;
            case "blue":
                blue = count;
                break;
        }
    }

    public boolean possible() {
        return red <= 12 && green <= 13 && blue <= 14;
    }

}
