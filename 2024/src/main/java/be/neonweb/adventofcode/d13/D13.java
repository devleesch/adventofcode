package be.neonweb.adventofcode.d13;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D13 extends Day {

    private static final Pattern PATTERN_BUTTON = Pattern.compile("Button [AB]: X\\+([0-9]+), Y\\+([0-9]+)");
    private static final Pattern PATTERN_PRIZE = Pattern.compile("Prize: X=([0-9]+), Y=([0-9]+)");

    @Override
    public String part1(List<String> lines) {
        int i = 0;
        List<Machine> machines = new ArrayList<>();
        while (i < lines.size()) {
            Machine machine = Machine.from(
                    List.of(
                            lines.get(i),
                            lines.get(i + 1),
                            lines.get(i + 2)
                    )
            );
            machines.add(machine);
            i += 4;
        }

        return machines.stream()
                       .map(Machine::token)
                       .filter(Objects::nonNull)
                       .reduce(BigDecimal.ZERO, BigDecimal::add)
                       .toString();
    }

    @Override
    public String part2(List<String> lines) {
        int i = 0;
        List<Machine> machines = new ArrayList<>();

        BigDecimal drift = new BigDecimal("10000000000000");

        while (i < lines.size()) {
            Machine machine = Machine.from(
                    List.of(
                            lines.get(i),
                            lines.get(i + 1),
                            lines.get(i + 2)
                    )
            );

            machine.getPrize()
                   .setX(machine.getPrize()
                                .getX()
                                .add(drift));

            machine.getPrize()
                   .setY(machine.getPrize()
                                .getY()
                                .add(drift));

            machines.add(machine);
            i += 4;
        }

        return machines.stream()
                       .map(Machine::token)
                       .filter(Objects::nonNull)
                       .reduce(BigDecimal.ZERO, BigDecimal::add)
                       .toString();
    }

    @Getter
    @ToString
    public static class Machine {
        private Button buttonA;
        private Button buttonB;
        private Position claw;
        private Position prize;

        public static Machine from(List<String> lines) {
            Machine machine = new Machine();
            machine.buttonA = Button.from(lines.get(0));
            machine.buttonB = Button.from(lines.get(1));
            machine.claw = new Position(BigDecimal.ZERO, BigDecimal.ZERO);
            machine.prize = Position.from(lines.get(2));
            return machine;
        }

        public BigDecimal token() {
            try {
                BigDecimal bPress = prize.y.multiply(buttonA.xMove)
                                           .subtract(buttonA.yMove.multiply(prize.x))
                                           .divide(buttonB.xMove.multiply(new BigDecimal("-1"))
                                                                .multiply(buttonA.yMove)
                                                                .add(buttonA.xMove.multiply(buttonB.yMove)));
                BigDecimal aPress = prize.x.subtract(buttonB.xMove.multiply(bPress))
                                           .divide(buttonA.xMove);

                return aPress.multiply(new BigDecimal("3"))
                             .add(bPress);
            } catch (ArithmeticException e) {
                return null;
            }
        }
    }

    @Getter
    @ToString
    public static class Button {
        private BigDecimal xMove;
        private BigDecimal yMove;

        public static Button from(String line) {
            Matcher matcher = PATTERN_BUTTON.matcher(line);
            if (matcher.matches()) {
                Button button = new Button();
                button.xMove = new BigDecimal(matcher.group(1));
                button.yMove = new BigDecimal(matcher.group(2));
                return button;
            }
            throw new RuntimeException("Unable to create Button");
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Position {
        private BigDecimal x;
        private BigDecimal y;

        public Position(BigDecimal x, BigDecimal y) {
            this.x = x;
            this.y = y;
        }

        public static Position from(String line) {
            Matcher matcher = PATTERN_PRIZE.matcher(line);
            if (matcher.matches()) {
                return new Position(new BigDecimal(matcher.group(1)), new BigDecimal(matcher.group(2)));
            }
            throw new RuntimeException("Unable to create Prize");
        }
    }
}
