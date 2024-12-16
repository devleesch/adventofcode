package be.neonweb.adventofcode.d14;

import be.neonweb.adventofcode.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class D14 extends Day {

    private static final Pattern PATTERN_ROBOT = Pattern.compile("p=(-?[0-9]+),(-?[0-9]+) v=(-?[0-9]+),(-?[0-9]+)");
    private static final Logger log = LoggerFactory.getLogger(D14.class);

    @Override
    public String part1(List<String> lines) {
        List<Robot> robots = lines.stream()
                                  .map(Robot::new)
                                  .toList();

        int x1 = 101;
        int y1 = 103;

        for (int i = 0; i < 100; i++) {
            robots.forEach(robot -> robot.move(x1, y1));
        }

        return robots.stream()
                     .filter(robot -> robot.x != x1 / 2)
                     .filter(robot -> robot.y != y1 / 2)
                     .collect(Collectors.groupingBy(robot -> robot.quadrant(x1, y1)))
                     .values()
                     .stream()
                     .map(List::size)
                     .reduce(1, (a, b) -> a * b)
                     .toString();
    }

    @Override
    public String part2(List<String> lines) {
        List<Robot> robots = lines.stream()
                                  .map(Robot::new)
                                  .toList();

        int x1 = 101;
        int y1 = 103;

        JFrame jFrame = new JFrame();
        jFrame.setLayout(new FlowLayout());

        JLabel image = new JLabel(new ImageIcon(image(x1, y1, robots)));
        //image.setBounds(2, 2, x1 * 5, y1 * 5);
        jFrame.add(image);

        JLabel turn = new JLabel();
        turn.setText("0");
        jFrame.add(turn);

        JButton next = new JButton("Next");
        AtomicInteger i = new AtomicInteger(0);
        next.addActionListener(e -> {
            robots.forEach(robot -> robot.move(x1, y1));
            image.setIcon(new ImageIcon(image(x1, y1, robots)));
            turn.setText(String.valueOf(i.incrementAndGet()));
        });
        jFrame.add(next);

        jFrame.setBounds(0, 0, x1 * 5 + 20, y1 * 5 + 75);
        jFrame.setVisible(true);

        for (int j = 0; j < 1_000_000; j++) {
            robots.forEach(robot -> robot.move(x1, y1));

            //if (j == 98 || (j-98) % 103 == 0) {
            if (j <= 7411) {
                log.info(String.valueOf(j));
                image.setIcon(new ImageIcon(image(x1, y1, robots)));
                turn.setText(String.valueOf(j));
            }
        }

        return "";
    }

    public static BufferedImage image(int x1, int y1, List<Robot> robots) {
        BufferedImage bufferedImage = new BufferedImage(x1 * 5, y1 * 5, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < y1; y++) {
            for (int x = 0; x < x1; x++) {
                int cX = x;
                int cY = y;
                boolean hasRobot = robots.stream()
                                         .anyMatch(r -> r.x == cX && r.y == cY);
                for (int pY = 0; pY < 5; pY++) {
                    for (int pX = 0; pX < 5; pX++) {
                        bufferedImage.setRGB(x * 5 + pX, y * 5 + pY, hasRobot ? 255 : 0);
                    }
                }
            }
        }
        return bufferedImage;
    }

    @Getter
    @Setter
    @ToString
    public static class Robot {
        private int x;
        private int y;
        private int vX;
        private int vY;

        public Robot(String line) {
            Matcher matcher = PATTERN_ROBOT.matcher(line);
            if (!matcher.matches()) {
                throw new RuntimeException("Unable to create Robot from: " + line);
            }
            this.x = Integer.parseInt(matcher.group(1));
            this.y = Integer.parseInt(matcher.group(2));
            this.vX = Integer.parseInt(matcher.group(3));
            this.vY = Integer.parseInt(matcher.group(4));
        }

        public void move(int x1, int y1) {
            x = x + vX;
            while (x >= x1) {
                x = x - x1;
            }
            while (x < 0) {
                x = x + x1;
            }

            y = y + vY;
            while (y >= y1) {
                y = y - y1;
            }
            while (y < 0) {
                y = y + y1;
            }
        }

        public int quadrant(int x1, int y1) {
            int qX = 0;
            if (this.x > x1 / 2) {
                qX = 1;
            }

            int qY = 0;
            if (this.y > y1 / 2) {
                qY = 1;
            }

            return 2 * qX + qY;
        }
    }
}
