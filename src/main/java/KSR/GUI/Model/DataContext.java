package KSR.GUI.Model;

import KSR.Basic.*;

import java.util.ArrayList;
import java.util.Map;

public class DataContext {
    // Path to selected file
    public String filePath;
    public ArrayList<String> filePaths;
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
    // List of key word from training articles
    public ArrayList<KeyWord> keyWords;
    public Map<String, ArrayList<String>> keyWordsMap;
    // List of classification results
    public ArrayList<Result> classificationResults;
    // Cache
    public ArrayList<ClassificationCache> caches;

    public DataContext() {
        filePath = "";
        selectedTags = new ArrayList<>();
        rawArticles = new ArrayList<>();
        keyWords = new ArrayList<>();
        filePaths = new ArrayList<>();
        classificationResults = new ArrayList<>();
        caches = new ArrayList<>();
        caches.add(new ClassificationCache("m", 0, 0, "s", "f"));
    }
}
