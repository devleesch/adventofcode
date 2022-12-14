package be.neonweb.aoc.solvers.d12;

import be.neonweb.aoc.utils.Position;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class D12b extends D12a {

    @Override
    public Object solve() throws Exception {
        Set<Square> starts = squares.stream()
                                    .filter(square -> square.getLetter()
                                                            .equals('S') || square.getLetter()
                                                                                  .equals('a'))
                                    .collect(Collectors.toSet());

        Square end = squares.stream()
                            .filter(square -> square.getLetter()
                                                    .equals('E'))
                            .findFirst()
                            .get();

        return starts.parallelStream()
                     .map(start -> {
                         Set<Square> collect = squares.stream()
                                                      .map(Square::copy)
                                                      .collect(Collectors.toSet());
                         return dijkstra(collect, start.getPosition(), end.getPosition(), startDistance);
                     })
                     .min(Integer::compareTo)
                     .get();
    }

    private int dijkstra(Set<Square> squares, Position startPosition, Position endPosition, Cache<Square, Integer> cache) {
        Square start = squares.stream()
                              .filter(square -> square.getPosition()
                                                      .equals(startPosition))
                              .findFirst()
                              .get();

        Square end = squares.stream()
                            .filter(square -> square.getPosition()
                                                    .equals(endPosition))
                            .findFirst()
                            .get();

        squares.forEach(square -> square.setDistance(Integer.MAX_VALUE));
        start.setDistance(0);

        int count = 0;
        Set<Square> result = new LinkedHashSet<>();
        while (squares.contains(end)) {
            Square current = min(squares);

            /*if (cache.containsKey(current)) {
                count = cache.get(current);
                break;
            }*/

            squares.remove(current);

            current.adjacents(squares)
                   .forEach(square -> square.updateDistance(current));
        }

        Square current = end;
        while (Objects.nonNull(current.getPrevious())) {
            count++;
            current = current.getPrevious();
        }

        cache.put(start, count);
        log.info(start + " -> " + count);

        return count;
    }
}
