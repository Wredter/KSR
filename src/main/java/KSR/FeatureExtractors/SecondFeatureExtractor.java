package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SecondFeatureExtractor implements IFeatureExtractor {
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        // (1,"liczba tagów") - częstość wystąpienia słów kluczowych dla tagów w tekście
        // ("liczba tagów",2*"liczba tagów") - miara określajca powtarzanie sie poszczególnych słów kluczowych;
        Map<String, Map<String, Integer>> keyWordReplayMapByTag = new HashMap<>();
        ArrayList<Double> extractionResult = new ArrayList<>();
        ArrayList<Double> firstFeatureExtractionResult = new ArrayList<>();
        ArrayList<Double> secondFeatureExtractionResult = new ArrayList<>();
        for (int i = 0; i < keyWords.keySet().size(); i++) {
            firstFeatureExtractionResult.add(0d);
            secondFeatureExtractionResult.add(0d);
        }
        int tagIndex = 0;
        for (String tag : keyWords.keySet()) {
            keyWordReplayMapByTag.put(tag, new HashMap<>());
            for (String keyWord : keyWords.get(tag)) {
                keyWordReplayMapByTag.get(tag).put(keyWord, 0);
                for (String word : article.words) {
                    Double value = similarity.CalculateSimilarity(keyWord, word);
                    if (value != 0) {
                        firstFeatureExtractionResult.set(tagIndex, firstFeatureExtractionResult.get(tagIndex) + (1d / (double) article.words.size()));
                        keyWordReplayMapByTag.get(tag).replace(keyWord, keyWordReplayMapByTag.get(tag).get(keyWord) + 1);
                    }
                }
            }
            tagIndex++;
        }
        tagIndex = 0;
        for (String tag : keyWordReplayMapByTag.keySet()) {
            double tagValue = 1d;
            boolean flag = true;
            for (String keyWord : keyWordReplayMapByTag.get(tag).keySet()) {
                if (keyWordReplayMapByTag.get(tag).get(keyWord) != 0 && flag) {
                    flag = false;
                }
                for (int i = 1; i < keyWordReplayMapByTag.get(tag).get(keyWord); i++) {
                    tagValue /= 2d;
                }
            }
            if (flag) {
                tagValue = 0;
            }
            secondFeatureExtractionResult.set(tagIndex, tagValue);
            tagIndex++;
        }
        extractionResult.addAll(MyNormalize(firstFeatureExtractionResult));
        extractionResult.addAll(MyNormalize(secondFeatureExtractionResult));

        return extractionResult;
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
