package be.neonweb.adventofcode.d03;

import be.neonweb.adventofcode.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class D03 extends Day {

    private static final Pattern PATTERN_MUL = Pattern.compile("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)");
    private static final Pattern PATTERN_SWITCH = Pattern.compile("(do\\(\\)|don't\\(\\))");

    @Override
    public String getDay() {
        return "03";
    }

    @Override
    public String part1(List<String> lines) {
        return lines.stream()
                    .map(PATTERN_MUL::matcher)
                    .flatMap(Matcher::results)
                    .map(matchResult -> Long.parseLong(matchResult.group(1)) * Long.parseLong(matchResult.group(2)))
                    .reduce(Long::sum)
                    .map(Object::toString)
                    .orElse(null);

    }

    @Override
    public String part2(List<String> lines) {
        List<String> instructions = new ArrayList<>();
        boolean enabled = true;
        for (String line : lines) {
            int start = 0;
            Matcher matcher = PATTERN_SWITCH.matcher(line);
            while (matcher.find()) {
                if (matcher.group(1)
                           .equals("do()")) {
                    if (!enabled) {
                        enabled = true;
                        //instructions.add(line.substring(start, matcher.start()));
                        start = matcher.start();
                    }
                } else {
                    if (enabled) {
                        enabled = false;
                        instructions.add(line.substring(start, matcher.start()));
                        start = matcher.start();
                    }
                }
            }

            instructions.add(line.substring(start));
        }

        return instructions.stream()
                           .filter(s -> !s.startsWith("don't()"))
                           .map(PATTERN_MUL::matcher)
                           .flatMap(Matcher::results)
                           .map(matchResult -> Long.parseLong(matchResult.group(1)) * Long.parseLong(matchResult.group(2)))
                           .reduce(Long::sum)
                           .map(Object::toString)
                           .orElse(null);

        // 77877805 - too high
    }
}
