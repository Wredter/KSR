package KSR.Similarities;

public class NGramSimilarity implements ISimilarity {
    Integer n = 3;

    @Override
    public Double CalculateSimilarity(String word1, String word2) {
        String shortWord;
        String longWord;
        Double denominator;
        Double nominator = 1.0;
        Integer occurNum = 0;

        if (word1.length() < word2.length()) {
            shortWord = word1;
            longWord = word2;
        } else {
            shortWord = word2;
            longWord = word1;
        }

        if (longWord.length() - this.n + 1.0 >= 0) {
            denominator = longWord.length() - this.n + 1.0;

            for (int i = 0; i < denominator; i++) {
                if (word2.length() >= i + this.n) {
                    String nGram = word2.substring(i, i + this.n);
                    if (word1.contains(nGram)) {
                        occurNum++;
                    }
                }
            }

            return ((nominator / denominator) * occurNum);
        }
        return 0.0;
    }
}
