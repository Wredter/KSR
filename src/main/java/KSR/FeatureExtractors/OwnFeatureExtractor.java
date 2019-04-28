package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.BinarySimilarity;
import KSR.Similarities.ISimilarity;
import KSR.Similarities.NGramSimilarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class OwnFeatureExtractor implements IFeatureExtractor {
    // Opis wektora
    // 1. (rozmiar: liczba tagów, pozycja początkowa: 0 * liczba tagów) - ilość słów kluczowych w pierwszej połowie tekstu
    // 2. (rozmiar: liczba tagów, pozycja początkowa: 1 * liczba tagów) - ilość słów kluczowych w całym tekscie
    // 3. (rozmiar: liczba tagów, pozycja początkowa: 2 * liczba tagów) - suma wartości binarnej miary podobieństwa słów kluczowych i słów z artykułu w pierwszych 40% artykułu z uwzględnieniem współczynnika równego 2
    // 4. (rozmiar: liczba tagów, pozycja początkowa: 3 * liczba tagów) - suma wartości binarnej miary podobieństwa słów kluczowych i słów z artykułu w 100% artykułu
    // 5. (rozmiar: liczba tagów, pozycja początkowa: 4 * liczba tagów) - suma wartości miary podobieństwa n-gram słów kluczowych i słów z artykułu w pierwszych 40% artykułu z uwzględnieniem współczynnika równego 2
    // 6. (rozmiar: liczba tagów, pozycja początkowa: 5 * liczba tagów) - suma wartości miary podobieństwa n-gram słów kluczowych i słów z artykułu w 100% artykułu
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        ArrayList<Double> featureVector = new ArrayList<>();
        ISimilarity binarySimilarity = new BinarySimilarity();
        ISimilarity nGramSimilatiry = new NGramSimilarity();
        SumOfSimilarity sumOfSimilarity = new SumOfSimilarity();

        // Quantity Feature Extractor (2 vectors)
        IFeatureExtractor quantityFeature = new QuantityFeatureExtractor();
        featureVector.addAll(MyNormalize((ArrayList<Double>) quantityFeature.CalculateFeatureValue(article, keyWords, binarySimilarity)));

        // Sum of similarity for Binary Similarity for 40% of collection
        featureVector.addAll(MyNormalize((ArrayList<Double>) sumOfSimilarity.CalculateFeatureValue(article, keyWords, binarySimilarity, 0.4, 2)));

        // Sum of similarity for Binary Similarity for 100% of collection
        featureVector.addAll(MyNormalize((ArrayList<Double>) sumOfSimilarity.CalculateFeatureValue(article, keyWords, binarySimilarity, 1.0, 1)));

        // Sum of similarity for N-gram Similarity for 40% of collection
        featureVector.addAll(MyNormalize((ArrayList<Double>) sumOfSimilarity.CalculateFeatureValue(article, keyWords, nGramSimilatiry, 0.4, 2)));

        // Sum of similarity for N-gram Similarity for 100% of collection
        featureVector.addAll(MyNormalize((ArrayList<Double>) sumOfSimilarity.CalculateFeatureValue(article, keyWords, nGramSimilatiry, 1.0, 1)));

        return featureVector;
    }

    private ArrayList<Double> MyNormalize(ArrayList<Double> input) {
        Double sum = 0d;
        ArrayList<Double> result = new ArrayList<>();
        for (Double value : input) {
            sum += value;
        }
        if (sum == 0) {
            return input;
        } else {
            for (Double value : input) {
                result.add(value / sum);
            }
            return result;
        }
    }
}
