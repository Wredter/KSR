package KSR.Metrics;

import java.util.ArrayList;
import java.lang.Math;

public class EuclideanMetric implements IMetric {

    // Source:
    // https://pl.wikipedia.org/wiki/Odleg%C5%82o%C5%9B%C4%87
    @Override
    public Double CalculateDistance(ArrayList<Double> A, ArrayList<Double> B) {
        Double result = 0.0;

        for (int i = 0; i < A.size(); i++)
        {
            result += Math.pow((A.get(i) - B.get(i)), 2);
        }

        return Math.sqrt(result);
    }
}
