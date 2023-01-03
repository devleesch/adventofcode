package be.neonweb.aoc.solvers.d25;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class SNAFU {

    private String value;

    private List<Integer> digits = new ArrayList<>();

    private Double decimal;

    public static SNAFU of(String s) {
        SNAFU snafu = new SNAFU();
        snafu.value = s;
        for (String s1 : s.split("")) {
            if (s1.equals("=")) {
                snafu.digits.add(-2);
            } else if (s1.equals("-")) {
                snafu.digits.add(-1);
            } else {
                snafu.digits.add(Integer.parseInt(s1));
            }
        }
        return snafu;
    }

    public Double toDecimal() {
        int base = 5;
        if (decimal == null) {
            decimal = 0d;
            for (int i = 0; i < digits.size(); i++) {
                decimal += digits.get(i) * Math.pow(base, digits.size() - 1 - i);
            }
        }
        return decimal;
    }

    public static SNAFU fromDecimal(Double value) {
        int base = 5;
        List<Double> decomposition = new ArrayList<>();
        while (value >= base) {
            decomposition.add(value % base);
            value = (value - (value % base)) / base;
        }
        decomposition.add(value);

        int report = 0;
        List<String> res = new ArrayList<>();
        for (Double i : decomposition) {
            i += report;
            report = 0;

            if (i >= base) {
                i = i % base;
                report += 1;
            }

            if (i == 3) {
                report += 1;
                res.add("=");
            } else if (i == 4) {
                report += 1;
                res.add("-");
            } else {
                res.add(String.valueOf(i.intValue()));
            }
        }

        if (report > 0) {
            res.add(String.valueOf(report));
        }

        Collections.reverse(res);

        SNAFU snafu = new SNAFU();
        snafu.value = String.join("", res);

        return snafu;
    }

}
