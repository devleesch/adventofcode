package be.neonweb.aoc.d01;

import java.util.Arrays;
import java.util.stream.Gatherers;

import be.neonweb.aoc.AbstractDay;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class D01 extends AbstractDay {

    public static void main(String[] args) throws Exception {
        D01 d01 = new D01();
        d01.run();
    }

    @Override
    public Object part1() {
        String line = lines.get(0) + lines.get(0).charAt(0);
        return Arrays.stream(line.split(""))
            .gather(Gatherers.windowSliding(2))
            .mapToInt(t -> {
                if (t.getFirst().equals(t.getLast())) {
                    return Integer.valueOf(t.getFirst());
                }
                return 0;
            })
            .sum();
    }

    @Override
    public Object part2() {
        String line = lines.get(0);
        String[] split = line.split("");

        int step = split.length / 2;
        int captcha = 0;
        for (int i = 0; i < split.length; i++) {
            Integer a = Integer.valueOf(split[i]);
            Integer b = Integer.valueOf(split[(i + step) % split.length]);

            if (a == b) {
                captcha += a;
            }
        }

        return captcha;
    }

}
