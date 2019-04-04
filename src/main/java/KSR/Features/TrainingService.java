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

    public void Train(Integer keyWordsCount, String selectedExtractor) {
        //Map<String, ArrayList<String>> keyWords = new HashMap<>();
        Map<String, Double> wordsMap = new HashMap<>();
        ArrayList<String> sortedWords = new ArrayList<>();

        for (String tag : tags) {
            ArrayList<PreparedArticle> filteredArticles = this.articles.stream().parallel()
                    .filter(t -> t.tags.contains(tag))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (selectedExtractor == "WordsNumber") {
                wordsMap = this.CalculateWordsNumber(filteredArticles);
            } else if (selectedExtractor == "WordsFrequency") {
                wordsMap = this.CalculateWordFrequency(filteredArticles);
            }

            wordsMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(t -> sortedWords.add(t.getKey()));

            this.keyWords.put(tag, sortedWords);
        }

        for (String tag : tags) {
            ArrayList<String> oldValue = this.keyWords.get(tag);
            ArrayList<String> oldMapObj = this.keyWords.replace(tag, oldValue.stream().parallel()
                    .limit(keyWordsCount)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

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

    private Map<String, Double> CalculateWordFrequency(ArrayList<PreparedArticle> selectedArticles) {
        Map<String, Double> distinctWords = new HashMap<>();
        Double docWordCount = 0.0;

        for (PreparedArticle article : selectedArticles) {
            for (String word : article.words) {
                if (distinctWords.containsKey(word)) {
                    distinctWords.put(word, distinctWords.get(word) + 1.0);
                } else {
                    distinctWords.put(word, 1.0);
                }
                docWordCount += 1.0;
            }
        }

        for (String key : new ArrayList<>(distinctWords.keySet())) {
            distinctWords.put(key, distinctWords.get(key) / docWordCount);
        }

        return distinctWords;
    }
}
