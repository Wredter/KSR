package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class DictionaryMatchingFeatureExtractor implements IFeatureExtractor {

    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        ArrayList<String> keys = keyWords.get(article.tags.get(0));
        ArrayList<Double> result = new ArrayList<>();
        for (String key : keys) {
            Double count = 0.0;
            for (String word : article.words) {
                if (key.equals(word)) {
                    count += 1.0;
                }
            }
            result.add(count);
        }

        return result;
    }
}
