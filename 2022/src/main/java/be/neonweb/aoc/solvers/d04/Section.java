package be.neonweb.aoc.solvers.d04;

import lombok.Data;

@Data
public class Section {

    private int start;
    private int end;

    public boolean contains(Section other) {
        return this.start <= other.start && this.end >= other.end;
    }

    public boolean overlap(Section other) {
        return contains(other)
            || this.start <= other.start && this.end >= other.start
            || this.start >= other.start && this.end <= other.end;
    }

    public static Section of(String value) {
        Section section = new Section();
        String[] split = value.split("-");
        section.start = Integer.parseInt(split[0]);
        section.end = Integer.parseInt(split[1]);
        return section;
    }

}
