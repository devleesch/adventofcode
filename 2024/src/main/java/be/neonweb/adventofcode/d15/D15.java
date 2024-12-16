package be.neonweb.adventofcode.d15;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class D15 extends Day {
    @Override
    public String part1(List<String> lines) {
        List<Item> items = IntStream.range(0, lines.indexOf(""))
                                    .mapToObj(y -> IntStream.range(0, lines.get(y)
                                                                           .length())
                                                            .filter(x -> lines.get(y)
                                                                              .charAt(x) != '.')
                                                            .mapToObj(x -> Item.from(x, y, String.valueOf(lines.get(y)
                                                                                                               .charAt(x)))
                                                            ))
                                    .flatMap(itemStream -> itemStream)
                                    .toList();

        List<Move> moves = lines.subList(lines.indexOf("") + 1, lines.size())
                                .stream()
                                .flatMap(line -> Arrays.stream(line.split("")))
                                .map(Move::from)
                                .toList();

        Item robot = items.stream()
                          .filter(item -> item instanceof Robot)
                          .findFirst()
                          .orElseThrow();

        moves.forEach(move -> robot.move(move, items));

        return items.stream()
                    .filter(item -> item instanceof Box)
                    .map(Item::gps)
                    .reduce(0, Integer::sum)
                    .toString();
    }

    @Override
    public String part2(List<String> lines) {
        List<String> newLines = lines.stream()
                                     .map(line -> line.replaceAll("#", "##")
                                                      .replaceAll("O", "[]")
                                                      .replaceAll("\\.", "..")
                                                      .replaceAll("@", "@."))
                                     .toList();

        List<Item> items = IntStream.range(0, lines.indexOf(""))
                                    .mapToObj(y -> IntStream.range(0, newLines.get(y)
                                                                              .length())
                                                            .filter(x -> newLines.get(y)
                                                                                 .charAt(x) != '.')
                                                            .mapToObj(x -> Item.from(x, y, String.valueOf(newLines.get(y)
                                                                                                                  .charAt(x)))
                                                            ))
                                    .flatMap(itemStream -> itemStream)
                                    .toList();

        List<Move> moves = lines.subList(lines.indexOf("") + 1, lines.size())
                                .stream()
                                .flatMap(line -> Arrays.stream(line.split("")))
                                .map(Move::from)
                                .toList();

        Item robot = items.stream()
                          .filter(item -> item instanceof Robot)
                          .findFirst()
                          .orElseThrow();

        //display(null, items);
        moves.forEach(move -> {
            robot.move(move, items);
            //display(move, items);
        });

        return items.stream()
                    .filter(item -> item instanceof BoxL)
                    .map(Item::gps)
                    .reduce(0, Integer::sum)
                    .toString();
    }

    public static void display(Move move, List<Item> items) {
        int maxY = items.stream()
                        .map(item -> item.position.y)
                        .max(Integer::compareTo)
                        .orElse(0);

        int maxX = items.stream()
                        .map(item -> item.position.x)
                        .max(Integer::compareTo)
                        .orElse(0);

        System.out.println("Move: " + move);

        for (int y = 0; y < maxY + 1; y++) {
            for (int x = 0; x < maxX + 1; x++) {
                Position position = new Position(x, y);
                String display = items.stream()
                                      .filter(item -> item.position.equals(position))
                                      .findFirst()
                                      .map(item -> item.display)
                                      .orElse(".");
                System.out.print(display);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Getter
    @Setter
    @ToString
    public static abstract class Item {
        private String display;
        private Position position;

        public Item(int x, int y, String display) {
            this.display = display;
            this.position = new Position(x, y);
        }

        public void move(Move move, List<Item> items) {
            if (canMove(move, items)) {
                Position nextPosition = move.function.apply(this.getPosition());
                items.stream()
                     .filter(item -> item.position.equals(nextPosition))
                     .findFirst()
                     .ifPresent(item -> item.move(move, items));
                setPosition(nextPosition);
            }
        }

        public boolean canMove(Move move, List<Item> items) {
            Position nextPosition = move.function.apply(position);
            Optional<Item> optionalItem = items.stream()
                                               .filter(item -> item.position.equals(nextPosition))
                                               .findFirst();

            return optionalItem.map(item -> item.canMove(move, items))
                               .orElse(true);
        }

        public int gps() {
            return 100 * position.y + position.x;
        }

        public static Item from(int x, int y, String value) {
            return switch (value) {
                case "#" -> new Wall(x, y);
                case "@" -> new Robot(x, y);
                case "O" -> new Box(x, y);
                case "[" -> new BoxL(x, y);
                case "]" -> new BoxR(x, y);
                default -> throw new RuntimeException("Unknown item: " + value);
            };
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Robot extends Item {
        public Robot(int x, int y) {
            super(x, y, "@");
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Box extends Item {
        public Box(int x, int y) {
            super(x, y, "O");
        }
    }

    @Getter
    @Setter
    @ToString
    public static class BoxL extends Item {
        public BoxL(int x, int y) {
            super(x, y, "[");
        }

        @Override
        public void move(Move move, List<Item> items) {
            if (move.equals(Move.LEFT) || move.equals(Move.RIGHT)) {
                super.move(move, items);
                return;
            }

            if (canMove(move, items)) {
                BoxR boxR = items.stream()
                                 .filter(item -> item instanceof BoxR)
                                 .filter(item -> item.position.equals(new Position(this.getPosition().x + 1, this.getPosition().y)))
                                 .findFirst()
                                 .map(BoxR.class::cast)
                                 .orElseThrow();

                Stream.of(move.function.apply(this.getPosition()), move.function.apply(boxR.getPosition()))
                      .flatMap(position -> items.stream()
                                                .filter(item -> item.position.equals(position)))
                      .forEach(item -> item.move(move, items));

                this.setPosition(move.function.apply(this.getPosition()));
                boxR.setPosition(move.function.apply(boxR.getPosition()));
            }
        }

        @Override
        public boolean canMove(Move move, List<Item> items) {
            if (move.equals(Move.LEFT) || move.equals(Move.RIGHT)) {
                return super.canMove(move, items);
            }

            BoxR boxR = items.stream()
                             .filter(item -> item instanceof BoxR)
                             .filter(item -> item.position.equals(new Position(this.getPosition().x + 1, this.getPosition().y)))
                             .findFirst()
                             .map(BoxR.class::cast)
                             .orElseThrow();

            return Stream.of(move.function.apply(this.getPosition()), move.function.apply(boxR.getPosition()))
                         .map(nextPosition -> items.stream()
                                                   .filter(item -> item.position.equals(nextPosition))
                                                   .findFirst())
                         .flatMap(Optional::stream)
                         .map(item -> item.canMove(move, items))
                         .reduce(true, Boolean::logicalAnd);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class BoxR extends Item {
        public BoxR(int x, int y) {
            super(x, y, "]");
        }

        @Override
        public void move(Move move, List<Item> items) {
            if (move.equals(Move.LEFT) || move.equals(Move.RIGHT)) {
                super.move(move, items);
                return;
            }

            if (canMove(move, items)) {
                BoxL boxL = items.stream()
                                 .filter(item -> item instanceof BoxL)
                                 .filter(item -> item.position.equals(new Position(this.getPosition().x - 1, this.getPosition().y)))
                                 .findFirst()
                                 .map(BoxL.class::cast)
                                 .orElseThrow();

                Stream.of(move.function.apply(this.getPosition()), move.function.apply(boxL.getPosition()))
                      .flatMap(position -> items.stream()
                                                .filter(item -> item.position.equals(position)))
                      .forEach(item -> item.move(move, items));

                this.setPosition(move.function.apply(this.getPosition()));
                boxL.setPosition(move.function.apply(boxL.getPosition()));
            }
        }

        @Override
        public boolean canMove(Move move, List<Item> items) {
            if (move.equals(Move.LEFT) || move.equals(Move.RIGHT)) {
                return super.canMove(move, items);
            }

            BoxL boxL = items.stream()
                             .filter(item -> item instanceof BoxL)
                             .filter(item -> item.position.equals(new Position(this.getPosition().x - 1, this.getPosition().y)))
                             .findFirst()
                             .map(BoxL.class::cast)
                             .orElseThrow();

            return Stream.of(move.function.apply(this.getPosition()), move.function.apply(boxL.getPosition()))
                         .map(nextPosition -> items.stream()
                                                   .filter(item -> item.position.equals(nextPosition))
                                                   .findFirst())
                         .flatMap(Optional::stream)
                         .map(item -> item.canMove(move, items))
                         .reduce(true, Boolean::logicalAnd);
        }

    }

    @Getter
    @Setter
    @ToString
    public static class Wall extends Item {
        public Wall(int x, int y) {
            super(x, y, "#");
        }

        @Override
        public boolean canMove(Move move, List<Item> items) {
            return false;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public enum Move {
        UP("^", position -> new Position(position.x, position.y - 1)),
        DOWN("v", position -> new Position(position.x, position.y + 1)),
        LEFT("<", position -> new Position(position.x - 1, position.y)),
        RIGHT(">", position -> new Position(position.x + 1, position.y));

        private final String value;
        private final Function<Position, Position> function;

        Move(String value, Function<Position, Position> function) {
            this.value = value;
            this.function = function;
        }

        public static Move from(String value) {
            return Arrays.stream(Move.values())
                         .filter(move -> move.value.equals(value))
                         .findFirst()
                         .orElseThrow();
        }
    }
}
