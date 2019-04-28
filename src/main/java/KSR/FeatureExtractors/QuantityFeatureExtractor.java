package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class QuantityFeatureExtractor implements IFeatureExtractor {
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        // 1. (rozmiar: liczba tagów, pozycja początkowa: 0) - ilość słów kluczowych w pierwszej połowie tekstu
        // 2. (rozmiar: liczba tagów, pozycja początkowa: 2 * liczba tagów) - ilość słów kluczowych w całym tekscie
        ArrayList<Double> featureVector = new ArrayList<>();

        for (int i = 0; i < keyWords.keySet().size() * 2; i++) {
            featureVector.add(0d);
        }

        int index = 0;
        for (String word : article.words) {
            int tagIndex = 0;
            for (String tagKey : keyWords.keySet()) {
                for (String keyword : keyWords.get(tagKey)) {
                    Double value;
                    value = similarity.CalculateSimilarity(word, keyword);
                    if (value != 0) {
                        if (index < article.words.size() / 2) {
                            featureVector.set(tagIndex, featureVector.get(tagIndex) + value);
                        } else if (index < article.words.size()) {
                            featureVector.set(tagIndex + keyWords.keySet().size(), featureVector.get(tagIndex + keyWords.keySet().size()) + (value));
                        }
                    }
                }
                tagIndex++;
            }
            index++;
        }
        return featureVector;
    }
}
