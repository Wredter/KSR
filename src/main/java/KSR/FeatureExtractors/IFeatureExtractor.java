package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IFeatureExtractor {
    public Collection<Double> ExtractFeatures(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity);

    static Collection<Double> Normalize(Map<String, Double> featureVector) {
        Set<String> tags = featureVector.keySet();
        Double sum = 0d;
        for (String tag : tags) {
            sum += featureVector.get(tag);
        }
        if(sum<=0){
            System.out.println("Nie da się zaklasyfikować");
            return featureVector.values();

        }else{
            for (String tag : tags) {
                featureVector.replace(tag, (featureVector.get(tag) / sum));
            }
            return featureVector.values();
        }


    }
}
