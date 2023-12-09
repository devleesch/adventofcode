package be.neonweb.adventofcode.aoc2023.d06;

import be.neonweb.adventofcode.aoc2023.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D06 extends Solver {

    private static final Pattern PATTERN_NUMBER = Pattern.compile("[0-9]+");

    private List<Race> races = new ArrayList<>();

    public D06(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        parse(inputLines);
        return races.stream()
                    .map(Race::nbWin)
                    .reduce(1L, (a, b) -> a * b);
    }

    @Override
    public Object part2(List<String> inputLines) {
        Race race = new Race();

        String time = races.stream()
                           .map(Race::getTime)
                           .map(String::valueOf)
                           .reduce("", String::concat);

        String distance = races.stream()
                             .map(Race::getDistance)
                             .map(String::valueOf)
                             .reduce("", String::concat);

        race.setTime(Long.parseLong(time));
        race.setDistance(Long.parseLong(distance));

        return race.nbWin();
    }

    private void parse(List<String> inputLines) {
        Matcher matcherTime = PATTERN_NUMBER.matcher(inputLines.get(0));
        Matcher matcherDistance = PATTERN_NUMBER.matcher(inputLines.get(1));
        while (matcherTime.find() && matcherDistance.find()) {
            Race race = new Race();
            race.setTime(Long.parseLong(matcherTime.group()));
            race.setDistance(Long.parseLong(matcherDistance.group()));
            races.add(race);
        }
    }

    public static void main(String[] args) {
        D06 d06 = new D06("src/main/resources/06/input.txt");
        Solver.run(d06);
    }
}
