package KSR.Metrics;

import java.util.ArrayList;

public class TaximanMetric implements IMetric {

    // Source
    // https://zasoby1.open.agh.edu.pl/dydaktyka/matematyka/c_analiza_matematyczna/wyklady/Wyklad8.htm
    @Override
    public Double CalculateDistance(ArrayList<Double> A, ArrayList<Double> B) {
        Double result = 0.0;

        for (int i = 0; i < A.size(); i++)
        {
            result += Math.abs(A.get(i) - B.get(i));
        }

        return result;
    }
}
