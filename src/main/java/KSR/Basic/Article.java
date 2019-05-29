package KSR.Basic;

import java.io.Serializable;
import java.util.ArrayList;

public class Article implements Serializable {
    public ArrayList<String> words;
    public ArrayList<String> tags;
    public ArrayList<String> title;

    public Article() {
    }

    public Article(ArrayList<String> tags) {
        this.tags = tags;
    }
}
