package KSR;

import KSR.DataExtractor.DataExtarctor;
import KSR.Similarities.BinarySimilarity;
import KSR.Similarities.NGramSimilarity;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DataExtarctor dataExtarctor = new DataExtarctor();
        dataExtarctor.readfromFile("PLACES");
        System.out.println("Koniec");

        NGramSimilarity nGramSimilarity = new NGramSimilarity();
        Double s = nGramSimilarity.CalculateSimilarity("summary", "summarization");
        System.out.println(s);

        BinarySimilarity binarySimilarity = new BinarySimilarity();
        Double s1 = binarySimilarity.CalculateSimilarity("summary", "summary");
        System.out.println(s1);

    }
}
