package be.neonweb.adventofcode.d09;

import be.neonweb.adventofcode.Day;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class D09 extends Day {

    @Override
    public String part1(List<String> lines) {
        boolean isFile = true;
        int id = 0;

        List<Chunck> chuncks = new ArrayList<>();
        for (String s : lines.getFirst()
                             .split("")) {
            int size = Integer.parseInt(s);

            for (int i = 0; i < size; i++) {
                if (isFile) {
                    chuncks.add(new Chunck(id));
                } else {
                    chuncks.add(new Chunck(null));
                }
            }

            if (isFile) {
                id++;
            }

            isFile = !isFile;
        }

        while (hasBlankBetween(chuncks)) {
            //log.info(print(chuncks));
            defrag(chuncks);
        }

        return IntStream.range(0, chuncks.size())
                        .filter(i -> Objects.nonNull(chuncks.get(i).id))
                        .mapToObj(i -> new BigDecimal(chuncks.get(i).id).multiply(new BigDecimal(i)))
                        .reduce(BigDecimal::add)
                        .map(BigDecimal::toString)
                        .orElse("-");
    }

    private boolean hasBlankBetween(List<Chunck> chuncks) {
        Optional<Chunck> firstEmpty = chuncks.stream()
                                             .filter(chunck -> Objects.isNull(chunck.id))
                                             .findFirst();
        if (firstEmpty.isPresent()) {
            int firstEmptyIndex = chuncks.indexOf(firstEmpty.get());
            return chuncks.subList(firstEmptyIndex, chuncks.size())
                          .stream()
                          .anyMatch(chunck -> Objects.nonNull(chunck.id));
        }
        return false;
    }

    @Override
    public String part2(List<String> lines) {
        boolean isFile = true;
        int id = 0;

        List<Chunck> chuncks = new ArrayList<>();
        for (String s : lines.getFirst()
                             .split("")) {
            int size = Integer.parseInt(s);

            for (int i = 0; i < size; i++) {
                if (isFile) {
                    chuncks.add(new Chunck(id));
                } else {
                    chuncks.add(new Chunck(null));
                }
            }

            if (isFile) {
                id++;
            }

            isFile = !isFile;
        }

        chuncks.stream()
               .filter(chunck -> Objects.nonNull(chunck.id))
               .map(chunck -> chunck.id)
               .distinct()
               .sorted(Comparator.reverseOrder())
               .forEach(chunkId -> {
                   List<Chunck> file = chuncks.stream()
                                              .filter(chunck -> Objects.nonNull(chunck.id))
                                              .filter(chunck -> chunck.id.equals(chunkId))
                                              .toList();
                   defrag2(chuncks, file);
               });

        return IntStream.range(0, chuncks.size())
                        .filter(i -> Objects.nonNull(chuncks.get(i).id))
                        .mapToObj(i -> new BigDecimal(chuncks.get(i).id).multiply(new BigDecimal(i)))
                        .reduce(BigDecimal::add)
                        .map(BigDecimal::toString)
                        .orElse("-");
    }

    public static class Chunck {
        private Integer id;

        public Chunck(Integer id) {
            this.id = id;
        }
    }

    private static String print(List<Chunck> chuncks) {
        return chuncks.stream()
                      .map(chunck -> Objects.isNull(chunck.id) || chunck.id < 0 ? "." : String.valueOf(chunck.id))
                      .map(v -> {
                          while (v.length() < 4) {
                              v = " " + v;
                          }
                          return v;
                      })
                      .collect(Collectors.joining("|"));
    }

    private static void defrag(List<Chunck> chuncks) {
        Optional<Chunck> firstEmpty = chuncks.stream()
                                             .filter(chunck -> Objects.isNull(chunck.id))
                                             .findFirst();
        if (firstEmpty.isPresent()) {
            int firstEmptyIndex = chuncks.indexOf(firstEmpty.get());
            Optional<Chunck> lastChunk = IntStream.range(0, chuncks.size())
                                                  .mapToObj(i -> chuncks.get(chuncks.size() - 1 - i))
                                                  .filter(chunck -> Objects.nonNull(chunck.id))
                                                  .findFirst();
            if (lastChunk.isPresent()) {
                int lastChunkIndex = chuncks.indexOf(lastChunk.get());

                chuncks.remove(lastChunkIndex);
                chuncks.remove(firstEmptyIndex);

                chuncks.add(firstEmptyIndex, lastChunk.get());
                chuncks.add(lastChunkIndex, firstEmpty.get());
            }
        }
    }

    private static void defrag2(List<Chunck> chuncks, List<Chunck> file) {
        List<Integer> fileIndexes = file.stream()
                                        .map(chuncks::indexOf)
                                        .toList();

        List<Chunck> empties = new ArrayList<>();
        for (int i = 0; i < fileIndexes.getFirst(); i++) {
            Chunck current = chuncks.get(i);
            if (Objects.isNull(current.id)) {
                empties.add(current);
            } else {
                if (empties.size() >= file.size()) {
                    break;
                }
                empties.clear();
            }
        }

        if (empties.size() >= file.size()) {
            for (int j = 0; j < file.size(); j++) {
                chuncks.set(chuncks.indexOf(empties.get(j)), file.get(j));
            }
            for (int j = 0; j < file.size(); j++) {
                chuncks.set(fileIndexes.get(j), empties.get(j));
            }
        }
    }

}
