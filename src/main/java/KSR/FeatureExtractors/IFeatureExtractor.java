package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IFeatureExtractor {
    Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity);
}
