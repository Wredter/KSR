package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class OwnFeatureExtractor implements IFeatureExtractor {
    //Opis wektora
    //- Ilość spółgłosek (3 i mniej)
    //- Ilość spółgłosek (4,5,6,7)
    //- Ilość spółgłosek  (8 i więcej)
    //- Ilość słów w artykule
    //- ilość liter (3 i mniej)
    //- Ilość liter (4,5,6,7)
    //- Ilość liter (8 i więcej)
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        ArrayList<String> samo = new ArrayList<>(Arrays.asList("a", "e", "i", "o", "u", "y"));
        ArrayList<Double> result = new ArrayList<>(Arrays.asList(0d, 0d, 0d, 0d, 0d, 0d, 0d));
        ArrayList<String> pom = new ArrayList<>(article.words);

        for (String word : pom) {
            for (String letter : samo) {
                if (word.contains(letter)) {
                    word = word.replaceAll(letter, "");
                }
            }

            // 0
            if (word.length() < 4) {
                result.set(0, result.get(0) + 1);
                continue;
            }

            // 1
            if (word.length() == 4 || word.length() == 5 || word.length() == 6 || word.length() == 7) {
                result.set(1, result.get(1) + 1);
                continue;
            }

            // 2
            if (word.length() > 7) {
                result.set(2, result.get(2) + 1);
                continue;
            }
        }

        // 3
        result.set(3, (double) article.words.size());

        for (String word : article.words) {
            // 4
            if (word.length() < 4) {
                result.set(4, result.get(4) + 1);
                continue;
            }

            // 5
            if (word.length() == 4 || word.length() == 5 || word.length() == 6 || word.length() == 7) {
                result.set(5, result.get(4) + 1);
                continue;
            }

            // 6
            if (word.length() > 7) {
                result.set(6, result.get(5) + 1);
                continue;
            }
        }

        return result;
    }
}
