package be.neonweb.aoc.solvers.d20;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bloc {

    private Long value;

    public static Bloc of(String s) {
        return of(s, 1);
    }

    public static Bloc of(String s, long factor) {
        Bloc bloc = new Bloc();
        bloc.value = Long.parseLong(s) * factor;
        return bloc;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
