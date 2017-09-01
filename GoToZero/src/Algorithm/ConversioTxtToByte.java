package Algorithm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ConversioTxtToByte {
    int m;

    public ConversioTxtToByte(){
        m = 0;

    }


    public static void main(String[] args) {
        //convert("src/gotozero/txt/-34");

        (new ConversioTxtToByte()).convertTextToImage("src/gotozero/txt/-34");
    }

    private static void convert(String rute){
        ArrayList<Integer> datos = new ArrayList<>();

        try {
            File aux = new File(rute + ".txt");
            if(aux.exists()){
                Scanner bf = new Scanner(aux);
                while (bf.hasNextLine()) {
                    if(bf.hasNextInt()) {
                        datos.add(bf.nextInt());
                        //System.out.println("Se ha leido el Numero: " + bf.nextInt());
                    } else {
                        bf.nextLine();
                        //System.out.println("Salto de linea!!!!!" + bf.nextLine());
                        datos.add(-bf.nextLine().hashCode());
                    }
                }
                datos.add(Integer.MIN_VALUE);

                System.out.println("FicheroLeido");
                bf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        DataOutputStream salida = null;
        int i = 0;
        try {
            fos = new FileOutputStream(rute + ".dat");
            salida = new DataOutputStream(fos);
            int n;
            n = datos.get(i);
            while (n != Integer.MIN_VALUE) {
                n = datos.get(i);
                salida.writeInt(n); //se escribe el número entero en el fichero
                ++i;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadToResources(){
        DataInputStream dataIn = new DataInputStream(ConversioTxtToByte.class.getClassLoader().getResourceAsStream("/txt/" + -34 + ".dat"));

        try {
            while(dataIn.available()>0) {
                String k = dataIn.readUTF();
                System.out.print(k+" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void convertTextToImage(String rute){
        ArrayList<Integer> datos = new ArrayList<>();
        try {
            File aux = new File(rute + ".txt");
            if(aux.exists()){
                Scanner bf = new Scanner(aux);
                while (bf.hasNextLine()) {
                    if(bf.hasNextInt()) {
                        datos.add(bf.nextInt());
                        //System.out.println("Se ha leido el Numero: " + bf.nextInt());
                    } else {
                        bf.nextLine();
                        //System.out.println("Salto de linea!!!!!" + bf.nextLine());
                        datos.add(255);
                    }
                }
                datos.add(Integer.MIN_VALUE);

                System.out.println("FicheroLeido");
                bf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage outputImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        File outputfile = new File(rute + ".png");

        /*
            int r = // red component 0...255
            int g = // green component 0...255
            int b = // blue component 0...255
            int col = (r << 16) | (g << 8) | b;
            img.setRGB(x, y, col);
         */
        int i = 0,x = 0, y = 0;
        while (i < datos.size() && i < (100 * 100)) {
            System.out.println(((255 << 16)|(255 << 8)|datos.get(i)));
            outputImage.setRGB(x,y,(((255 << 24)|255 << 16)|(255 << 8)|datos.get(i)));
            ++x;
            if(x == 100) {
                x = 0;
                ++y;
                if(y == 100) {
                    y = 0;
                }
            }
            ++i;
        }

        try {
            ImageIO.write(outputImage, "png", outputfile);
            System.out.println("Imagen terminada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
