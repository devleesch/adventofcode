package be.neonweb.adventofcode.d12;

import be.neonweb.adventofcode.Day;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class D12 extends Day {

    private Set<Region> regions;
    private Set<Plot> plots;

    @Override
    public String part1(List<String> lines) {
        plots = IntStream.range(0, lines.size())
                         .mapToObj(y -> IntStream.range(0, lines.get(y)
                                                                .length())
                                                 .mapToObj(x -> Plot.from(x, y, String.valueOf(lines.get(y)
                                                                                                    .charAt(x)))))
                         .flatMap(plotStream -> plotStream)
                         .collect(Collectors.toSet());

        regions = new HashSet<>();
        for (Plot plot : plots) {

            boolean associated = regions.stream()
                                        .anyMatch(region -> region.plots.contains(plot));

            if (!associated) {
                Region region = new Region();
                region.plant = plot.plant;
                getRegion(plot, region.plots, plots);
                regions.add(region);
            }
        }

        return regions.parallelStream()
                      .map(region -> region.price(plots))
                      .reduce(0L, Long::sum)
                      .toString();

        // 13359410 - too high
        // 568179 - too low
    }

    public void getRegion(Plot plot, Set<Plot> region, Set<Plot> plots) {
        region.add(plot);
        for (Plot adjacent : plot.adjacents(plots)
                                 .flatMap(Optional::stream)
                                 .filter(p -> p.plant.equals(plot.plant))
                                 .filter(p -> !region.contains(p))
                                 .collect(Collectors.toSet())) {
            getRegion(adjacent, region, plots);
        }
    }

    @Override
    public String part2(List<String> lines) {
        return regions.stream()
                      .map(region -> region.price2(plots))
                      .reduce(0, Integer::sum)
                      .toString();
    }

    public static Integer lines(List<Integer> values) {
        if (values.isEmpty()) {
            return 0;
        }

        int gaps = 0;
        Integer previous = null;
        for (Integer value : values) {
            if (Objects.nonNull(previous) && value - previous > 1) {
                gaps++;
            }
            previous = value;
        }
        return gaps + 1;
    }

    @ToString
    public static class Plot {
        public int x;
        public int y;
        public String plant;

        public static Plot from(int x, int y, String plant) {
            Plot plot = new Plot();
            plot.x = x;
            plot.y = y;
            plot.plant = plant;
            return plot;
        }

        public Optional<Plot> get(int x, int y, Set<Plot> plots) {
            return plots.stream()
                        .filter(p -> p.x == x)
                        .filter(p -> p.y == y)
                        .findFirst();
        }

        public Optional<Plot> left(Set<Plot> plots) {
            return get(this.x - 1, this.y, plots);
        }

        public Optional<Plot> right(Set<Plot> plots) {
            return get(this.x + 1, this.y, plots);
        }

        public Optional<Plot> up(Set<Plot> plots) {
            return get(this.x, this.y - 1, plots);
        }

        public Optional<Plot> down(Set<Plot> plots) {
            return get(this.x, this.y + 1, plots);
        }

        public Stream<Optional<Plot>> adjacents(Set<Plot> plots) {
            return Stream.of(left(plots), right(plots), up(plots), down(plots));
        }
    }

    @ToString
    public static class Region {
        public String plant;
        public Set<Plot> plots = new HashSet<>();

        public Long perimeter(Set<Plot> plots) {
            return this.plots.stream()
                             .map(plot -> plot.adjacents(plots)
                                              .filter(optionalAdjacent -> optionalAdjacent.isEmpty() || !optionalAdjacent.get().plant.equals(plant))
                                              .count())
                             .reduce(0L, Long::sum);
        }

        public Integer surface() {
            return plots.size();
        }

        public Integer sides(Set<Plot> plots) {
            log.debug("region = {}", this);
            Integer yLine = this.plots.stream()
                                      .collect(Collectors.groupingBy(plot -> plot.y))
                                      .values()
                                      .stream()
                                      .map(yPlots -> {
                                          int upLine = lines(yPlots.stream()
                                                                   .map(yPlot -> yPlot.up(plots)
                                                                                      .orElseGet(() -> {
                                                                                          Plot plot = new Plot();
                                                                                          plot.x = yPlot.x;
                                                                                          plot.y = yPlot.y - 1;
                                                                                          plot.plant = "#";
                                                                                          return plot;
                                                                                      }))
                                                  .filter(yPlot -> !yPlot.plant.equals(this.plant))
                                                  .map(plot -> plot.x)
                                                  .sorted(Integer::compare)
                                                  .toList());
                                          int downLine = lines(yPlots.stream()
                                                                     .map(yPlot -> yPlot.down(plots)
                                                                                        .orElseGet(() -> {
                                                                                            Plot plot = new Plot();
                                                                                            plot.x = yPlot.x;
                                                                                            plot.y = yPlot.y + 1;
                                                                                            plot.plant = "#";
                                                                                            return plot;
                                                                                        }))
                                                                     .filter(yPlot -> !yPlot.plant.equals(this.plant))
                                                                     .map(plot -> plot.x)
                                                                     .sorted(Integer::compare)
                                                                     .toList());
                                          log.debug("upLine = {}, downLine = {}", upLine, downLine);
                                          return upLine + downLine;
                                      })
                                      .reduce(0, Integer::sum);

            Integer xLine = this.plots.stream()
                                      .collect(Collectors.groupingBy(plot -> plot.x))
                                      .values()
                                      .stream()
                                      .map(xPlots -> {
                                          int leftLine = lines(xPlots.stream()
                                                                     .map(xPlot -> xPlot.left(plots)
                                                                                        .orElseGet(() -> {
                                                                                            Plot plot = new Plot();
                                                                                            plot.x = xPlot.x - 1;
                                                                                            plot.y = xPlot.y;
                                                                                            plot.plant = "#";
                                                                                            return plot;
                                                                                        }))
                                                                     .filter(xPlot -> !xPlot.plant.equals(this.plant))
                                                                     .map(plot -> plot.y)
                                                                     .sorted(Integer::compare)
                                                                     .toList());
                                          int rightLine = lines(xPlots.stream()
                                                                      .map(xPlot -> xPlot.right(plots)
                                                                                         .orElseGet(() -> {
                                                                                             Plot plot = new Plot();
                                                                                             plot.x = xPlot.x + 1;
                                                                                             plot.y = xPlot.y;
                                                                                             plot.plant = "#";
                                                                                             return plot;
                                                                                         }))
                                                                      .filter(xPlot -> !xPlot.plant.equals(this.plant))
                                                                      .map(plot -> plot.y)
                                                                      .sorted(Integer::compare)
                                                                      .toList());
                                          log.debug("leftLine = {}, rightLine = {}", leftLine, rightLine);
                                          return leftLine + rightLine;
                                      })
                                      .reduce(0, Integer::sum);

            log.debug("yLine = {}, xLine = {}", yLine, xLine);
            return yLine + xLine;
        }

        public Long price(Set<Plot> plots) {
            return perimeter(plots) * surface();
        }

        public Integer price2(Set<Plot> plots) {
            return sides(plots) * surface();
        }
    }
}
