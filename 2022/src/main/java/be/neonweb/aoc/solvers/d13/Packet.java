package be.neonweb.aoc.solvers.d13;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Slf4j
@Data
public class Packet implements Comparable<Packet> {

    private String value;

    @ToString.Exclude
    private List<Packet> subs = new ArrayList<>();

    public static Packet of(String s) {
        Packet packet = new Packet();

        if (s.isEmpty()) {
            return null;
        }

        if (s.contains("[")) {
            packet.value = s;
            Stack<Character> stack = new Stack<>();
            String inner = "";
            for (char c : packet.value.substring(1, s.length() - 1)
                                      .toCharArray()) {
                if (c == ',') {
                    if (stack.isEmpty()) {
                        addSub(Packet.of(inner), packet.subs);
                        inner = "";
                    } else {
                        inner += c;
                    }
                } else {
                    inner += c;
                    if (c == '[') {
                        stack.push(c);
                    } else if (c == ']') {
                        stack.pop();
                    }
                }
            }
            addSub(Packet.of(inner), packet.subs);
        } else if (s.contains(",")) {
            for (String split : s.split(",")) {
                addSub(Packet.of(split), packet.subs);
            }
        } else {
            packet.value = s;
        }

        return packet;
    }

    public int compareTo(Packet o) {
        log.debug("Compare " + this.getValue() + " vs " + o.getValue());

        if (this.isNumeric() && o.isNumeric()) {
            // both int
            return this.getNumericValue()
                       .compareTo(o.getNumericValue());
        } else if (this.isList() && o.isList()) {
            // both list
            for (int i = 0; i < Math.min(subs.size(), o.subs.size()); i++) {
                int c = subs.get(i)
                            .compareTo(o.subs.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return Integer.compare(subs.size(), o.subs.size());
        } else {
            // mixed
            if (this.isNumeric()) {
                return Packet.of("[" + this.value + "]")
                             .compareTo(o);
            } else {
                return this.compareTo(Packet.of("[" + o.value + "]"));
            }
        }
    }

    private boolean isNumeric() {
        return !value.contains("[");
    }

    private boolean isList() {
        return value.contains("[");
    }

    private Integer getNumericValue() {
        return Integer.parseInt(value);
    }

    private static void addSub(Packet packet, List<Packet> subs) {
        if (Objects.nonNull(packet)) {
            subs.add(packet);
        }
    }
}
