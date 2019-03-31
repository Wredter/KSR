package KSR.Metrics;

import java.util.ArrayList;
import java.util.Collections;

public class ChebyshevMetric implements IMetric {

    // Source:
    // https://pl.wikipedia.org/wiki/Odleg%C5%82o%C5%9B%C4%87_Czebyszewa
    @Override
    public Double CalculateDistance(ArrayList<Double> A, ArrayList<Double> B) {
        ArrayList<Double> series = new ArrayList<>();

        for(int i = 0; i < A.size(); i++)
        {
            series.add(Math.abs(A.get(i) - B.get(i)));
        }

        return Collections.max(series);
    }
}
