package be.neonweb.aoc.solvers.d10;

public class D10b extends D10a {

    @Override
    public Object solve() throws Exception {
        CPU cpu = new CPU();
        cpu.setOperations(operations);

        CRT crt = new CRT();
        cpu.run(crt);

        crt.getScreen()
           .forEach(System.out::println);

        return null;
    }
}
