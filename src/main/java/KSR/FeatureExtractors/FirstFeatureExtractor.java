package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class FirstFeatureExtractor implements IFeatureExtractor{
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        // (1,"liczba tagów") - wystąpienia słów kluczowych dla tagów w pierwszej połowie tekstu
        // ("liczba tagów",2*"liczba tagów") - wystąpienia słów kluczowych dla tagów w drugiej połowie tekstu
        ArrayList<Double> featureVector = new ArrayList<>();
        int firstHalfValueMultiplayer = 4;
        for(int i = 0;i < keyWords.keySet().size()*2; i++){
            featureVector.add(0d);
        }
        int index = 0;
        for(String word : article.words) {
            int tagIndex = 0;
            for (String tagKey : keyWords.keySet()) {
                for (String keyword : keyWords.get(tagKey)){
                    Double value;
                    value = similarity.CalculateSimilarity(word,keyword);
                    if(value != 0){
                        if(index < article.words.size()/2){
                           featureVector.set(tagIndex,featureVector.get(tagIndex)+(value*firstHalfValueMultiplayer));
                        }else{
                            featureVector.set(tagIndex+keyWords.keySet().size(),featureVector.get(tagIndex+keyWords.keySet().size())+(value));
                        }
                    }
                }
                tagIndex ++;
            }
            index ++;
        }
        return featureVector;
    }
}
