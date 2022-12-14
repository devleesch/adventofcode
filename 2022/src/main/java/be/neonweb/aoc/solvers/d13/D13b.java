package be.neonweb.aoc.solvers.d13;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class D13b extends D13a {

    @Override
    public Object solve() throws Exception {
        Pair divider = new Pair();
        Packet p2 = Packet.of("[[2]]");
        divider.setLeft(p2);
        Packet p6 = Packet.of("[[6]]");
        divider.setRight(p6);
        pairs.add(divider);

        List<Packet> packets = pairs.stream()
                                    .map(pair -> Arrays.asList(pair.getLeft(), pair.getRight()))
                                    .flatMap(Collection::stream)
                                    .sorted()
                                    .toList();
        return (packets.indexOf(p2) + 1) * (packets.indexOf(p6) + 1);
    }
}
