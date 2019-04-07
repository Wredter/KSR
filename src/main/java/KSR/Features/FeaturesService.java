package KSR.Features;

import KSR.Basic.PreparedArticle;
import KSR.FeatureExtractors.IFeatureExtractor;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeaturesService {
    public Map<String, ArrayList<String>> keyWords;
    public ISimilarity similarity;
    public ArrayList<IFeatureExtractor> featureExtractors;

    public FeaturesService(Map<String, ArrayList<String>> keyWords, ISimilarity similarity, ArrayList<IFeatureExtractor> featureExtractors) {
        this.keyWords = keyWords;
        this.similarity = similarity;
        this.featureExtractors = featureExtractors;
    }

    public Map<String, Double> GetFeaturesVector(PreparedArticle article) {
        Map<String, Double> features = new HashMap<>();
        for (IFeatureExtractor featureExtractor : featureExtractors) {
            features.putAll(featureExtractor.ExtractFeatures(article, keyWords, similarity));
        }

        return features;
    }
}
