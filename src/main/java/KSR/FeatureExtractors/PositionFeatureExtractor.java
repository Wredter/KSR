package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.*;

public class PositionFeatureExtractor implements IFeatureExtractor {
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        Map<String, Double> featureVector = new HashMap<>();
        Set<String> tags = keyWords.keySet();
        Double sim;
        int counter = article.words.size();
        for (String tag : tags) {
            featureVector.put(tag, 0d);
        }
        for (String word : article.words) {
            for (String tag : tags) {
                for (String keyWord : keyWords.get(tag)) {
                    sim = similarity.CalculateSimilarity(word, keyWord);
                    sim = sim * ((double) counter / (double) article.words.size());
                    featureVector.replace(tag, featureVector.get(tag) + sim);
                }
            }
            counter--;
        }

        return IFeatureExtractor.Normalize(featureVector);
    }

}
