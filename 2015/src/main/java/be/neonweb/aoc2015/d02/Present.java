package be.neonweb.aoc2015.d02;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@AllArgsConstructor
@ToString
public class Present {

    private static final Pattern PATTERN = Pattern.compile("([0-9]+)x([0-9]+)x([0-9]+)");

    private int length;
    private int width;
    private int height;

    public static Present of(String line) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.matches()) {
            int length = Integer.parseInt(matcher.group(1));
            int width = Integer.parseInt(matcher.group(2));
            int height = Integer.parseInt(matcher.group(3));
            return new Present(length, width, height);
        }
        throw new IllegalStateException();
    }

    public int wrapping() {
        int lw = length * width;
        int wh = width * height;
        int hl = height * length;
        int min = Stream.of(lw, wh, hl)
                        .min(Integer::compare)
                        .orElseThrow();

        return 2 * lw + 2 * wh + 2 * hl + min;
    }

    public int ribbon() {
        List<Integer> sides = new ArrayList<>();
        sides.add(length);
        sides.add(width);
        sides.add(height);

        sides.sort(Integer::compareTo);

        return 2 * sides.getFirst() + 2 * sides.get(1) + length * width * height;
    }

}
