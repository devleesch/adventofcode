package be.neonweb.adventofcode.aoc2023.aoc02;

import be.neonweb.adventofcode.aoc2023.Solver;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Aoc02 extends Solver {

    private static final Pattern GAME_PATTERN = Pattern.compile("Game ([0-9]+)");
    private static final Pattern COLOR_PATTERN = Pattern.compile("([0-9]+) ([a-z]+)");
    private List<Game> games;

    public Aoc02(String inputPath) {
        super(inputPath);
    }

    @Override
    public Object part1(List<String> inputLines) {
        games = parse(inputLines);
        return games.stream()
                    .filter(game -> game.getCubeSets()
                                               .stream()
                                               .allMatch(CubeSet::possible))
                    .map(Game::getIndex)
                    .reduce(0, Integer::sum);
    }

    @Override
    public Object part2(List<String> inputLines) {
        return games.stream()
                    .map(Game::power)
                    .reduce(0L, Long::sum);
    }

    public static void main(String[] args) {
        Aoc02 aoc02 = new Aoc02("src/main/resources/02/input.txt");
        Solver.run(aoc02);
    }

    private List<Game> parse(List<String> lines) {
        List<Game> games = new ArrayList<>();
        for (String line : lines) {
            String[] gameSplit = line.split(":");
            Matcher gameMatcher = GAME_PATTERN.matcher(gameSplit[0]);
            if (gameMatcher.matches()) {
                Game game = new Game();
                game.setIndex(Integer.parseInt(gameMatcher.group(1)));
                games.add(game);

                List<CubeSet> cubeSets = new ArrayList<>();
                for (String setSplit : gameSplit[1].split(";")) {
                    CubeSet cubeSet = new CubeSet();
                    for (String colorSplit : setSplit.split(",")) {
                        colorSplit = colorSplit.strip();
                        Matcher colorMatcher = COLOR_PATTERN.matcher(colorSplit);
                        if (colorMatcher.matches()) {
                            cubeSet.add(colorMatcher.group(2), Integer.parseInt(colorMatcher.group(1)));
                        }
                    }
                    cubeSets.add(cubeSet);
                }
                game.setCubeSets(cubeSets);
            }
        }
        return games;
    }
}
