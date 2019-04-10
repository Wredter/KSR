package KSR.DataOperations;

import KSR.Basic.Article;
import KSR.Basic.PreparedArticle;
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
        result = this.RemoveWordsContainedInStopList(result, stopList);

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
        PorterStemmer stemmer;
        ArrayList<String> stemmWords = new ArrayList<>();
        String stemmWord;

        for (String w : words) {
            stemmer = new PorterStemmer();
            stemmer.add(w.toCharArray(), w.length());
            stemmer.stem();
            stemmWord = stemmer.toString();
            if (stemmWord.length() > 1) {
                stemmWords.add(stemmWord);
            }
        }

        return stemmWords;
    }

    public ArrayList<String> GenerateStopList(ArrayList<PreparedArticle> articles, Double occurancePercentage) {
        Map<String, Integer> stopLista = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();

        for (PreparedArticle article : articles) {
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
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\Data\\stopwords.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String stopWord;
            while ((stopWord = bufferedReader.readLine()) != null) {
                if (!result.contains(stopWord)) {
                    result.add(stopWord);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się otworzyć pliku");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    // STWORZYC ALGORYTM DIRECTOR MATCHING
    private Map<String, Double> CalculateWordsNumber(ArrayList<PreparedArticle> selectedArticles) {
        Map<String, Double> result = new HashMap<>();
        ArrayList<String> allwords = new ArrayList<>();

        for (PreparedArticle article : selectedArticles) {
            for (String word : article.words) {
                allwords.add(word);
            }
        }

        ArrayList<String> distinctWords = allwords.stream()
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        for (String word : distinctWords) {
            result.put(word, 0.0);
        }

        for (PreparedArticle article : selectedArticles) {
            for (String w : article.words) {
                result.put(w, result.get(w) + 1.0);
            }
        }


        return result;
    }

    public ArrayList<String> GetMeaningulWordsFromArticle(PreparedArticle article) {
        ArrayList<String> result = new ArrayList<>();
        Map<String, Double> wordsMap = new HashMap<>();

        ArrayList<PreparedArticle> preparedArticles = new ArrayList<>();
        preparedArticles.add(article);

        wordsMap = this.CalculateWordsNumber(preparedArticles);

        wordsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(t -> result.add(t.getKey()));

        return result;
    }
    // STOP
}
