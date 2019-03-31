package KSR.DataExtractor;

import KSR.Basic.Tekst;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DataExtarctor {
    //public String linia;
    private ArrayList<Tekst> teksty;
    private String dataLocation;
    public DataExtarctor(){

        teksty = new ArrayList<>();
        dataLocation = System.getProperty("user.dir");
        dataLocation += "\\Data";
    }
    public void readfromFile(String tag){
        tag = "<"+tag+">";
        ArrayList<String> tagi;
        String tytul = "";
        String tresc = "";
        Tekst obecnyTekst = new Tekst();


        boolean flaga;
        try {
            String bufferline;

            FileReader fileReader = new FileReader(dataLocation + "\\reut2-000.sgm");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((bufferline = bufferedReader.readLine()) != null){
                if(bufferline.contains(tag)){
                    obecnyTekst = new Tekst();
                    tagi = new ArrayList<>();
                    do {
                        flaga = true;
                        int znacznikPoc = bufferline.indexOf("<");
                        int znacznikKon = bufferline.indexOf(">");
                        if (znacznikKon > znacznikPoc) {
                            if(znacznikKon + 1 >= bufferline.length()) {
                                flaga = false;
                            }else {
                                bufferline = bufferline.substring(znacznikKon);
                            }
                        }
                        if(flaga) {
                            znacznikPoc = bufferline.indexOf("<");
                            znacznikKon = bufferline.indexOf(">");
                            if (znacznikKon + 1 == znacznikPoc) {
                                bufferline = bufferline.substring(znacznikPoc);
                            } else {
                                tagi.add(bufferline.substring(znacznikKon + 1, znacznikPoc));
                                bufferline = bufferline.substring(znacznikPoc);
                            }
                        }
                    }while (flaga);
                    obecnyTekst.tagi = tagi;
                }
                if(bufferline.contains("<TITLE>")){
                    int znacznikkon = bufferline.indexOf(">");
                    int znacznikpoc;
                    bufferline = bufferline.substring(znacznikkon+1);
                    znacznikkon = bufferline.indexOf(">");
                    if(znacznikkon + 1 != bufferline.length()){
                        bufferline = bufferline.substring(znacznikkon+1);
                    }
                    if(bufferline.contains("</TITLE>")){
                        znacznikpoc = bufferline.indexOf("<");
                        tytul = bufferline.substring(0,znacznikpoc);
                    }else {
                        while(!bufferline.contains("</TITLE>")){
                            tytul = tytul + bufferline + " ";
                            bufferline = bufferedReader.readLine();
                        }
                        znacznikpoc = bufferline.indexOf("<");
                        tytul = tytul + bufferline.substring(0,znacznikpoc);
                    }
                    obecnyTekst.tytul = GetWordsFromSentence(tytul);
                }
                if(bufferline.contains("<BODY>")){
                    int znacznikkon = bufferline.indexOf("<BODY>");
                    int znacznikpoc;
                    bufferline = bufferline.substring(znacznikkon+6);
                    tresc = "";
                    while (!bufferline.contains("</BODY>")){
                        tresc = tresc + bufferline + " ";
                        bufferline = bufferedReader.readLine();
                    }
                    znacznikpoc = bufferline.indexOf("<");
                    tresc = tresc + bufferline.substring(0,znacznikpoc);
                    obecnyTekst.zdania = GetWordsFromSentence(tresc);
                }
                if(bufferline.contains("</REUTERS>")){
                    if(obecnyTekst.zdania == null){

                    }else{
                        teksty.add(obecnyTekst);
                    }
                }
            }
            bufferedReader.close();
        }catch (FileNotFoundException e){
            System.out.println("Nie można otwożyć pliku");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void splittest(){
        String s = "Ala ma kota ale, nie lubi bułek.";
        ArrayList<String> rez;
        rez = GetWordsFromSentence(s);
        System.out.println(rez);
    }
    private ArrayList<String> GetWordsFromSentence(String tekst){
        String[] pom;
        ArrayList<String> Rez = new ArrayList<>();
        pom = tekst.split(" ");
        Collections.addAll(Rez,pom);
        Rez.removeAll(Arrays.asList("", null));
        return Rez;
    }
}
