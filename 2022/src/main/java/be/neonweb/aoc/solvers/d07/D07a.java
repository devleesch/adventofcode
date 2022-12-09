package be.neonweb.aoc.solvers.d07;

import be.neonweb.aoc.solvers.Solver;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class D07a extends Solver {

    List<Command> commands = new ArrayList<>();

    @Override
    public void config() throws Exception {

    }

    @Override
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/d07/input.txt"));
        Command command = new Command();
        for (String line : lines) {
            if (line.startsWith("$")) {
                command = Command.of(line);
                commands.add(command);
            } else {
                command.getOutputs()
                       .add(VFile.of(line));
            }
        }
    }

    @Override
    public Object solve() throws Exception {
        VFile root = VFile.of("dir /");
        VFile current = root;
        for (Command command : commands) {
            current = current.apply(command, root);
        }

        return commands.stream()
                       .flatMap(command -> command.getOutputs()
                                                  .stream())
                       .filter(VFile::isDir)
                       .map(VFile::size)
                       .filter(size -> size < 100000)
                       .reduce(0, Integer::sum);
    }
}
