package KSR.GUI.Controller;

import KSR.Basic.PreparedArticle;
import KSR.DataOperations.ArticleOperation;
import KSR.DataOperations.DataExtarctor;
import KSR.GUI.Model.DataContext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

public class MainController {
    public DataContext dataContext;
    private ArticleOperation articleOperation;

    public MainController() {
        dataContext = new DataContext();
        articleOperation = new ArticleOperation();
    }

    public void ReadFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SGM FILES", "sgm");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            dataContext.filePath = selectedFile.getPath();
        } else {
            JOptionPane.showMessageDialog(null, "Wystąpił problem z plikiem.");
        }
    }

    public void FilterFile(JTextField category, JTextField tags) {
        if (dataContext.filePath == "") {
            JOptionPane.showMessageDialog(null, "Wybierz plik.");
            return;
        }

        if (category.getText().length() < 1 || category.getText() == null) {
            JOptionPane.showMessageDialog(null, "Nieprawidłowa kategoria.");
            return;
        }
        dataContext.selectedCategory = category.getText();

        if (tags.getText().length() < 1 || tags.getText() == null) {
            JOptionPane.showMessageDialog(null, "Nieprawidłowe tagi.");
            return;
        }

        String rawTags = tags.getText();
        String[] listOfTags = rawTags.split("\\s+");
        for (String t : listOfTags) {
            dataContext.selectedTags.add(t);
        }

        DataExtarctor dataExtarctor = new DataExtarctor();
        dataExtarctor.readfromFile(dataContext.selectedCategory, dataContext.filePath);
        dataContext.rawArticles = dataExtarctor.articles;

        dataContext.rawArticles = ArticleOperation.Filter(dataContext.rawArticles, dataContext.selectedTags);
    }

    public void GenerateStopList() {
        dataContext.stopList = articleOperation.GenerateStopList(dataContext.rawArticles, 0.2);
    }

    public void PrepareArticles(Integer treningNum) {
        dataContext.treningArticles = new ArrayList<>();
        dataContext.testArticles = new ArrayList<>();

        treningNum = (dataContext.rawArticles.size() * treningNum / 100);

        for (int i = 0; i < treningNum; i++) {
            dataContext.treningArticles.add(new PreparedArticle(dataContext.rawArticles.get(i), dataContext.selectedTags, dataContext.stopList));
        }

        for (int i = treningNum; i < dataContext.rawArticles.size(); i++) {
            dataContext.testArticles.add(new PreparedArticle(dataContext.rawArticles.get(i), dataContext.selectedTags, dataContext.stopList));
        }

        String x = "x";
    }


}
