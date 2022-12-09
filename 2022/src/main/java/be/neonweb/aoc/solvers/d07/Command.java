package be.neonweb.aoc.solvers.d07;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Command {

    private String name;

    private String arg;

    private List<VFile> outputs = new ArrayList<>();

    public static Command of(String line) {
        Command command = new Command();

        String[] split = line.split(" ");

        command.name = split[1];
        if (split.length > 2) {
            command.arg = split[2];
        }

        return command;
    }

}
