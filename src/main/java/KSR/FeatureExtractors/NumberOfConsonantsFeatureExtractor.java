package KSR.FeatureExtractors;

import KSR.Basic.PreparedArticle;
import KSR.Similarities.ISimilarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class NumberOfConsonantsFeatureExtractor implements IFeatureExtractor{
    //Opis wektora
    //- 3 lub mniej spółgłoski
    //- 4 spółgłoski
    //- 5
    //- 6
    //- 7
    //- 8 lub więcej
    @Override
    public Collection<Double> CalculateFeatureValue(PreparedArticle article, Map<String, ArrayList<String>> keyWords, ISimilarity similarity) {
        ArrayList<String> samo = new ArrayList<>(Arrays.asList("a","e","i","o","u","y"));
        ArrayList<Double> result = new ArrayList<>(Arrays.asList(0d,0d,0d,0d,0d,0d));
        ArrayList<String> pom = new ArrayList<>(article.words);
        for(String word : pom){
            for(String letter : samo){
                if(word.contains(letter)){
                     word = word.replaceAll(letter,"");
                }
            }
            for(int i = 0; i < result.size();i++){
                if(word.length() == i+3){
                    result.set(i,result.get(i)+1);
                    break;
                }if(word.length() < 4 ){
                    result.set(0,result.get(0)+1);
                    break;
                }if(word.length() > 7){
                    result.set(5,result.get(5)+1);
                    break;
                }
            }
        }
        return result;
    }
}
