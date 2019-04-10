package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.*;

public class QuantityFeatureExtractor implements IFeatureExtractor {

    @Override
    public Collection<Double> ExtractFeatures(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        Map<String, Double> featureVector = new HashMap<>();
        Set<String> tags = keyWords.keySet();
        Double sim;
        for (String tag : tags) {
            featureVector.put(tag, 0d);
        }
        for (String word : article.words) {
            for (String tag : tags) {
                for (String keyWord : keyWords.get(tag)) {
                    sim = similarity.CalculateSimilarity(word, keyWord);
                    featureVector.replace(tag, featureVector.get(tag) + sim);
                }
            }
        }
        return IFeatureExtractor.Normalize(featureVector);
    }

    @Override
    public Double CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords) {
        return null;
    }

}
