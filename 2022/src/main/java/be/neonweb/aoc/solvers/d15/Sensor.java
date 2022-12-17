package be.neonweb.aoc.solvers.d15;

import be.neonweb.aoc.utils.Position;
import lombok.Builder;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
public class Sensor {

    private static final Pattern PATTERN = Pattern.compile("Sensor at x=([\\-0-9]+), y=([\\-0-9]+): closest beacon is at x=([\\-0-9]+), y=([\\-0-9]+)");

    private Position position;

    private Beacon beacon;

    public static Sensor of(String s) {
        Matcher matcher = PATTERN.matcher(s);
        if (matcher.matches()) {
            return Sensor.builder()
                         .position(Position.builder()
                                           .x(Integer.parseInt(matcher.group(1)))
                                           .y(Integer.parseInt(matcher.group(2)))
                                           .build())
                         .beacon(Beacon.builder()
                                       .position(Position.builder()
                                                         .x(Integer.parseInt(matcher.group(3)))
                                                         .y(Integer.parseInt(matcher.group(4)))
                                                         .build())
                                       .build())
                         .build();
        }
        return null;
    }

    public int radius() {
        return Math.abs(beacon.getPosition().getX() - position.getX())
                + Math.abs(beacon.getPosition().getY() - position.getY());
    }

}
