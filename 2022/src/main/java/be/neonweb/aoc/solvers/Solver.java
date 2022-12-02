package be.neonweb.aoc.solvers;

import be.neonweb.aoc.utils.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

import java.util.Objects;

@Slf4j
public abstract class Solver {

    protected boolean skipPart1 = false;
    protected boolean skipPart2 = false;
    protected CacheManager cacheManager;

    public abstract void config() throws Exception;

    public abstract void parse() throws Exception;

    public abstract Object part1() throws Exception;

    public abstract Object part2() throws Exception;

    private void internalParse() throws Exception {
        Stopwatch stopwatch = Stopwatch.start();
        log.info("start parse()");
        parse();
        log.info("end parse() - " + stopwatch.stop());
    }

    private void internalSolve() throws Exception {
        Stopwatch stopwatch = Stopwatch.start();
        log.info("start solve()");
        if (!skipPart1) {
            log.info("part1: " + part1());
        }
        if (!skipPart2) {
            log.info("part2: " + part2());
        }
        log.info("end solve() - " + stopwatch.stop());
    }

    private void initCache() {
        this.cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                                               .with(CacheManagerBuilder.persistence("cache"))
                                               .build();
        this.cacheManager.init();
    }

    public void run() {
        try {
            log.debug(this.getClass()
                          .getCanonicalName());
            initCache();

            config();

            internalParse();
            internalSolve();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (Objects.nonNull(this.cacheManager)) {
                this.cacheManager.close();
            }
        }
    }
}
