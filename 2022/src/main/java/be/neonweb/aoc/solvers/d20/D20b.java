package be.neonweb.aoc.solvers.d20;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class D20b extends D20a {

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d20/input.txt"));
        for (int i = 0; i < lines.size(); i++) {
            encrypted.add(Bloc.of(lines.get(i), 811589153));
        }
    }

    @Override
    public Object solve() throws Exception {
        List<Bloc> decrypted = new ArrayList<>(encrypted);
        Bloc bloc0 = encrypted.stream()
                              .filter(bloc -> bloc.getValue() == 0)
                              .findFirst()
                              .get();

        for (int i = 0; i < 10; i++) {
            log.info("round " + i);
            for (Bloc bloc : encrypted) {
                int currentIndex = decrypted.indexOf(bloc);
                Long newIndex = currentIndex + bloc.getValue();
                newIndex = getLoopIndex(newIndex);

                //log.info(bloc.getValue() + " moves between " + decrypted.get(newIndex) + " and " + decrypted.get(getLoopIndex(newIndex + 1)));
                decrypted.remove(bloc);
                decrypted.add(newIndex.intValue(), bloc);
                //log.info("" + decrypted);
            }
        }

        Long a1000 = getNthAfter(decrypted, bloc0, 1000);
        Long a2000 = getNthAfter(decrypted, bloc0, 2000);
        Long a3000 = getNthAfter(decrypted, bloc0, 3000);

        return a1000 + a2000 + a3000;
    }
}
