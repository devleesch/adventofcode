package be.neonweb.aoc2016.d02;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import be.neonweb.aoc2016.AbstractDay;
import lombok.Getter;

public class D02 extends AbstractDay {

    // 99264
    @Override
    public Object part1() {

        List<Key> pad = new ArrayList<>();

        List<String> padStr = new ArrayList<>();
        padStr.add("1 2 3");
        padStr.add("4 5 6");
        padStr.add("7 8 9");

        int y = 0;
        for (String l : padStr) {
            int x = 0;
            for (char c : l.toCharArray()) {
                if (c != ' ') {
                    Key key = new Key(x, y, String.valueOf(c));
                    pad.add(key);
                }
                x += 1;
            }
            y += 1;
        }

        Key key = pad.stream()
                .filter(k -> k.getValue().equals("5"))
                .findFirst()
                .orElseThrow();
        String code = "";
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                key = move(key, c, pad);
            }
            code += key.getValue();
        }

        return code;
    }

    @Override
    public Object part2() {
        List<Key> pad = new ArrayList<>();

        List<String> padStr = new ArrayList<>();
        padStr.add("    1");
        padStr.add("  2 3 4");
        padStr.add("5 6 7 8 9");
        padStr.add("  A B C");
        padStr.add("    D");

        int y = 0;
        for (String l : padStr) {
            int x = 0;
            for (char c : l.toCharArray()) {
                if (c != ' ') {
                    Key key = new Key(x, y, String.valueOf(c));
                    pad.add(key);
                }
                x += 1;
            }
            y += 1;
        }

        Key key = pad.stream()
                .filter(k -> k.getValue().equals("5"))
                .findFirst()
                .get();

        String code = "";

        for (String line : lines) {
            for (char c : line.toCharArray()) {
                key = move(key, c, pad);
            }
            code += key.getValue();
        }

        return code;
    }

    private Key move(Key key, char c, List<Key> pad) {
        return switch (c) {
            case 'U' -> key.getUp().apply(pad);
            case 'D' -> key.getDown().apply(pad);
            case 'L' -> key.getLeft().apply(pad);
            case 'R' -> key.getRight().apply(pad);
            default -> throw new UnsupportedOperationException();
        };
    }

    public String getFileName() {
        return "input.txt";
    }

    @Getter
    public static class Key {
        private int x;
        private int y;
        private String value;
        private Function<List<Key>, Key> up = pad -> pad.stream()
                .filter(key -> key.getX() == this.getX() && key.getY() == this.getY() - 1).findFirst().orElse(this);
        private Function<List<Key>, Key> down = pad -> pad.stream()
                .filter(key -> key.getX() == this.getX() && key.getY() == this.getY() + 1).findFirst().orElse(this);
        private Function<List<Key>, Key> left = pad -> pad.stream()
                .filter(key -> key.getX() == this.getX() - 2 && key.getY() == this.getY()).findFirst().orElse(this);
        private Function<List<Key>, Key> right = pad -> pad.stream()
                .filter(key -> key.getX() == this.getX() + 2 && key.getY() == this.getY()).findFirst().orElse(this);

        public Key(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

}
