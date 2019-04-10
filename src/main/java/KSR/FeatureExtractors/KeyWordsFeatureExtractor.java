package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class KeyWordsFeatureExtractor implements IFeatureExtractor {
    @Override
    public Collection<Double> ExtractFeatures(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        return null;
    }

    @Override
    public Double CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords) {
        return null;
    }

}
