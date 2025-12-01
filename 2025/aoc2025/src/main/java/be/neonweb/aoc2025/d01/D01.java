package be.neonweb.aoc2025.d01;

import be.neonweb.aoc2025.Solver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class D01 extends Solver {

    private List<Rotation> rotations;

    @Override
    public void parseFile() throws Exception {
        readFile(null);
        rotations = lines.stream()
                         .filter(l -> !l.isEmpty())
                         .map(Rotation::of)
                         .toList();
    }

    @Override
    public Object part1() {
        Lock lock = new Lock(50);
        List<Lock> locks = new ArrayList<>();
        locks.add(lock);

        for (Rotation rotation : rotations) {
            lock = lock.apply(rotation);
            locks.add(lock);
        }

        return locks.stream()
                    .filter(it -> it.getPosition() == 0)
                    .count();
    }

    @Override
    public Object part2() {
        Lock lock = new Lock(50);
        List<Lock> locks = new ArrayList<>();
        locks.add(lock);

        for (Rotation rotation : rotations) {
            int unaryDistance = rotation.getDistance() / Math.abs(rotation.getDistance());
            Rotation unaryRotation = new Rotation(unaryDistance);
            for (int i = 0; i < Math.abs(rotation.getDistance()); i++) {
                lock = lock.apply(unaryRotation);
                locks.add(lock);
            }
        }

        return locks.stream()
                    .filter(it -> it.getPosition() == 0)
                    .count();
    }

    @Getter
    @Setter
    public static class Lock {
        private int position;

        public Lock(int position) {
            this.position = position;
        }

        public Lock apply(Rotation rotation) {
            int position = this.position + rotation.getDistance();
            while (position < 0) {
                position += 100;
            }

            while (position > 99) {
                position -= 100;
            }

            return new Lock(position);
        }
    }

    @Getter
    @Setter
    public static class Rotation {
        private int distance;

        public Rotation(int distance) {
            this.distance = distance;
        }

        public static Rotation of(String line) {
            int factor = line.charAt(0) == 'L' ? -1 : 1;
            int distance = Integer.parseInt(line.substring(1));
            return new Rotation(factor * distance);
        }
    }
}
