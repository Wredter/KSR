package KSR.Basic;

import java.util.ArrayList;

public class Article {
    public ArrayList<String> words;
    public ArrayList<String> tags;
    public ArrayList<String> title;

    public Article() {
    }

    public Article(ArrayList<String> tags) {
        this.tags = tags;
    }
}
