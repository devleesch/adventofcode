package be.neonweb.aoc.solvers.d15;

import be.neonweb.aoc.solvers.Solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class D15a extends Solver {

    List<Sensor> sensors = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        sensors = Files.readAllLines(Path.of("src/main/resources/d15/input.txt"))
                       .stream()
                       .map(Sensor::of)
                       .collect(Collectors.toList());
    }

    @Override
    public Object solve() throws Exception {
        //int targetY = 10;
        int targetY = 2000000;

        List<Interval> intervals = new ArrayList<>();
        for (Sensor sensor : sensors) {
            int distanceToTarget = Math.abs(sensor.getPosition()
                                                  .getY() - targetY);
            if (sensor.radius() >= distanceToTarget) {
                Interval interval = Interval.builder()
                                            .start(sensor.getPosition()
                                                         .getX() - sensor.radius() + distanceToTarget)
                                            .end((sensor.getPosition()
                                                        .getX() + sensor.radius() - distanceToTarget))
                                            .build();
                intervals.add(interval);
            }
        }

        intervals = Interval.reduce(intervals);


        return intervals.stream()
                        .map(Interval::size)
                        .reduce(Integer::sum)
                        .get() - sensors.stream()
                                        .map(sensor -> sensor.getBeacon()
                                                             .getPosition())
                                        .filter(position -> position.getY() == targetY)
                                        .distinct()
                                        .count();
    }
}
