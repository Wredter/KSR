package KSR.GUI.Model;

import KSR.Basic.Article;

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

    public DataContext() {
        filePath = "";
        selectedTags = new ArrayList<>();
        rawArticles = new ArrayList<>();
    }
}
