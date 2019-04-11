package KSR.Basic;

public class Result {
    public String tag;
    public Integer all;
    public Integer tp;
    public Integer tn;

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
}
