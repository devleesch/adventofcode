package be.neonweb.aoc2025.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tuple<LEFT, RIGHT> {

    private LEFT left;
    private RIGHT right;

}
