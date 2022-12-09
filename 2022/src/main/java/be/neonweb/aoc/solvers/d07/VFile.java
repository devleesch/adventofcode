package be.neonweb.aoc.solvers.d07;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VFile {

    private VFile parent;

    private List<VFile> children = new ArrayList<>();

    private boolean dir = false;

    private String name;

    private int size;

    public VFile apply(Command command, VFile root) {
        String name = command.getName();
        if ("cd".equals(name)) {
            if ("/".equals(command.getArg())) {
                return root;
            } else if ("..".equals(command.getArg())) {
                return this.parent;
            } else {
                return this.children.stream()
                                    .filter(VFile::isDir)
                                    .filter(child -> child.name.equals(command.getArg()))
                                    .findFirst()
                                    .orElseThrow();
            }
        } else if ("ls".equals(name)) {
            children = command.getOutputs();
            children.forEach(child -> child.parent = this);
            return this;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int size() {
        if (isDir()) {
            return this.children.stream()
                                .map(VFile::size)
                                .reduce(0, Integer::sum);
        } else {
            return this.size;
        }
    }

    @Override
    public String toString() {
        return "VFile{" +
                "dir=" + dir +
                ", name='" + name + '\'' +
                ", size()='" + size() + '\'' +
                '}';
    }

    public static VFile of(String line) {
        VFile vFile = new VFile();

        String[] split = line.split(" ");
        vFile.dir = "dir".equals(split[0]);
        vFile.name = split[1];
        vFile.size = vFile.isDir() ? 0 : Integer.parseInt(split[0]);

        return vFile;
    }

}
