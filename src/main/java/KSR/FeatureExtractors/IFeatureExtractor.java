package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface IFeatureExtractor {
    public Map<String,Double>  ExtractFeatures(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity);
    static Map<String,Double> Normalize(Map<String,Double> featureVector){
        Set<String> tags = featureVector.keySet();
        Double sum = 0d;
        for(String tag : tags){
            sum += featureVector.get(tag);
        }
        for(String tag : tags){
            featureVector.replace(tag,(featureVector.get(tag)/sum));
        }
        return featureVector;

    }
}
