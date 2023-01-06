package be.neonweb.aoc.solvers.d16;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class D16b extends D16a {

    @Override
    public Object solve() throws Exception {
        List<Valve> scoreValves = valves.values()
                                        .stream()
                                        .filter(valve -> valve.getFlowRate() > 0)
                                        .sorted(Comparator.comparing(Valve::getName))
                                        .toList();

        return explore2(new ArrayList<>(), new ArrayList<>(), scoreValves, 0);
    }

    int explore2(List<Valve> myPaths, List<Valve> ePath, List<Valve> valves, int max) {
        int myScore = score(myPaths, 26);
        int eScore = score(ePath, 26);
        int score = myScore + eScore;

        if (score >= 0 && !valves.isEmpty()) {
            for (int i = 0; i < valves.size() / 2; i++) {
                ArrayList<Valve> newValves = new ArrayList<>(valves);
                ArrayList<Valve> newMyPath = new ArrayList<>(myPaths);
                Valve myValve = valves.get(i * 2);
                newMyPath.add(myValve);
                newValves.remove(myValve);

                ArrayList<Valve> newEPath = new ArrayList<>(ePath);
                if (valves.size() > i * 2 + 1) {
                    Valve eValve = valves.get(i * 2 + 1);
                    newEPath.add(eValve);
                    newValves.remove(eValve);
                }
                max = explore2(newMyPath, newEPath, newValves, max);


                newValves = new ArrayList<>(valves);
                newEPath = new ArrayList<>(myPaths);
                Valve eValve = valves.get(i * 2);
                newEPath.add(eValve);
                newValves.remove(eValve);

                newEPath = new ArrayList<>(ePath);
                if (valves.size() < i * 2 + 1) {
                    myValve = valves.get(i * 2 + 1);
                    newMyPath.add(myValve);
                }
                max = explore2(newMyPath, newEPath, newValves, max);
            }
        }

        return Math.max(score, max);
    }

}