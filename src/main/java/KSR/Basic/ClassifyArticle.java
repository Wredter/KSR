package KSR.Basic;

import java.util.ArrayList;
import java.util.Map;

public class ClassifyArticle {
    public String tag;
    public String predictedTag;
    public ArrayList<Double> featuresVector;

    public ClassifyArticle(String tag, String predictedTag, ArrayList<Double> featuresVector) {
        this.tag = tag;
        this.predictedTag = predictedTag;
        this.featuresVector = featuresVector;
    }
}
