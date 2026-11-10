package be.neonweb.aoc2016;

import be.neonweb.aoc2016.d01.D01;
import be.neonweb.aoc2016.d02.D02;
import be.neonweb.aoc2016.d03.D03;
import be.neonweb.aoc2016.d04.D04;
import be.neonweb.aoc2016.d05.D05;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        AbstractDay d = new D05();
        d.run();
    }

}
