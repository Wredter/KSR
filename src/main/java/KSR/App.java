package KSR;

import KSR.DataOperations.DataExtarctor;
import KSR.GUI.MainWindow;
import KSR.Similarities.BinarySimilarity;
import KSR.Similarities.NGramSimilarity;

public class App
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }
}
