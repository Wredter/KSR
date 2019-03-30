package KSR.Similarities;

public class NGramMethod implements  ISimilarity {
    Integer n = 3;

    @Override
    public Double CalculateSimilarity(String word1, String word2) {
        String shortWord;
        String longWord;
        Double denominator;
        Double nominator = 1.0;
        Integer occurNum = 0;

        if(word1.length() < word2.length())
        {
            shortWord = word1;
            longWord = word2;
        } else {
            shortWord = word2;
            longWord = word1;
        }

        denominator = 1.0 / (longWord.length() - this.n + 1.0);

        for(int i = 0; i < denominator; i++) {
            String nGram = word2.substring(i, n);
            if(word1.contains(nGram))
            {
                occurNum++;
            }
        }

        return ((nominator / denominator) * occurNum);
    }
}
