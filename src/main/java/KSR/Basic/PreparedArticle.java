package KSR.Basic;

import KSR.DataOperations.ArticleOperation;

import java.io.Serializable;
import java.util.ArrayList;

public class PreparedArticle implements Serializable {
    public ArrayList<String> words;
    public ArrayList<String> tags;

    public PreparedArticle(Article article, ArrayList<String> listOfTags) {
        this.words = new ArrayList<>();
        this.words = article.words;

        this.tags = new ArrayList<>();
        this.tags = listOfTags;
    }


}
