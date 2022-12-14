package be.neonweb.aoc.solvers.d12;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.CacheUtil;
import be.neonweb.aoc.utils.Position;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.config.ResourceUnit;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class D12a extends Solver {

    Set<Square> squares = new LinkedHashSet<>();

    Cache<Square, Integer> startDistance;

    @Override
    public void config() throws Exception {
        startDistance = CacheUtil.getCache(
                cacheManager,
                "startDistance",
                Square.class,
                Integer.class,
                new CacheUtil.CacheSize<>(1500, EntryUnit.ENTRIES),
                new CacheUtil.CacheSize<>(128, MemoryUnit.MB),
                new CacheUtil.CacheSize<>(1024, MemoryUnit.MB));
    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d12/input.txt"));

        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y)
                                .toCharArray();
            for (int x = 0; x < chars.length; x++) {
                squares.add(new Square(new Position(x, y), chars[x]));
            }
        }
    }

    @Override
    public Object solve() throws Exception {
        Square start = squares.stream()
                              .filter(square -> square.getLetter()
                                                      .equals('S'))
                              .findFirst()
                              .get();

        Square end = squares.stream()
                            .filter(square -> square.getLetter()
                                                    .equals('E'))
                            .findFirst()
                            .get();

        return dijkstra(squares, start, end, startDistance);
    }

    private int dijkstra(Set<Square> squares, Square start, Square end, Cache<Square, Integer> cache) {
        squares.forEach(square -> square.setDistance(Integer.MAX_VALUE));
        start.setDistance(0);

        Set<Square> result = new LinkedHashSet<>();
        while (!result.contains(end)) {
            Square current = min(squares);

            if (cache.containsKey(current)) {
                return cache.get(current);
            }

            squares.remove(current);
            result.add(current);

            current.adjacents(squares)
                   .forEach(square -> square.updateDistance(current));
        }

        int count = 0;
        Square current = end;
        while (Objects.nonNull(current.getPrevious())) {
            log.info(current.getPosition()
                            .toString());
            count++;
            current = current.getPrevious();
        }

        cache.put(start, count);

        return count;
    }

    protected Square min(Set<Square> squares) {
        return squares.stream()
                      .min(Comparator.comparing(Square::getDistance))
                      .get();
    }
}
