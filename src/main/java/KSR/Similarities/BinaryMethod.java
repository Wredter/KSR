package KSR.Similarities;

public class BinaryMethod implements  ISimilarity {

    @Override
    public Double CalculateSimilarity(String word1, String word2) {
       if(word1 == word2) {
           return 1.0;
       }
       return 0.0;
    }
}