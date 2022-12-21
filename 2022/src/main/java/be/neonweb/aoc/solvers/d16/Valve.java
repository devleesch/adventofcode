package be.neonweb.aoc.solvers.d16;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Valve {

    private static final Pattern PATTERN = Pattern.compile("Valve ([A-Z]+) has flow rate=([0-9]+); tunnels{0,1} leads{0,1} to valves{0,1} (.*)");

    private String name;

    private int flowRate;

    @ToString.Exclude
    private Map<String, Valve> tunnels = new HashMap<>();

    public static Valve of(String s) {
        Matcher matcher = PATTERN.matcher(s);
        if (matcher.matches()) {
            Valve valve = new Valve();
            valve.setName(matcher.group(1));
            valve.setFlowRate(Integer.parseInt(matcher.group(2)));
            for (String name : matcher.group(3)
                                      .replaceAll(" ", "")
                                      .split(",")) {
                Valve tunnel = new Valve();
                tunnel.setName(name);
                valve.getTunnels()
                     .put(name, tunnel);
            }
            return valve;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return name.equals(valve.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
