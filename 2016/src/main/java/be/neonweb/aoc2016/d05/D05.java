package be.neonweb.aoc2016.d05;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import be.neonweb.aoc2016.AbstractDay;

public class D05 extends AbstractDay {

    @Override
    public Object part1() {
        try {
            String input = lines.get(0);

            String password = "";
            MessageDigest md = MessageDigest.getInstance("MD5");

            long i = 0;
            while(password.length() < 8) {
                String current = input+i;
                byte[] digest = md.digest(current.getBytes());
                String formatHex = HexFormat.of().formatHex(digest);
                
                if (formatHex.startsWith("00000")) {
                    password += formatHex.charAt(5);
                }

                i++;
            }

            return password;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object part2() {
        try {
            String input = lines.get(0);

            List<String> password = new ArrayList<>(8);
            for (int i = 0; i < 8; i++) {
                password.add(" ");
            }
            MessageDigest md = MessageDigest.getInstance("MD5");

            long i = 0;
            while(password.contains(" ")) {
                String current = input+i;
                byte[] digest = md.digest(current.getBytes());
                String formatHex = HexFormat.of().formatHex(digest);
                
                if (formatHex.startsWith("00000")) {
                    int index = Integer.valueOf(formatHex.charAt(5)) - 48;
                    if (index >= 0 && index < password.size() && password.get(index) == " ") {
                        char value = formatHex.charAt(6);
                        password.set(index, String.valueOf(value));
                    }
                }

                i++;
            }

            return String.join("", password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
