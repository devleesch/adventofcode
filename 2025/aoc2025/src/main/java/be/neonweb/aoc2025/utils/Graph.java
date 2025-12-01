package be.neonweb.aoc2025.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Graph {

    private List<Node<?>> nodes;
    private List<Edge> edges;

    public Graph(List<Node<?>> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;

        edges.forEach(edge -> {
            edge.getA()
                .getAdjacents()
                .put(edge, edge.getB());
            edge.getB()
                .getAdjacents()
                .put(edge, edge.getA());
        });
    }

    @Getter
    @Setter
    public static class Node<T> {
        private T value;
        private int distance;
        private Map<Edge, Node<?>> adjacents = new HashMap<>();

        public Node(T value) {
            this.value = value;
        }
    }

    @Getter
    @Setter
    public static class Edge {
        private Node<?> a;
        private Node<?> b;
        private int weight;

        public Edge(Node<?> a, Node<?> b, int weight) {
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
    }

    public List<Node<?>> neighboors(Node<?> source) {
        return edges.stream()
                    .filter(edge -> edge.getA()
                                        .equals(source) || edge.getB()
                                                               .equals(source))
                    .map(edge -> edge.getA()
                                     .equals(source) ? edge.getB() : edge.getA())
                    .toList();
    }

    public static List<Graph.Node<?>> difference(List<Node<?>> a, List<Node<?>> b) {
        return a.stream()
                .filter(b::contains)
                .toList();
    }

}
