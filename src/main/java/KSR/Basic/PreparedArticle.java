package KSR.Basic;

import KSR.DataOperations.ArticleOperation;

import java.util.ArrayList;

public class PreparedArticle {
    public ArrayList<String> words;
    public String tag;
    public ArrayList<String> title;

    public ArticleOperation operation = new ArticleOperation();

    public PreparedArticle(Article article, String tag, ArrayList<String> stopList) {
        this.tag = tag;
        words = operation.Prepare(article.words, stopList);
    }


}
