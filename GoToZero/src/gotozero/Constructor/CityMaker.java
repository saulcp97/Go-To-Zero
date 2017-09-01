/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Constructor;

import gotozero.*;
import sun.misc.ObjectInputFilter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import static jdk.nashorn.internal.codegen.OptimisticTypesPersistence.load;

/**
 *
 * @author saulc
 */
public class CityMaker {

    public static CityMaker loader = new CityMaker();

    public CityMaker(){

    }

    public static v3 InitialPoint = new v3();
    
    public static void CrearCiudad(world w, int Type, int X, int Y, int Z){
        switch(Type) {
            case 1:
                Edify.addHouse(w, 0, X, Y, Z);
                break;       
        }
    }

    public void zonaTutorial(world w) {
        this.loadByFile(w, "-1");
/*
        w.resetMap();

        Edify.FillZone(5,1, 10, 25, 50, 65, 0, w);

        Edify.FillZone(5,1, 25, 70, 35, 100, 0, w);

        Edify.FillZone(5,1, 0, 105, 60, 165, 0, w);

        Edify.FillZone(5,1, -50, 115, 0, 120, 0, w);

        Edify.FillZone(5,1, -70, 110, -55, 125, 0, w);

        //Entrada a la zona de batalla con "Miedo"
        Edify.FillZone(5,1, 25, 170, 35, 175, 0, w);
        //Pasillo
            //Camino entrada al sementerio
        Edify.FillZone(5,1, -35, 140, -35, 185, 0, w);

        //
        Edify.FillZone(5,1, -35, 180, 40, 190, 0, w);

        Edify.FillZone(5,1, -20, 195, -10, 235, 0, w);

        Edify.FillZone(5,1, -5, 230, 0, 235, 0, w);

        //Sementerio
        Edify.FillZone(5,1, -60, 140, -40, 155, 0, w);


        //Sala Punteria
        Edify.FillZone(5,1, 5, 205, 55, 255, 0, w);

        //Sala inicial


        Edify.FillZone(5,1, 100, 185, 135, 205, 0, w); //inicio
            //Roca señaladora
            Block spawnZero = new Block(125,195,5,(byte)12);
            InitialPoint.setX(spawnZero.getX());
            InitialPoint.setY(spawnZero.getY());
        w.addToWorld(spawnZero);

        Edify.FillZone(5,1, 75, 195, 95, 195, 0, w);

        w.addToWorld(75,200,0,1);

        Edify.FillZone(5,1, 60, 205, 75, 205, 0, w);





        //Piso 2
        Edify.FillZone(5, 14, 5, 20, 55,20, 5, w);
        Edify.FillZone(5, 14, 5, 25, 5,70, 5, w);
        Edify.FillZone(5, 14, 55, 20, 55,70, 5, w);
        //Parte Inferior
        Edify.FillZone(5, 14, 10, 70, 20,70, 5, w);
        Edify.FillZone(5, 14, 40, 70, 55,70, 5, w);

        //pasillo salida gran sala
        Edify.FillZone(5, 14, 20, 75, 20,100, 5, w);
        Edify.FillZone(5, 14, 40, 75, 40,100, 5, w);

        //Paredes gran sala
        Edify.FillZone(5, 14, -5, 100, 15,100, 5, w);
        Edify.FillZone(5, 14, 45, 100, 65,100, 5, w);

        Edify.FillZone(5, 14, -5, 105, -5,110, 5, w);
        Edify.FillZone(5, 14, 65, 105, 65,170, 5, w);

        Edify.FillZone(5, 14, -5, 125, -5,170, 5, w);

        Edify.FillZone(5, 14, 0, 170, 20,170, 5, w);
        Edify.FillZone(5, 14, 40, 170, 60,170, 5, w);

        //Pasillo izquierdo
        Edify.FillZone(5, 14, -50, 110, -10,110, 5, w);
        Edify.FillZone(5, 14, -50, 125, -10,125, 5, w);

        //Ejemplo de roca señalizadora
        w.addToWorld(20,175,5,12);
        w.addToWorld(40,175,5,12);

        Edify.FillZone(5, 14, 45, 175, 45,190, 5, w);

        Edify.FillZone(5, 14, -5, 195, 70,200, 5, w);


        /*
        1,1,1,1,1, 1,1,1, 1,1,1,1,1
 */
    }

