package be.neonweb.aoc.solvers.d14;

import be.neonweb.aoc.utils.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class D14b extends D14a {

    @Override
    public Object solve() throws Exception {
        int maxY = map.stream()
                      .map(item -> item.getPosition()
                                       .getY())
                      .max(Integer::compareTo)
                      .get() + 1;

        int count = 0;
        int size;
        do {
            size = map.size();
            count++;
            Sand sand = new Sand(new Position(500, 0));
            sand.fall2(map, maxY);
            map.add(sand);

            if (count % 1000 == 0) {
                log.info("Map after " + count + " count(s)");
                display();
                log.info("--------------------------------");
            }
        } while (size < map.size());

        display();

        return count - 1; // minus the one that falls
    }
}
