package KSR;

import KSR.DataExtractor.DataExtarctor;
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DataExtarctor dataExtarctor = new DataExtarctor();
        dataExtarctor.readfromFile("PLACES");
        System.out.println("Koniec");

    }
}
