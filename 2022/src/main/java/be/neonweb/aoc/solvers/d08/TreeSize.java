package be.neonweb.aoc.solvers.d08;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
public class TreeSize {

    private int x;
    private int y;

    private int value;

    public boolean visible(List<TreeSize> treeSizes) {
        return visible(treeSizes, (treeSize) -> treeSize.y == this.y && treeSize.x < this.x)
                || visible(treeSizes, (treeSize) -> treeSize.y == this.y && treeSize.x > this.x)
                || visible(treeSizes, (treeSize) -> treeSize.y < this.y && treeSize.x == this.x)
                || visible(treeSizes, (treeSize) -> treeSize.y > this.y && treeSize.x == this.x);
    }

    private boolean visible(List<TreeSize> treeSizes, Predicate<TreeSize> predicate) {
        return treeSizes.stream()
                        .filter(predicate)
                        .map(treeSize -> treeSize.value)
                        .max(Integer::compareTo)
                        .map(value -> value < this.value)
                        .orElse(true);
    }

    public int score(List<TreeSize> treeSizes) {
        return score(treeSizes, (treeSize) -> treeSize.y == this.y && treeSize.x < this.x, true)
                * score(treeSizes, (treeSize) -> treeSize.y == this.y && treeSize.x > this.x, false)
                * score(treeSizes, (treeSize) -> treeSize.y < this.y && treeSize.x == this.x, true)
                * score(treeSizes, (treeSize) -> treeSize.y > this.y && treeSize.x == this.x, false);
    }

    private int score(List<TreeSize> treeSizes, Predicate<TreeSize> predicate, boolean reverse) {
        List<TreeSize> collect = treeSizes.stream()
                                          .filter(predicate)
                                          .collect(Collectors.toList());
        if (reverse) {
            Collections.reverse(collect);
        }
        int score = 0;
        for (TreeSize treeSize : collect) {
            score++;
            if (treeSize.value >= this.value) {
                break;
            }
        }
        return score;
    }


}
