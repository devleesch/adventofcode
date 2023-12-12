package be.neonweb.adventofcode.aoc2023.d07;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndexedHand {

    private Integer index;
    private Hand value;

    public static IndexedHand of(Integer index, Hand hand) {
        return IndexedHand.builder()
                          .index(index)
                          .value(hand)
                          .build();
    }
}
