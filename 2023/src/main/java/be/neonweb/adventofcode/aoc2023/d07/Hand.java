package be.neonweb.adventofcode.aoc2023.d07;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Slf4j
public class Hand implements Comparable<Hand> {

    private static final Map<Character, Integer> CONVERTER = new HashMap<>();

    static {
        CONVERTER.put('2', 2);
        CONVERTER.put('3', 3);
        CONVERTER.put('4', 4);
        CONVERTER.put('5', 5);
        CONVERTER.put('6', 6);
        CONVERTER.put('7', 7);
        CONVERTER.put('8', 8);
        CONVERTER.put('9', 9);
        CONVERTER.put('T', 10);
        CONVERTER.put('J', 11);
        CONVERTER.put('Q', 12);
        CONVERTER.put('K', 13);
        CONVERTER.put('A', 14);
    }

    private String value;
    private List<Integer> cards = new ArrayList<>();
    private BigInteger bid;
    private Type type;

    private Hand noJHand;

    public static Hand of(String line) {
        Hand hand = new Hand();
        String[] split = line.split(" ");
        hand.setValue(split[0]);
        hand.setBid(new BigInteger(split[1]));

        for (char c : hand.getValue()
                          .toCharArray()) {
            hand.getCards()
                .add(CONVERTER.get(c));
        }

        if (hand.getValue()
                .contains("J")) {
            hand.setNoJHand(hand.getBest());
        }
        hand.setType(hand.computeType());

        return hand;
    }

    @Override
    public int compareTo(Hand hand) {
        if (!type.equals(hand.getType())) {
            return type.getIndex()
                       .compareTo(hand.getType()
                                      .getIndex());
        }

        for (int i = 0; i < cards.size(); i++) {
            Integer thisCard = cards.get(i);
            Integer handCard = hand.getCards()
                                   .get(i);

            thisCard = thisCard == 11 ? 1 : thisCard;
            handCard = handCard == 11 ? 1 : handCard;
            if (!thisCard.equals(handCard)) {
                return thisCard.compareTo(handCard);
            }
        }

        return 0;
    }

    private Type computeType() {
        List<Integer> cards = Objects.nonNull(noJHand) ? noJHand.getCards() : getCards();
        List<Integer> repartition = cards.stream()
                                         .collect(Collectors.groupingBy(Function.identity()))
                                         .values()
                                         .stream()
                                         .map(List::size)
                                         .sorted()
                                         .toList();
        return Type.of(repartition);
    }

    private Hand getBest() {
        Hand best = CONVERTER.keySet()
                             .stream()
                             .filter(c -> c != 'J')
                             .map(c -> {
                                 String replaced = value.replaceAll("J", c.toString());
                                 //log.info("c=" + c + ";value=" + value + ";replaced=" + replaced);
                                 return Hand.of(replaced + " " + getBid());
                             })
                             .max(Hand::compareTo)
                             .orElseThrow();
        log.info("hand=" + this.getValue() + "; best=" + best.getValue());
        return best;
    }
}
