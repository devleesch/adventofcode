package be.neonweb.adventofcode;

import be.neonweb.adventofcode.d17.D17;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        Day d = new D17();

        loggerContext.getLoggerList()
                     .forEach(logger -> logger.setLevel(Level.WARN));

        loggerContext.getLoggerList()
                     .stream()
                     .filter(logger -> logger.getName()
                                             .startsWith("be.neonweb.adventofcode"))
                     .forEach(logger -> logger.setLevel(Level.INFO));

        d.run("test2.txt");
    }
}
