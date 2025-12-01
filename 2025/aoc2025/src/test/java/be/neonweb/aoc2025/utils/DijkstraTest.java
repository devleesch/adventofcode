package be.neonweb.aoc2025.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DijkstraTest {

    @Test
    public void path() {
        Graph.Node<String> a = new Graph.Node<>("A");
        Graph.Node<String> b = new Graph.Node<>("B");
        Graph.Node<String> c = new Graph.Node<>("C");
        Graph.Node<String> d = new Graph.Node<>("D");
        Graph.Node<String> e = new Graph.Node<>("E");
        Graph.Node<String> f = new Graph.Node<>("F");
        Graph.Node<String> g = new Graph.Node<>("G");
        Graph.Node<String> h = new Graph.Node<>("H");
        Graph.Node<String> i = new Graph.Node<>("I");
        Graph.Node<String> j = new Graph.Node<>("J");

        Graph.Edge ab = new Graph.Edge(a, b, 85);
        Graph.Edge ac = new Graph.Edge(a, c, 217);
        Graph.Edge ae = new Graph.Edge(a, e, 173);
        Graph.Edge bf = new Graph.Edge(b, f, 80);
        Graph.Edge cg = new Graph.Edge(c, g, 186);
        Graph.Edge ch = new Graph.Edge(c, h, 103);
        Graph.Edge hd = new Graph.Edge(h, d, 183);
        Graph.Edge fi = new Graph.Edge(f, i, 250);
        Graph.Edge ij = new Graph.Edge(i, j, 84);
        Graph.Edge hj = new Graph.Edge(h, j, 167);
        Graph.Edge ej = new Graph.Edge(e, j, 502);

        Graph graph = new Graph(
                List.of(a, b, c, d, e, f, g, h, i, j),
                List.of(ab, ac, ae, bf, cg, ch, hd, fi, ij, hj, ej)
        );

        Dijkstra dijkstra = new Dijkstra(graph, a, j);
        List<Graph.Node<?>> path = dijkstra.path();

        List<Graph.Node<String>> expectedPath = List.of(a, c, h, j);

        Assertions.assertEquals(expectedPath, path);
    }

}
