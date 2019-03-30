package KSR.DataExtractor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DataExtarctor {
    public String linia;
    public DataExtarctor(){

    }
    public void readfromFile(){
        try {
            FileReader fileReader = new FileReader("D:\\Pobierane\\Studia\\sem VI\\KSR\\DANE\\reut2-000.sgm");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            linia = bufferedReader.readLine();

        }catch (FileNotFoundException e){
            System.out.println("Nie można otwożyć pliku");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
