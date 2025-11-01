package be.neonweb.aoc2015;

import be.neonweb.aoc2015.d01.D01;
import be.neonweb.aoc2015.d02.D02;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws IOException {
        AbstractDay d = new D02();
        d.run();
    }

}
