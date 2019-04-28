package KSR.Basic;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Result {
    public String tag;
    public Integer all;
    public Integer tp;
    public Integer tn;
    public String tpPercentage;
    public String recall;
    public String precision;

    public Result(String tag) {
        this.tag = tag;
        all = 0;
        tp = 0;
        tn = 0;
    }

    public void incAll() {
        this.all += 1;
    }

    public void incTp() {
        this.tp += 1;
    }

    public void incTn() {
        this.tn += 1;
    }

    public void calculateTpPercentage() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = (double) this.tp / this.all * 100.0;

        this.tpPercentage = df.format(temp) + "%";
    }

    public void calculateRecallValue() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = (double) this.tp / this.all * 100.0;

        this.recall = df.format(temp) + "%";
    }

    public void calculatePrecisionValue() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = (double) this.tp / (this.tp + this.tn) * 100.0;

        this.precision = df.format(temp) + "%";
    }
}
