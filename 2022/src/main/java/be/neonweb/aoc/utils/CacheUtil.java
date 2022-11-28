package be.neonweb.aoc.utils;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.ResourceUnit;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

public class CacheUtil {

    public static <K, V> Cache<K, V> getCache(CacheManager cacheManager, String name, Class<K> key, Class<V> value, CacheSize<? extends ResourceUnit> heap, CacheSize<MemoryUnit> offHeap, CacheSize<MemoryUnit> disk) {
        CacheConfigurationBuilder<K, V> cacheConfigurationBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                key,
                value,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                    .heap(heap.size, heap.unit)
                                    .offheap(offHeap.size, offHeap.unit)
                                    .disk(disk.size, disk.unit));
        return cacheManager.createCache(name, cacheConfigurationBuilder);
    }

    public static class CacheSize<T extends ResourceUnit> {
        private final long size;
        private final T unit;

        public CacheSize(long size, T unit) {
            this.size = size;
            this.unit = unit;
        }
    }

}
