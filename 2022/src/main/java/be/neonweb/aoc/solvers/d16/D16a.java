package be.neonweb.aoc.solvers.d16;

import be.neonweb.aoc.solvers.Solver;
import be.neonweb.aoc.utils.Dijkstra;
import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
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
        valves = Files.readAllLines(Path.of("src/main/resources/d16/test1.txt.txt"))
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
        List<Valve> scoreValves = valves.values()
                                        .stream()
                                        .filter(valve -> valve.getFlowRate() > 0)
                                        .sorted(Comparator.comparing(Valve::getName))
                                        .toList();

        return explore(new ArrayList<>(), scoreValves, 0);
    }

    int explore(List<Valve> path, List<Valve> valves, int max) {
        int score = score(path, 30);

        if (score >= 0 && !valves.isEmpty()) {
            int finalMax = max;
            max = valves.stream()
                        .parallel()
                        .map(valve -> {
                            ArrayList<Valve> newPath = new ArrayList<>(path);
                            newPath.add(valve);

                            ArrayList<Valve> newValves = new ArrayList<>(valves);
                            newValves.remove(valve);

                            return explore(newPath, newValves, finalMax);
                        })
                        .max(Integer::compareTo)
                        .get();
        }

        /*if (score > max) {
            String collect = path.stream()
                                 .map(Valve::getName)
                                 .collect(Collectors.joining("->"));
            log.info(score + " = " + collect);
        }*/

        return Math.max(score, max);
    }

    int score(List<Valve> valves, int tick) {
        int tickLeft = tick;
        int score = 0;

        Valve previous = this.valves.get("AA");
        for (Valve valve : valves) {
            Set<Dijkstra.Node> nodes = getNodes();
            Dijkstra.Node start = getNode(nodes, previous.getName());

            Dijkstra.Node end = getNode(nodes, valve.getName());
            Integer pathSize = cache.computeIfAbsent(start.getSource() + "->" + end.getSource(), key -> Dijkstra.shortestPath(nodes, start, end)
                                                                                                                .size());

            // move
            tickLeft -= pathSize - 1;

            // open
            tickLeft--;

            if (tickLeft < 0) {
                return Integer.MIN_VALUE;
            }

            score += tickLeft * this.valves.get(valve.getName())
                                           .getFlowRate();

            previous = valve;
        }

        return score;
    }

    Dijkstra.Node getNode(Set<Dijkstra.Node> nodes, String node) {
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