    private static Image load(String r){
        try {
            return ImageIO.read(sprite.class.getResource(r));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void loadByFile(world w, String str){
        int z = 0;
        int R=0;
        int G=0;
        int B=0;

        BufferedImage bufferPlano = (BufferedImage)(load("txt/" + str + ".png"));
        /*Recorro la imagen y voy mirando cada pixel*/
        for(int i=0; i < bufferPlano.getWidth(); ++i){
            for(int j=0; j < bufferPlano.getHeight(); ++j){
                try{
                    R = (bufferPlano.getRGB(i,j)>>16) & 255;
                    G = (bufferPlano.getRGB(i,j)>>8) & 255;
                    B = (bufferPlano.getRGB(i,j)) & 255;
                }
                catch(java.lang.ArrayIndexOutOfBoundsException e){}
                if(B != 0 && B != 255) {
                    w.addToWorld(i * 5, j * 5, z, (byte) B);
                }
            }//Fin del for para recorrer pixeles
        }//Fin del for para recorrer pixeles
    }

    public void loadByFile5(world w, int str) {
        int y = 0, z = 0;

        FileInputStream fis = null;
        DataInputStream entrada = null;
        int n = 0;
        try {
            //fis =  this.getClass().getResourceAsStream("src/gotozero/txt/" + str + ".dat");
            fis = new FileInputStream("src/gotozero/txt/" + str + ".dat");
            //loader.getClass().getResourceAsStream("src/gotozero/txt/" + str + ".dat");
            entrada = new DataInputStream(fis);

            while (true) {
                //se lee  un entero del fichero
                //System.out.println(n);  //se muestra en pantalla
                for (int i = 0; i < 100; ++i) {
                    n = entrada.readInt();
                    if(n > 0) {
                        w.addToWorld(i * 5, y, z, (byte) n);
                    }
                }
                y += 5;
                if(n < 0 && n != Integer.MIN_VALUE) {
                    z += 5;
                    System.out.println("SaltoDeLinea ultraDimensionalLlllllllllllllllllllllllllllllllllllllllllllllllll");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    if (entrada != null) {
                        entrada.close();
                    }
                }
            } catch (IOException e) { System.out.println(e.getMessage()); }

        }
    }
    public static void loadByFile1(world w, int str) {
        int y = 0, z = 0;
        try {
            //El Chunk es de 100, 100, indefinido
            Scanner bf = new Scanner(new File("src/gotozero/txt/" + str + ".txt"));
            String[] toNumber;
            String readed;
            while(bf.hasNextLine()){
                readed = bf.nextLine();
                toNumber = readed.split(" ");
                if(toNumber.length > 1){
                    for(int i = 0; i < toNumber.length; ++i) {
                        if(!Objects.equals(toNumber[i], "00")) {
                            if(!toNumber[i].contains("M")) {
                                w.addToWorld(i * 5, y, z, (byte) Integer.parseInt(toNumber[i]));
                            } else {
                                Monster battler = new Monster((byte) 0, "Guerrero", "Cebolla");
                                battler.setPonderedPos(i*5, y, z + 1);
                                w.addMonster(battler);
                            }
                        }
                    }
                    y += 5;
                } else {
                    z += 5;
                    y = 0;
                }
            }

            bf.close();
            System.out.println("FicheroLeido");
            setEscritura(w.getByMap());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Linea" + (y/5));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setEscritura(Object[] e) throws IOException {
        File out = new File("datos.data");
        FileOutputStream fOS = new FileOutputStream(out);
        ObjectOutputStream oOS = new ObjectOutputStream(fOS);
        oOS.writeObject(e);

        oOS.close();
    }
}
