package be.neonweb.aoc.solvers.d08;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class D08b extends D08a {

    @Override
    public Object solve() throws Exception {
        return treeSizes.stream()
                        .map(treeSize -> treeSize.score(treeSizes))
                        .max(Integer::compareTo)
                        .orElseThrow();
    }

}
