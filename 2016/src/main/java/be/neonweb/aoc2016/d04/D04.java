package be.neonweb.aoc2016.d04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import be.neonweb.aoc2016.AbstractDay;
import lombok.Getter;

public class D04 extends AbstractDay {

    private List<Room> rooms = new ArrayList<>();

    @Override
    public Object part1() {
        
        for (String l : lines) {
            rooms.add(new Room(l));
        }

        return rooms.stream()
                .filter(r -> r.isValid())
                .mapToInt(r -> r.getSectorId())
                .sum();
    }

    @Override
    public Object part2() {
        rooms.stream()
            .filter(Room::isValid)
            .forEach(r -> System.out.printf("%-256s => %s%n", r, r.decrypt()));

        return null;
    }

    @Getter
    private static class Room {
        private String line;
        private String encryptedName;
        private Integer sectorId;
        private String checksum;

        private Room(String line) {
            this.line = line;

            int lastIndexOf = this.line.lastIndexOf("-");
            this.encryptedName = this.line.substring(0, lastIndexOf);

            int lastIndexOf2 = this.line.indexOf("[");
            this.sectorId = Integer.valueOf(this.line.substring(lastIndexOf + 1, lastIndexOf2));
            this.checksum = this.line.substring(lastIndexOf2 + 1, this.line.length() - 1);
        }

        private boolean isValid() {
            Map<Character, Integer> counts = new LinkedHashMap<>();

            for (char c : this.encryptedName.replace("-", "").toCharArray()) {
                if (!counts.containsKey(c)) {
                    counts.put(c, 0);
                }
                counts.replace(c, counts.get(c) + 1);
            }

            String checksum = counts.entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed()
                    .thenComparing(Map.Entry.comparingByKey()))
                .map(entry -> entry.getKey().toString())
                .limit(5)
                .collect(Collectors.joining());

            return this.checksum.equals(checksum);
        }

        private String decrypt() {
            String name = "";
            for (char c : encryptedName.toCharArray()) {
                if (c != '-') {
                    int v = ((c - 97 + sectorId) % 26) + 97;
                    name += (char) v;
                } else {
                    name += ' ';
                }
            }
            return name;
        }

        @Override
        public String toString() {
            return "Room [line=" + line + ", encryptedName=" + encryptedName + ", sectorId=" + sectorId + ", checksum="
                    + checksum + "]";
        }
    }

}
