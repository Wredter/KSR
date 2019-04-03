package KSR.Features;

import KSR.Basic.PreparedArticle;

import java.util.*;
import java.util.stream.Collectors;

public class TrainingService {
    public String category;
    public ArrayList<String> tags;
    public ArrayList<PreparedArticle> articles;
    public Map<String, ArrayList<String>> keyWords;

    public TrainingService(String category, ArrayList<String> tags, ArrayList<PreparedArticle> articles) {
        this.category = category;
        this.tags = tags;
        this.articles = articles;
        this.keyWords = new HashMap<>();

        for (String tag : this.tags) {
            keyWords.put(tag, new ArrayList<>());
        }
    }

    public void Train(Integer keyWordsCount) {
        Map<String, ArrayList<String>> keyWords = new HashMap<>();

        for (String tag : tags) {
            ArrayList<PreparedArticle> filteredArticles = new ArrayList<>();
                    filteredArticles = this.articles.stream().parallel()
                    .filter(t -> t.tags.contains(tag))
                    .collect(Collectors.toCollection(ArrayList::new));

            Map<String, Integer> wordsNumber = this.CalculateWordsNumber(filteredArticles);

            ArrayList<String> sortedWords = new ArrayList<>();
            wordsNumber.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(t -> sortedWords.add(t.getKey()));

            keyWords.put(tag, sortedWords);
        }

        for (String tag : tags) {
            ArrayList<String> oldValue = keyWords.get(tag);
            ArrayList<String> oldMapObj = keyWords.replace(tag, oldValue.stream().parallel()
                    .limit(keyWordsCount)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    private Map<String, Integer> CalculateWordsNumber(ArrayList<PreparedArticle> selectedArticles) {
        Map<String, Integer> result = new HashMap<>();

        // NOTE: DISTINCT DONT WORK
        ArrayList<String> distinctWords = selectedArticles.stream()
                .parallel()
                .flatMap(t -> t.words.stream())
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        for (String word : distinctWords) {
            result.put(word, 0);
        }

        for (PreparedArticle article : selectedArticles) {
            for (String w : article.words) {
                // NOTE CHECK IT ++
                Integer oldValue = result.get(w);
                Integer oldMapObj = result.replace(w, oldValue++);
            }
        }

        return result;
    }
}
