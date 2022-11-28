package be.neonweb.aoc.solvers.d01;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

@Slf4j
public class D01 extends Solver {

    private Cache<Long, String> testCache;

    @Override
    public void parse() throws Exception {
        testCache = CacheUtil.getCache(
                cacheManager,
                "test",
                Long.class,
                String.class,
                new CacheUtil.CacheSize<>(100, EntryUnit.ENTRIES),
                new CacheUtil.CacheSize<>(2, MemoryUnit.GB),
                new CacheUtil.CacheSize<>(16, MemoryUnit.GB)
        );
    }

    @Override
    public void solve() throws Exception {
        testCache.put(1L, "VALUE IN CACHE");
        log.info(testCache.get(1L));
        log.info(testCache.get(1L));
    }
}
