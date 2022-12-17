package be.neonweb.aoc.solvers.d15;

import be.neonweb.aoc.utils.Position;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Slf4j
public class D15b extends D15a {

    @Override
    public Object solve() throws Exception {
        //int size = 20;
        int size = 4000000;

        Position beacon = new Position(-1, -1);
        for (int i = 0; i < size; i++) {
            List<Interval> intervals = new ArrayList<>();
            for (Sensor sensor : sensors) {
                int distanceToTarget = Math.abs(sensor.getPosition()
                                                      .getY() - i);
                if (sensor.radius() >= distanceToTarget) {
                    Interval interval = Interval.builder()
                                                .start(Math.max(0, sensor.getPosition()
                                                                         .getX() - sensor.radius() + distanceToTarget))
                                                .end(Math.min(sensor.getPosition()
                                                                    .getX() + sensor.radius() - distanceToTarget, size))
                                                .build();
                    intervals.add(interval);
                }
            }

            intervals = Interval.reduce(intervals);
            if (intervals.size() > 1) {
                beacon.setX(intervals.get(0)
                                     .getEnd() + 1);
                beacon.setY(i);
            }
        }

        return new BigDecimal(beacon.getX()).multiply(new BigDecimal(size))
                                            .add(new BigDecimal(beacon.getY()));
    }
}
