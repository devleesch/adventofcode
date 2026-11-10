package be.neonweb.aoc2016.d03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.neonweb.aoc2016.AbstractDay;

public class D03 extends AbstractDay {

    @Override
    public Object part1() {
        List<Triangle> triangles = new ArrayList<>();
        for (String l : lines) {
            List<Integer> values = Arrays.stream(l.split(" "))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .toList();

            triangles.add(new Triangle(values));
        }

        return triangles.stream().filter(t -> t.isValid()).count();
    }

    @Override
    public Object part2() {
        List<Triangle> triangles = new ArrayList<>();

        List<List<Integer>> allValues = new ArrayList<>();
        for (String l : lines) {
            List<Integer> values = Arrays.stream(l.split(" "))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .toList();

            allValues.add(values);
        }

        for (int i = 0; i < allValues.size(); i+=3) {
            List<Integer> values1 = allValues.get(i);
            List<Integer> values2 = allValues.get(i+1);
            List<Integer> values3 = allValues.get(i+2);

            for (int j=0; j < values1.size(); j++) {
                triangles.add(new Triangle(List.of(values1.get(j), values2.get(j), values3.get(j))));
            }
        }

        return triangles.stream().filter(t -> t.isValid()).count();
    }

    private static class Triangle {
        private List<Integer> sides;

        public Triangle(List<Integer> sides) {
            this.sides = sides;
        }

        public boolean isValid() {
            return sides.get(0) + sides.get(1) > sides.get(2)
                && sides.get(1) + sides.get(2) > sides.get(0)
                && sides.get(2) + sides.get(0) > sides.get(1);
        }
    }

}
