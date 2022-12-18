package be.neonweb.aoc.utils;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Dijkstra {

    public static List<Node> shortestPath(Set<Node> nodes, Node start, Node end) {
        nodes.forEach(square -> square.setDistance(Integer.MAX_VALUE));
        start.setDistance(0);

        while (nodes.contains(end)) {
            Node current = min(nodes);

            nodes.remove(current);

            current.getNeighbors()
                   .forEach(node -> {
                       int distance = current.getDistance() + 1;
                       if (distance < node.getDistance()) {
                           node.setDistance(distance);
                           node.setPrevious(current);
                       }
                   });
        }

        List<Node> path = new ArrayList<>();
        path.add(end);
        Node current = end.getPrevious();
        while (Objects.nonNull(current)) {
            path.add(current);
            current = current.getPrevious();
        }

        Collections.reverse(path);

        return path;
    }

    private static Node min(Set<Node> nodes) {
        return nodes.stream()
                    .min(Comparator.comparing(Node::getDistance))
                    .orElseThrow();
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class Node {
        private String source;

        private int distance;

        @ToString.Exclude
        private List<Node> neighbors = new ArrayList<>();

        @ToString.Exclude
        private Node previous;
    }
}
