package KSR.Similarities;

public class BinarySimilarity implements  ISimilarity {

    @Override
    public Double CalculateSimilarity(String word1, String word2) {
       if(word1.equals(word2)) {
           return 1.0;
       }
       return 0.0;
    }
}