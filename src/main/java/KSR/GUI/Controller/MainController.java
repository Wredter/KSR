package KSR.GUI.Controller;

import KSR.DataOperations.ArticleOperation;
import KSR.DataOperations.DataExtarctor;
import KSR.GUI.Model.DataContext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

public class MainController {
    public DataContext dataContext;

    public MainController() {
        dataContext = new DataContext();
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
            JOptionPane.showMessageDialog(null, "Wystąpił problem z plikiem");
        }
    }

    public void FilterFile(JTextField category, JTextField tags) {
        if(category.getText().length() < 1 || category.getText() == null){
            JOptionPane.showMessageDialog(null, "Nieprawidłowa kategoria");
            return;
        }
        dataContext.selectedCategory = category.getText();

        if(tags.getText().length() < 1 || tags.getText() == null){
            JOptionPane.showMessageDialog(null, "Nieprawidłowe tagi");
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


}
