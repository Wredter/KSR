package KSR.DataOperations;

import KSR.Basic.Article;

import java.util.ArrayList;

public class ArticleOperation {

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
        ArrayList<String> result = new ArrayList<>();

        result = this.RemoveNonLetterWords(words);
        result = this.RemoveWordsContainedInStopList(words, stopList);
        result = this.MakeWordsStamization(words);

        return result;
    }

    public ArrayList<String> RemoveNonLetterWords(ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();



        return result;
    }

    public ArrayList<String> RemoveWordsContainedInStopList(ArrayList<String> words, ArrayList<String> stopList) {
        ArrayList<String> result = new ArrayList<>();



        return result;
    }

    public ArrayList<String> MakeWordsStamization(ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();



        return result;
    }
}
