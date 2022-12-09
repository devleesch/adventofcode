package be.neonweb.aoc.solvers.d08;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class D08a extends Solver {

    List<TreeSize> treeSizes = new ArrayList<>();

    @Override
    public void config() throws Exception {
    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d08/input.txt"));

        treeSizes = IntStream.range(0, lines.size())
                             .mapToObj(y -> {
                                 String[] split = lines.get(y)
                                                       .split("");
                                 return IntStream.range(0, split.length)
                                                 .mapToObj(x -> {
                                                     TreeSize treeSize = new TreeSize();
                                                     treeSize.setX(x);
                                                     treeSize.setY(y);
                                                     treeSize.setValue(Integer.parseInt(split[x]));
                                                     return treeSize;
                                                 })
                                                 .toList();
                             })
                             .flatMap(Collection::stream)
                             .toList();
    }

    @Override
    public Object solve() throws Exception {
        return treeSizes.stream()
                        .filter(treeSize -> treeSize.visible(treeSizes))
                        .count();
    }
}
