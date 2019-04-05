package KSR.GUI.Controller;

import KSR.Basic.KeyWord;
import KSR.Basic.PreparedArticle;
import KSR.DataOperations.ArticleOperation;
import KSR.DataOperations.DataExtarctor;
import KSR.Features.TrainingService;
import KSR.GUI.Model.DataContext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

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
    public void ReadMultipleFiles(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SGM FILES", "sgm");
        fileChooser.setFileFilter(filter);
        int retV = fileChooser.showOpenDialog(null);
        if(retV == JFileChooser.APPROVE_OPTION){
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for(File file : selectedFiles){
                dataContext.filePaths.add(file.getPath());
            }
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

    public void FilterMultipleFiles(JTextField category, JTextField tags) {
        if (dataContext.filePaths == null) {
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
        for(String path : dataContext.filePaths){
            dataExtarctor.readfromFile(dataContext.selectedCategory, path);
            dataContext.rawArticles.addAll(dataExtarctor.articles);
        }

        dataContext.rawArticles = ArticleOperation.Filter(dataContext.rawArticles, dataContext.selectedTags);
    }


    public void GenerateStopList() {
        dataContext.stopList = articleOperation.GenerateStopList(dataContext.rawArticles, 0.05);
    }

    public void PrepareArticles(Integer treningNum) {
        dataContext.treningArticles = new ArrayList<>();
        dataContext.testArticles = new ArrayList<>();

        treningNum = (dataContext.rawArticles.size() * treningNum / 100);

        for (int i = 0; i < treningNum; i++) {
            if (dataContext.rawArticles.get(i).tags.size() == 1) {
                dataContext.treningArticles.add(new PreparedArticle(dataContext.rawArticles.get(i), dataContext.rawArticles.get(i).tags, dataContext.stopList));
            }
        }

        for (int i = treningNum; i < dataContext.rawArticles.size(); i++) {
            if (dataContext.rawArticles.get(i).tags.size() == 1) {
                dataContext.testArticles.add(new PreparedArticle(dataContext.rawArticles.get(i), dataContext.rawArticles.get(i).tags, dataContext.stopList));
            }
        }
    }

    public void Train(JTextField KeyWordsNumTextField, String selectedExtractor) {
        TrainingService trainingService = new TrainingService(dataContext.selectedCategory, dataContext.selectedTags, dataContext.treningArticles);
        Integer keyWordsCount = Integer.parseInt(KeyWordsNumTextField.getText());
        trainingService.Train(keyWordsCount, selectedExtractor);

        for (String tag : dataContext.selectedTags) {
            for (String word : trainingService.keyWords.get(tag)) {
                dataContext.keyWords.add(new KeyWord(tag, word));
            }
        }

        // I THINK WE SHOULD PUT FEATURES EXTRACTION DEFINITION THERE -> KEY WORDS USAGE

    }

    public DefaultTableModel CreateKeyWordsTable() {
        JTable keyWordsTable = new JTable();
        DefaultTableModel rowModel = new DefaultTableModel(new String[]{"No.", "Tag", "Słowo kluczowe"}, 0);

        keyWordsTable.setModel(rowModel);

        for (int i = 0; i < dataContext.keyWords.size(); i++) {
            rowModel.addRow(
                    new Object[]{
                            i+1,
                            dataContext.keyWords.get(i).tag,
                            dataContext.keyWords.get(i).word
                    }
            );
        }

        return rowModel;
    }

    public void HardReset() {
        DataContext newDataContext = new DataContext();
        dataContext = newDataContext;
    }

}
