package be.neonweb.aoc.solvers;

import be.neonweb.aoc.utils.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.io.File;
import java.util.Objects;

@Slf4j
public abstract class Solver {

    protected CacheManager cacheManager;

    public abstract void parse() throws Exception;

    public abstract void solve() throws Exception;

    private void internalParse() throws Exception {
        Stopwatch stopwatch = Stopwatch.start();
        log.info("start parse()");
        parse();
        log.info("end parse() - " + stopwatch.stop());
    }

    private void internalSolve() throws Exception {
        Stopwatch stopwatch = Stopwatch.start();
        log.info("start solve()");
        solve();
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
