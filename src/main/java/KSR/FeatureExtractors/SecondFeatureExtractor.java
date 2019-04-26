package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class SecondFeatureExtractor implements IFeatureExtractor{
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        // (1,"liczba tagów") - częstość wystąpienia słów kluczowych dla tagów w tekście
        ArrayList<Double> extractionResult = new ArrayList<>();
        for(int i = 0;i < keyWords.keySet().size(); i++){
            extractionResult.add(0d);
        }
        int tagIndex = 0;
        for(String tag : keyWords.keySet()){
            for(String keyWord : keyWords.get(tag)){
                for(String word : article.words){
                    Double value = similarity.CalculateSimilarity(keyWord,word);
                    if(value != 0){
                        extractionResult.set(tagIndex,extractionResult.get(tagIndex)+(1/article.words.size()));
                    }
                }
            }
            tagIndex ++;
        }




        return extractionResult;
    }
}
