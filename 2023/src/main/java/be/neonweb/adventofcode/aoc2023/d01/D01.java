package be.neonweb.adventofcode.aoc2023.aoc01;

import be.neonweb.adventofcode.aoc2023.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class D01 extends Solver {

    private static final Pattern PATTERN = Pattern.compile("one|two|three|four|five|six|seven|eight|nine");
    private static final Map<String, String> CONVERTER = new HashMap<>();

    static {
        CONVERTER.put("one", "1");
        CONVERTER.put("two", "2");
        CONVERTER.put("three", "3");
        CONVERTER.put("four", "4");
        CONVERTER.put("five", "5");
        CONVERTER.put("six", "6");
        CONVERTER.put("seven", "7");
        CONVERTER.put("eight", "8");
        CONVERTER.put("nine", "9");
    }

    public D01(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        return inputLines.stream()
                         .map(this::extract)
                         .reduce(0, Integer::sum);
    }

    @Override
    public Object part2(List<String> inputLines) {
        return inputLines.stream()
                         .map(this::convert)
                         .map(this::extract)
                         .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        D01 d01 = new D01("src/main/resources/01/input.txt");
        Solver.run(d01);
    }

    private Integer extract(String line) {
        char first = '*';
        char last = '*';
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                if (first == '*') {
                    first = c;
                }
                last = c;
            }
        }

        return Character.getNumericValue(first) * 10 + Character.getNumericValue(last);
    }

    private String convert(String line) {
        line = line.replaceAll("oneight", "oneeight")
                   .replaceAll("threeight", "threeeight")
                   .replaceAll("fiveight", "fiveeight")
                   .replaceAll("nineight", "nineeight")
                   .replaceAll("twone", "twoone")
                   .replaceAll("sevenine", "sevennine")
                   .replaceAll("eightwo", "eighttwo");
        Matcher matcher = PATTERN.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            line = line.replaceFirst(group, CONVERTER.get(group));
        }
        return line;
    }

}
