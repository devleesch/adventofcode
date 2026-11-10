package be.neonweb.aoc.d03;

import java.util.LinkedHashMap;
import java.util.Map;
import be.neonweb.aoc.AbstractDay;
import be.neonweb.aoc.model.Mover;
import be.neonweb.aoc.model.Point;
import be.neonweb.aoc.model.Mover.Direction;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class D03 extends AbstractDay {

    public static void main(String[] args) throws Exception {
        D03 d03 = new D03();
        d03.run();
    }

    @Override
    public Object part1() {
        int i = 1;
        int target = 277678;
        int last = Math.powExact(i, 2);
        while(last < target) {
            i += 2;
            last = Math.powExact(i, 2);
        }

        while (last > target) {
            last -= (i - 1);
        }
        last += (i - 1);

        int mid = last - i / 2;
        int delta = target - mid;
        log.info("i: {} - last: {} - mid: {} - delta: {}", i, last, mid, delta);

        return Math.abs(delta) + i / 2;
    }

    @Override
    public Object part2() {
        int target = 277678;

        Map<Point, Integer> values = new LinkedHashMap<>();
        int current = 1;
        values.put(new Point(0, 0), current);

        Mover mover = new Mover(new Point(0, 0), Direction.RIGHT);
        int i = 0;
        int step = 1;
        int remainingStep = step;
        int turn = 0;
        while (current < target) {
            mover.forward();
            current = mover.getPoint()
                .getAdjacent(values)
                .values()
                .stream()
                .mapToInt(it -> Integer.valueOf(it))
                .sum();
            values.put(mover.getPoint(), current);
            remainingStep--;

            if (remainingStep == 0) {
                turn++;
                if (turn % 2 == 0) {
                    step++;
                }
                mover.turnLeft();
                remainingStep = step;
            } 
        }

        return current;
    }
    
}
