package KSR.GUI.Model;

import KSR.Basic.Article;
import KSR.Basic.PreparedArticle;

import java.util.ArrayList;

public class DataContext {
    // Path to selected file
    public String filePath;
    // Catagory selected before filter
    public String selectedCategory;
    // Tags selected before filter
    public ArrayList<String> selectedTags;
    // List of "raw" articles after filtered
    public ArrayList<Article> rawArticles;
    // Stoplist, simple heh :)
    public ArrayList<String> stopList;
    // List of articles after divide and prepared
    public ArrayList<PreparedArticle> treningArticles = null;
    public ArrayList<PreparedArticle> testArticles = null;

    public DataContext() {
        filePath = "";
        selectedTags = new ArrayList<>();
        rawArticles = new ArrayList<>();
    }
}
