package KSR.View;

import KSR.View.Model.DataContext;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainWindow extends JFrame {

    public DataContext dataContext;

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

    private JPanel RightPanel;
    private JPanel TeachPanel;
    private JPanel ClassificationPanel;

    private JButton button2;
    private JButton button3;
    private JButton button4;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }

    public MainWindow() {
        super("Okno główne");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 720);
        setLocation(50, 50);
        setVisible(true);

        CreateMainPanelStructure();
        add(MainPanel);

        dataContext = new DataContext();

        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataContext.selectedCategory = categoryTextField.getText();
            }
        });

    }

    public void CreateMainPanelStructure() {
        MainPanel.setLayout(new GridLayout(1, 2));
        MainPanel.setBackground(Color.red);

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
    }

    public void CreateRightPanelStructure() {
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Wynik");
        RightPanel.setLayout(new GridLayout(1, 1));
        RightPanel.setBorder(title);
    }

}