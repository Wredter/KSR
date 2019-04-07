package KSR.Basic;

import java.util.ArrayList;
import java.util.Map;

public class ClassifyArticle {
    public String tag;
    public String predictedTag;
    public Map<String, Double> featuresVector;
    public ArrayList<Double> featureValueVector;

    public ClassifyArticle(String tag, String predictedTag, Map<String, Double> featuresVector) {
        this.tag = tag;
        this.predictedTag = predictedTag;
        this.featuresVector = featuresVector;

        for(Map.Entry feature : featuresVector.entrySet()) {
            featureValueVector.add((Double) feature.getValue());
        }
    }
}
