package be.neonweb.aoc.solvers.d23;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class D23b extends D23a {

    @Override
    public Object solve() throws Exception {
        int round = 0;
        while (true) {
            round++;
            log.info("Round " + round);
            List<Elf> moves = new ArrayList<>(elves.values()
                                                   .stream()
                                                   .filter(elf -> elf.hasNeighboors(elves.values()))
                                                   .map(elf -> elf.propose(queue, elves.values()))
                                                   .toList());

            if (moves.isEmpty()) {
                break;
            }

            List<Elf> toRemove = moves.stream()
                                      .flatMap(move -> moves.stream()
                                                            .filter(other -> move.getIndex() != other.getIndex())
                                                            .filter(other -> move.getX() == other.getX() && move.getY() == other.getY()))
                                      .toList();
            moves.removeAll(toRemove);

            moves.forEach(move -> elves.put(move.getIndex(), move));

            queue.add(queue.poll());
        }
        return round;
    }
}
