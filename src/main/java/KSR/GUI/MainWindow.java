package KSR.GUI;

import KSR.GUI.Controller.MainController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public MainController mainController;

    private JPanel MainPanel;

    private JPanel DataPanel;
    private JPanel LeftPanel;
    private JPanel DataLeftPanel;
    private JButton loadDataButton;
    private JTextField categoryTextField;
    private JTextField tagsTextField;
    private JButton filterButton;

    private JPanel DataRightPanel;
    private JButton loadStopListButton;
    private JTextField treningDataTextField;
    private JTextField testDataTextField;
    private JButton prepareButton;

    private JPanel TeachPanel;
    private JComboBox extractionMethodCcomboBox;
    private JTextField keyWordsNumTextField;
    private JButton trainButton;

    private JPanel ClassificationPanel;

    private JPanel RightPanel;

    private JPanel KeyWordPanel;
    private JTable keyWordsTable;

    private JPanel ClassificationResultPanel;

    private JButton button4;
    private JButton resetButton;
    private JButton divideArticlesButton;


    public MainWindow() {
        super("Okno główne");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 720);
        setLocation(50, 50);
        setVisible(true);

        CreateMainPanelStructure();
        add(MainPanel);

        mainController = new MainController();

        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.ReadMultipleFiles();//podmieniłem metodę na multifile
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.FilterMultipleFiles(categoryTextField, tagsTextField);//podmieniłem metodę na multifile
            }
        });

        divideArticlesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer treningNum = Integer.parseInt(treningDataTextField.getText());
                Integer testNum = Integer.parseInt(testDataTextField.getText());
                if (treningNum >= 100 || testNum >= 100) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowa wartość.");
                    return;
                } else if ((treningNum + testNum) != 100) {
                    testNum = 100 - treningNum;
                    testDataTextField.setText(String.valueOf(testNum));
                    return;
                }

                mainController.DivideArticles(treningNum);

            }
        });

        loadStopListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.GenerateStopList();
            }
        });

        prepareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.PrepareArticles();
            }
        });

        trainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedExtractor = (String) extractionMethodCcomboBox.getSelectedItem();
                if (selectedExtractor == "Liczby słów") {
                    selectedExtractor = "WordsNumber";
                } else if (selectedExtractor == "Częstotliwości występowania słów") {
                    selectedExtractor = "WordsFrequency";
                } else {
                    selectedExtractor = "WordPlacement";
                }
                mainController.Train(keyWordsNumTextField, selectedExtractor);

                keyWordsTable.setModel(mainController.CreateKeyWordsTable());
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.HardReset();
                categoryTextField.setText("");
                tagsTextField.setText("");
                treningDataTextField.setText("60");
                testDataTextField.setText("40");
                keyWordsNumTextField.setText("20");
                keyWordsTable.setModel(new DefaultTableModel());
            }
        });

    }

    public void CreateMainPanelStructure() {
        MainPanel.setLayout(new GridLayout(1, 2));

        CreateLeftPanelStructure();
        MainPanel.add(LeftPanel);

        CreateRightPanelStructure();
        MainPanel.add(RightPanel);
    }

    public void CreateLeftPanelStructure() {
        TitledBorder title;
        LeftPanel.setLayout(new GridLayout(3, 1));

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Dane");
        DataPanel.setBorder(title);
        DataPanel.setLayout(new GridLayout(1, 2, 10, 0));
        DataPanel.add(DataLeftPanel);
        DataPanel.add(DataRightPanel);
        LeftPanel.add(DataPanel);

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Trening");
        TeachPanel.setBorder(title);
        LeftPanel.add(TeachPanel);

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Klasyfikacja");
        ClassificationPanel.setBorder(title);
        LeftPanel.add(ClassificationPanel);

        extractionMethodCcomboBox.addItem("Liczby słów");
        extractionMethodCcomboBox.addItem("Częstotliwości występowania słów");
        extractionMethodCcomboBox.addItem("Umiejscowienia słów");

    }

    public void CreateRightPanelStructure() {
        TitledBorder title;
        RightPanel.setLayout(new GridLayout(1, 2));

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Słowa kluczowe");
        KeyWordPanel.setBorder(title);
        RightPanel.add(KeyWordPanel);

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Klasyfikacja");
        ClassificationResultPanel.setBorder(title);
        RightPanel.add(ClassificationResultPanel);
    }

}
