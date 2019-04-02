package KSR.Basic;

import KSR.DataOperations.ArticleOperation;

import java.util.ArrayList;

public class PreparedArticle {
    public ArrayList<String> words;
    public ArrayList<String> tags;

    public ArticleOperation operation = new ArticleOperation();

    public PreparedArticle(Article article, ArrayList<String> listOfTags, ArrayList<String> stopList) {
        tags = new ArrayList<>();
        this.tags = listOfTags;
        words = operation.Prepare(article.words, stopList);
    }


}
