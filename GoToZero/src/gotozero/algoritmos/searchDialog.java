package gotozero.algoritmos;

import java.io.*;
import java.util.Scanner;

/**
 * Created by saulc on 07/07/2017.
 */
public class searchDialog {



    public static String search(String NombreNPC, int lineaGuion) {
        switch(NombreNPC){

        }



        return "";
    }

    private static String loadGuionLine (String dirFile, int line){
        String res = "";
        try {
            Scanner resource = new Scanner(new File("/Kamina.TXT"));
            int i = 0;
            //while(i <= line && resource.hasNextLine()){
                res += resource.nextLine();
                //++i;
            //}
            resource.close();
            return res + 1;
        } catch (Exception e) {}

        return res;
    }

    public static void main(String[] args) {
        System.out.println(loadGuionLine("Kamina.txt",0));


    }

}
