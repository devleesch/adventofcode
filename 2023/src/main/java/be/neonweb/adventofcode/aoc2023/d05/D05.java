package be.neonweb.adventofcode.aoc2023.d05;

import be.neonweb.adventofcode.aoc2023.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class D05 extends Solver {

    private static final Pattern PATTERN_SEEDS = Pattern.compile("seeds: ([0-9 ]+)");
    private static final Pattern PATTERN_MAPPER = Pattern.compile("(.*)-to-(.*) map:");
    private static final Pattern PATTERN_RANGE = Pattern.compile("([0-9]+) ([0-9]+) ([0-9]+)");

    private List<State> states;
    private List<Mapper> mappers;

    public D05(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);

        mappers.forEach(mapper -> states.forEach(state -> state.apply(mapper)));

        return states.stream()
                     .map(State::getValue)
                     .min(Long::compare)
                     .orElseThrow();
    }

    @Override
    public Object part2(List<String> inputLines) {
        parse(inputLines);

        Long min = Long.MAX_VALUE;
        for (int i = 0; i < states.size(); i += 2) {
            log.info("i: " + i);
            State start = states.get(i);
            State length = states.get(i + 1);

            for (long j = 0; j < length.getValue(); j++) {
                if (j % 100000 == 0) {
                    log.info(i + " -> " + j + " / " + length.getValue() + "("+ (double) j / (double) length.getValue() * 100 +"%)");
                }
                State state = State.builder()
                                   .name("seed")
                                   .value(start.getValue() + j)
                                   .build();

                for (Mapper mapper : mappers) {
                    state.apply(mapper);
                }

                if (state.getValue() < min) {
                    min = state.getValue();
                }
            }
        }

        return min;
    }

    private void parse(List<String> inputLines) {
        Matcher seedsMatcher = PATTERN_SEEDS.matcher(inputLines.get(0));
        if (seedsMatcher.matches()) {
            states = Arrays.stream(seedsMatcher.group(1)
                                               .split(" "))
                           .map(s -> State.builder()
                                          .name("seed")
                                          .value(Long.parseLong(s))
                                          .build())
                           .toList();
        }

        mappers = new ArrayList<>();
        Mapper mapper = new Mapper();
        for (int i = 1; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            if (line.isEmpty()) {
                mapper = new Mapper();
                mappers.add(mapper);
            } else {
                Matcher mapperMatcher = PATTERN_MAPPER.matcher(line);
                if (mapperMatcher.matches()) {
                    mapper.setFrom(mapperMatcher.group(1));
                    mapper.setTo(mapperMatcher.group(2));
                } else {
                    Matcher rangeMatcher = PATTERN_RANGE.matcher(line);
                    if (rangeMatcher.matches()) {
                        Range range = new Range();
                        range.setSource(Long.parseLong(rangeMatcher.group(2)));
                        range.setDestination(Long.parseLong(rangeMatcher.group(1)));
                        range.setLength(Long.parseLong(rangeMatcher.group(3)));
                        mapper.getRanges()
                              .add(range);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        D05 d05 = new D05("src/main/resources/05/input.txt");
        Solver.run(d05);
    }
}
