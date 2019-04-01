package KSR.DataOperations;

import KSR.Basic.Article;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ArticleOperation {

    public ArticleOperation() {

    }

    public static ArrayList<Article> Filter(ArrayList<Article> articles, ArrayList<String> tags) {
        ArrayList<Article> result = new ArrayList<>();

        for (Article article : articles) {
            for (String tag : tags) {
                boolean isContain = article.tags.contains(tag);
                if(isContain == true) {
                    result.add(article);
                    break;
                }
            }
        }
        return result;
    }

    public ArrayList<String> Prepare(ArrayList<String> words, ArrayList<String> stopList) {
        ArrayList<String> result;

        result = this.RemoveNonLetterWords(words);
        result = this.RemoveWordsContainedInStopList(result, stopList);
        result = this.MakeWordsStamization(result);

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

        // to do!!!!!

        return result;
    }
}
