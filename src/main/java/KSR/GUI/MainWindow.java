package KSR.GUI;

import KSR.GUI.Controller.MainController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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

    private JButton button2;
    private JButton button4;


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
                mainController.ReadFile();
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.FilterFile(categoryTextField, tagsTextField);
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
                Integer treningNum = Integer.parseInt(treningDataTextField.getText());
                Integer testNum = Integer.parseInt(testDataTextField.getText());
                if(treningNum >= 100 || testNum >= 100) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowa wartość.");
                    return;
                }
                else if((treningNum + testNum) != 100) {
                    testNum = 100 - treningNum;
                    testDataTextField.setText(String.valueOf(testNum));
                    return;
                }

                mainController.PrepareArticles(treningNum);

            }
        });

        trainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedExtractor = (String) extractionMethodCcomboBox.getSelectedItem();
                if(selectedExtractor == "liczby słów") {
                    selectedExtractor = "WordsNumber";
                } else {
                    selectedExtractor = "WordsFrequency";
                }
               mainController.Train(keyWordsNumTextField, selectedExtractor);
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
        //DataLeftPanel.setBackground(Color.getHSBColor(50, 50, 50));
        DataPanel.add(DataLeftPanel);
        //DataRightPanel.setBackground(Color.getHSBColor(50, 50, 50));
        DataPanel.add(DataRightPanel);
        LeftPanel.add(DataPanel);

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Trening");
        TeachPanel.setBorder(title);
        LeftPanel.add(TeachPanel);

        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Klasyfikacja");
        ClassificationPanel.setBorder(title);
        LeftPanel.add(ClassificationPanel);

        extractionMethodCcomboBox.addItem("liczby słów");
        extractionMethodCcomboBox.addItem("częstotliwości występowania słów");

    }

    public void CreateRightPanelStructure() {
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Wynik");
        RightPanel.setLayout(new GridLayout(1, 1));
        RightPanel.setBorder(title);
    }

}
