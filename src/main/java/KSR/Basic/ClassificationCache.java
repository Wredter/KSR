package KSR.Basic;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassificationCache implements Serializable {
    public String metric;
    public Integer paramK;
    public Integer amount;
    public String similarity;
    public String featureVector;
    public ArrayList<Result> classificationResults;


    public ClassificationCache() {
        classificationResults = new ArrayList<>();

    }

    public ClassificationCache(String m, Integer k, Integer a, String s, String f) {
        metric = m;
        paramK = k;
        amount = a;
        similarity = s;
        featureVector = f;
        classificationResults = new ArrayList<>();

    }


    public void setResult(ArrayList<Result> results) {
        classificationResults = results;
    }

    public void SetCache(String m, Integer k, Integer a, String s, String f) {
        metric = m;
        paramK = k;
        amount = a;
        similarity = s;
        featureVector = f;
    }

    public Boolean IsSameParameters(String m, Integer k, Integer a, String s, String f) {
        Boolean isSame = true;
        if(!metric.equals(m)) {
            isSame = false;
        }
        if(paramK != k) {
            isSame = false;
        }
        if(amount != a) {
            isSame = false;
        }
        if(!similarity.equals(s)) {
            isSame = false;
        }
        if(!featureVector.equals(f)) {
            isSame = false;
        }

        return  isSame;
    }

    @Override
    public String toString() {
        return "ClassificationCache{" +
                "metric='" + metric + '\'' +
                ", paramK=" + paramK +
                ", amount=" + amount +
                ", similarity='" + similarity + '\'' +
                ", featureVector='" + featureVector + '\'' +
                '}';
    }


}
