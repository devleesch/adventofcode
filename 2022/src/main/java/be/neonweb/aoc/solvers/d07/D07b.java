package be.neonweb.aoc.solvers.d07;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@Slf4j
public class D07b extends D07a {

    @Override
    public Object solve() throws Exception {
        VFile root = VFile.of("dir /");
        VFile current = root;
        for (Command command : commands) {
            current = current.apply(command, root);
        }

        log.info("root: " + root.size());
        return commands.stream()
                       .flatMap(command -> command.getOutputs()
                                                  .stream())
                       .filter(VFile::isDir)
                       .filter(vFile -> 70_000_000 - root.size() + vFile.size() >= 30_000_000)
                       .min(Comparator.comparing(VFile::size))
                       .map(VFile::size)
                       .orElseThrow();
    }
}
