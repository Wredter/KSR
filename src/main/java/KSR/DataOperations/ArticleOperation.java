package KSR.DataOperations;

import KSR.Basic.Article;
//import org.tartarus.snowball.ext.PorterStemmer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ArticleOperation {

    public ArticleOperation() {

    }

    public static ArrayList<Article> Filter(ArrayList<Article> articles, ArrayList<String> tags) {
        ArrayList<Article> result = new ArrayList<>();

        for (Article article : articles) {
            for (String tag : tags) {
                boolean isContain = article.tags.contains(tag);
                if (isContain) {
                    result.add(article);
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<String> Prepare(ArrayList<String> words, ArrayList<String> stopList) {
        ArrayList<String> result;

        result = this.WordsToLowerCaseWords(words);
        result = this.RemoveNonLetterWords(result);
        result = this.RemoveWordsContainedInStopList(result, stopList);
        result = this.MakeWordsStamization(result);

        return result;
    }

    public ArrayList<String> WordsToLowerCaseWords(ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();
        for (String word : words) {
            result.add(word.toLowerCase());
        }
        return result;
    }

    public ArrayList<String> RemoveNonLetterWords(ArrayList<String> words) {
        return words
                .stream().parallel()
                .filter(word -> word.chars().allMatch(Character::isLetter))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> RemoveWordsContainedInStopList(ArrayList<String> words, ArrayList<String> stopList) {
        return words
                .stream().parallel()
                .filter(word -> !stopList.contains(word))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> MakeWordsStamization(ArrayList<String> words) {
        ArrayList<String> result = words;
//
//        PorterStemmer stemmer = new PorterStemmer();
//
//        for (String word : words) {
//            stemmer.setCurrent(word);
//            stemmer.stem();
//            result.add(stemmer.getCurrent());
//        }

        return result;
    }

    public ArrayList<String> GenerateStopList(ArrayList<Article> articles, Double occurancePercentage) {
        Map<String, Integer> stopLista = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();

        for (Article article : articles) {
            for (String word : article.words) {
                if (stopLista.containsKey(word)) {
                    stopLista.replace(word, stopLista.get(word) + 1);
                } else {
                    stopLista.put(word, 1);
                }
            }
        }
        Set<String> words = stopLista.keySet();
        for (String word : words) {
            if (stopLista.get(word) > articles.size() * occurancePercentage || stopLista.get(word) == 1) {
                result.add(word);
            }
        }
        try {
            FileReader fileReader = new FileReader(System.getProperty("user.dir")+"\\Data\\stopwords.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String stopWord;
            while ((stopWord = bufferedReader.readLine()) != null) {
                if(!result.contains(stopWord)){
                    result.add(stopWord);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Nie udało się otworzyć pliku");
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
