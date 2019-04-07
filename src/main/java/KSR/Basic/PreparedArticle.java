package KSR.Basic;

import KSR.DataOperations.ArticleOperation;

import java.util.ArrayList;

public class PreparedArticle {
    public ArrayList<String> words;
    public ArrayList<String> tags;

    public PreparedArticle(Article article, ArrayList<String> listOfTags) {
        this.words = new ArrayList<>();
        this.words = article.words;

        this.tags = new ArrayList<>();
        this.tags = listOfTags;
    }


}
