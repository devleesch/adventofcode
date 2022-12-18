package be.neonweb.aoc.solvers.d16;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Dijkstra;
import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class D16a extends Solver {

    Map<String, Valve> valves;
    Map<String, Integer> cache = new ConcurrentHashMap<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        valves = Files.readAllLines(Path.of("src/main/resources/d16/input.txt"))
                      .stream()
                      .map(Valve::of)
                      .collect(Collectors.toMap(Valve::getName, Function.identity()));

        valves.values()
              .forEach(valve -> valve.setTunnels(valve.getTunnels()
                                                      .values()
                                                      .stream()
                                                      .map(tunnel -> valves.get(tunnel.getName()))
                                                      .collect(Collectors.toMap(Valve::getName, Function.identity()))));
    }

    @Override
    public Object solve() throws Exception {
        List<String> bestValves = valves.values()
                                        .stream()
                                        .filter(valve -> valve.getFlowRate() > 0)
                                        .map(Valve::getName)
                                        .toList();

        return Generator.combination(bestValves)
                        .simple(5)
                        .stream()
                        .parallel()
                        .map(combination -> Generator.permutation(combination)
                                                     .simple())
                        .flatMap(IGenerator::stream)
                        .map(this::score)
                        .max(Integer::compareTo)
                        .orElseThrow();
    }

    int score(List<String> valves) {
        int tickLeft = 30;
        int score = 0;

        String previous = "AA";
        for (String valve : valves) {
            Set<Dijkstra.Node> nodes = getNodes();
            Dijkstra.Node start = getNode(nodes, previous);

            Dijkstra.Node end = getNode(nodes, valve);
            Integer pathSize = cache.computeIfAbsent(start.getSource() + "->" + end.getSource(), key -> Dijkstra.shortestPath(nodes, start, end)
                                                                                                                .size());

            // move
            tickLeft -= pathSize - 1;

            // open
            tickLeft--;

            if (tickLeft < 0) {
                return Integer.MIN_VALUE;
            }

            score += tickLeft * this.valves.get(valve)
                                           .getFlowRate();

            previous = valve;
        }
        return score;
    }

    private static Dijkstra.Node getNode(Set<Dijkstra.Node> nodes, String node) {
        return nodes.stream()
                    .filter(n -> n.getSource()
                                  .equals(node))
                    .findFirst()
                    .get();
    }

    Set<Dijkstra.Node> getNodes() {
        Set<Dijkstra.Node> nodes = valves.values()
                                         .stream()
                                         .map(valve -> Dijkstra.Node.builder()
                                                                    .source(valve.getName())
                                                                    .build())
                                         .collect(Collectors.toSet());

        nodes.forEach(node -> {
            Valve valve = valves.get(node.getSource());
            node.setNeighbors(valves.get(valve.getName())
                                    .getTunnels()
                                    .values()
                                    .stream()
                                    .map(tunnel -> nodes.stream()
                                                        .filter(n -> n.getSource()
                                                                      .equals(tunnel.getName()))
                                                        .findFirst()
                                                        .orElseThrow())
                                    .collect(Collectors.toList()));
        });

        return nodes;
    }
}
