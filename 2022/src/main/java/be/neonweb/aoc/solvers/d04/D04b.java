package be.neonweb.aoc.solvers.d04;

public class D04b extends D04a {

    @Override
    public Object solve() throws Exception {
        return pairs.stream()
                    .filter(Pair::overlap)
                    .count();

    }
}
