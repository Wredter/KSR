package KSR.GUI.Controller;

import KSR.Basic.KeyWord;
import KSR.Basic.PreparedArticle;
import KSR.Classification.KNNService;
import KSR.DataOperations.ArticleOperation;
import KSR.DataOperations.DataExtarctor;
import KSR.FeatureExtractors.DictionaryMatchingFeatureExtractor;
import KSR.FeatureExtractors.IFeatureExtractor;
import KSR.FeatureExtractors.PositionFeatureExtractor;
import KSR.FeatureExtractors.QuantityFeatureExtractor;
import KSR.Features.FeaturesService;
import KSR.Features.TrainingService;
import KSR.GUI.Model.DataContext;
import KSR.Metrics.ChebyshevMetric;
import KSR.Metrics.EuclideanMetric;
import KSR.Metrics.IMetric;
import KSR.Metrics.TaximanMetric;
import KSR.Similarities.BinarySimilarity;
import KSR.Similarities.ISimilarity;
import KSR.Similarities.NGramSimilarity;

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
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.StrictMath.abs;

public class MainController {
    public DataContext dataContext;
    private ArticleOperation articleOperation;
    private KNNService knnService;

    public MainController() {
        dataContext = new DataContext();
        articleOperation = new ArticleOperation();
    }

    public void HardReset() {
        DataContext newDataContext = new DataContext();
        dataContext = newDataContext;
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

    public void ReadMultipleFiles() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SGM FILES", "sgm");
        fileChooser.setFileFilter(filter);
        int retV = fileChooser.showOpenDialog(null);
        if (retV == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
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
        for (String path : dataContext.filePaths) {
            dataExtarctor.readfromFile(dataContext.selectedCategory, path);
            dataContext.rawArticles.addAll(dataExtarctor.articles);
        }

        dataContext.rawArticles = ArticleOperation.Filter(dataContext.rawArticles, dataContext.selectedTags);
    }

    public void DivideArticles(Integer treningNum) {
        dataContext.treningArticles = new ArrayList<>();
        dataContext.testArticles = new ArrayList<>();

        treningNum = (dataContext.rawArticles.size() * treningNum / 100);

        for (int i = 0; i < treningNum; i++) {
            if (dataContext.rawArticles.get(i).tags.size() == 1) {
                dataContext.treningArticles.add(new PreparedArticle(dataContext.rawArticles.get(i), dataContext.rawArticles.get(i).tags));
            }
        }

        for (int i = treningNum; i < dataContext.rawArticles.size(); i++) {
            if (dataContext.rawArticles.get(i).tags.size() == 1) {
                dataContext.testArticles.add(new PreparedArticle(dataContext.rawArticles.get(i), dataContext.rawArticles.get(i).tags));
            }
        }
    }


    public void GenerateStopList() {
        dataContext.stopList = articleOperation.GenerateStopList(dataContext.treningArticles, 0.05);
    }

    public void PrepareArticles() {
        for (PreparedArticle art : dataContext.treningArticles) {
            art.words = articleOperation.Prepare(art.words, dataContext.stopList);
        }
        for (PreparedArticle art : dataContext.testArticles) {
            art.words = articleOperation.Prepare(art.words, dataContext.stopList);
        }
    }

    public void Train(JTextField KeyWordsNumTextField, String selectedExtractor) {
        Integer keyWordsCount = Integer.parseInt(KeyWordsNumTextField.getText());
        TrainingService trainingService = new TrainingService(dataContext.selectedCategory, dataContext.selectedTags, dataContext.treningArticles, selectedExtractor, keyWordsCount);
        trainingService.Train();

        for (String tag : dataContext.selectedTags) {
            for (String word : trainingService.keyWords.get(tag)) {
                dataContext.keyWords.add(new KeyWord(tag, word));
            }
        }

        dataContext.keyWordsMap = trainingService.keyWords;
    }

    public DefaultTableModel CreateKeyWordsTable() {
        JTable keyWordsTable = new JTable();
        DefaultTableModel rowModel = new DefaultTableModel(new String[]{"No.", "Tag", "Słowo kluczowe"}, 0);

        keyWordsTable.setModel(rowModel);

        for (int i = 0; i < dataContext.keyWords.size(); i++) {
            rowModel.addRow(
                    new Object[]{
                            i + 1,
                            dataContext.keyWords.get(i).tag,
                            dataContext.keyWords.get(i).word
                    }
            );
        }

        return rowModel;
    }

    public void Classify(String metric, String similarity, String k, String amountOfStartData, String extractionMethod) {
        IMetric selectedMetric;
        ISimilarity selectedSimilarity;
        Integer paramK = Integer.parseInt(k);
        Integer amount = Integer.parseInt(amountOfStartData);
        ArrayList<IFeatureExtractor> featureExtractors;
        Random rand = new Random();

        if (metric == "Czebyszewa") {
            selectedMetric = new ChebyshevMetric();
        } else if (metric == "Euklidesowa") {
            selectedMetric = new EuclideanMetric();
        } else {
            selectedMetric = new TaximanMetric();
        }

        if (similarity == "Binarna") {
            selectedSimilarity = new BinarySimilarity();
        } else {
            selectedSimilarity = new NGramSimilarity();
        }

        if (extractionMethod == "DM") {
            featureExtractors = PrepareDMFeatureExtractors();
        } else {
            featureExtractors = PrepareOwnFeatureExtractors();

        }

        FeaturesService featuresService = new FeaturesService(dataContext.keyWordsMap, selectedSimilarity, featureExtractors);
        knnService = new KNNService(featuresService, selectedMetric, paramK);

        // Prepate to "Cold Start"
        ArrayList<PreparedArticle> coldArticles = new ArrayList<>();
        for (String tag : dataContext.selectedTags) {
            ArrayList<Integer> indexs = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                Integer randIndex;
                Integer iterator = 0;
                while(true) {
                    iterator++;
                    randIndex = abs(rand.nextInt() % dataContext.testArticles.size());
                    if(!indexs.contains(randIndex)) {
                        indexs.add(randIndex);
                        break;
                    }
                    if(iterator > 100) {
                        break;
                    }
                }
                coldArticles.add(dataContext.testArticles.get(randIndex));
            }
            for (PreparedArticle art : coldArticles) {
                if(dataContext.testArticles.contains(art)) {
                    dataContext.testArticles.remove(art);
                }
            }
        }

        knnService.InitKnn(coldArticles);

        Integer all = 0;
        Integer tp = 0;
        Integer tn = 0;

        // Start classification
        for (PreparedArticle art : dataContext.testArticles) {
            String predictedTag = knnService.ClassifyArticle(art);
            for (String tag : dataContext.selectedTags) {
                if (art.tags.get(0).equals(tag)) {
                    all++;
                    if (predictedTag.equals(tag)) {
                        tp++;
                    }
                } else {
                    if (predictedTag.equals(tag)) {
                        tn++;
                    }
                }
            }
        }

        String x = "";
    }

    // Select which features extractors will be used
    public ArrayList<IFeatureExtractor> PrepareDMFeatureExtractors() {
        ArrayList<IFeatureExtractor> result = new ArrayList<>();

        result.add(new DictionaryMatchingFeatureExtractor());

        return result;
    }

    public ArrayList<IFeatureExtractor> PrepareOwnFeatureExtractors() {
        ArrayList<IFeatureExtractor> result = new ArrayList<>();

        result.add(new PositionFeatureExtractor());
        result.add(new QuantityFeatureExtractor());


        return result;
    }

}
