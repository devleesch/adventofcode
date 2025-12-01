package be.neonweb.aoc2025.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Dijkstra {

    private Graph graph;
    private Graph.Node<?> start;
    private Graph.Node<?> end;
    private Map<Graph.Node<?>, Graph.Node<?>> previous = new HashMap<>();

    public Dijkstra(Graph graph, Graph.Node<?> start, Graph.Node<?> end) {
        this.graph = graph;
        this.start = start;
        this.end = end;

        graph.getNodes()
             .forEach(node -> node.setDistance(Integer.MAX_VALUE));
        start.setDistance(0);
    }

    public List<Graph.Node<?>> path() {
        ArrayList<Graph.Node<?>> sub = new ArrayList<>(graph.getNodes());
        while (!sub.isEmpty()) {
            Graph.Node<?> min = sub.stream()
                                   .min(Comparator.comparing(Graph.Node::getDistance))
                                   .orElseThrow();
            sub.remove(min);
            min.getAdjacents()
               .forEach((key, value) -> {
                   int newDistance = min.getDistance() + key
                           .getWeight();
                   if (newDistance < value
                           .getDistance()) {
                       value.setDistance(newDistance);
                       previous.put(value, min);
                   }
               });
        }

        List<Graph.Node<?>> path = new ArrayList<>();
        Graph.Node<?> current = end;
        while (!current.equals(start)) {
            path.add(current);
            current = previous.get(current);
        }
        path.add(current);
        return path.reversed();
    }

}
