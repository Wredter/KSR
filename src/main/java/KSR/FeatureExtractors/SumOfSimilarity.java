package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class SumOfSimilarity {
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity, Double collectionPercentage, int multiplayer) {
        // 1. (rozmiar: liczba tagów, pozycja początkowa: 0) - ilość słów kluczowych w określonej części tekstu * współczynnik (jako bardziej znaczące)
        ArrayList<Double> featureVector = new ArrayList<>();

        for (int i = 0; i < keyWords.keySet().size(); i++) {
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
                        if (index < article.words.size() * collectionPercentage) {
                            featureVector.set(tagIndex, featureVector.get(tagIndex) + (value * multiplayer));
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
