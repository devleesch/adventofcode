package be.neonweb.adventofcode.aoc2023.d04;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Card {

    private static final Pattern PATTERN_LINE = Pattern.compile("Card[ ]+(?<id>[0-9]+): (?<winning>[0-9 ]+)\\|(?<pick>[0-9 ]+)");
    private static final Pattern PATTERN_NUMBER = Pattern.compile("[0-9]+");

    private long id;
    private Set<Integer> winning = new LinkedHashSet<>();
    private Set<Integer> pick = new LinkedHashSet<>();

    public static Card parse(String line) {
        Card card = new Card();

        Matcher matcherLine = PATTERN_LINE.matcher(line);
        if (matcherLine.matches()) {
            card.setId(Integer.parseInt(matcherLine.group("id")));

            Matcher matcherWinning = PATTERN_NUMBER.matcher(matcherLine.group("winning"));
            while (matcherWinning.find()) {
                card.getWinning()
                    .add(Integer.parseInt(matcherWinning.group()));
            }

            Matcher matcherPick = PATTERN_NUMBER.matcher(matcherLine.group("pick"));
            while (matcherPick.find()) {
                card.getPick()
                    .add(Integer.parseInt(matcherPick.group()));
            }
        }

        return card;
    }

    public double points() {
        long matches = matches();
        if (matches <= 0) {
            return 0D;
        }
        return Math.pow(2, matches - 1);
    }

    public long matches() {
        return pick.stream()
                   .filter(pick -> winning.contains(pick))
                   .count();
    }
}
