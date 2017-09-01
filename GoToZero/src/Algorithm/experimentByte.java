/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author saulc
 */
public class experimentByte {
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FileOutputStream fos = null;
        DataOutputStream salida = null;

        FileInputStream fis = null;
        DataInputStream entrada = null;
        int n;
/*
        try {
            fos = new FileOutputStream("src/gotozero/txt/datos.dat");
            salida = new DataOutputStream(fos);

            System.out.print("Introduce número entero. -1 para acabar: ");
            n = sc.nextInt();
            while (n != -1) {
                salida.writeInt(n); //se escribe el número entero en el fichero
                System.out.print("Introduce número entero. -1 para acabar: ");
                n = sc.nextInt();
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
*/

        System.out.println("Escribiendo la lectura de fichero");
        try {
            fis = new FileInputStream("src/gotozero/txt/-1.dat");
            entrada = new DataInputStream(fis);
            while (true) {
                n = entrada.readInt();  //se lee  un entero del fichero
                System.out.println(n);  //se muestra en pantalla
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }





    }
    
    private static short pulsarW(short e){
        return (short) (e + 1);
    }
    
    private static short soltarW(short e){
        return (short) (e - 1);
    }
    
    
    private static void comprov(short e){
        short activo =  (short)0b1000000000000000;
        //short empty =   (short) 0;
        //short W =       (short) 1;
        //short D =       (short) 2;
        //short S =       (short) 4;
        //short A =       (short) 8;
        //short Action =  (short) 16;

        if((activo & e) == activo){
            System.out.println("Entrada Activa \nProcediendo a lectura \n...");
            
                    //0b111111111111111
            switch((0b0000000000001111 & e)){
                case 1:
                    System.out.println("Pulsa 'W'");
                    break;
                case 2:
                    System.out.println("Pulsa 'D'");
                    break;                
                case 3:
                    System.out.println("Pulsa 'D + W'");
                    break;
                case 4:
                    System.out.println("Pulsa 'S'");
                    break;
                case 5:
                    System.out.println("Pulsa 'S + W' Imbecil~");
                    break;
                case 6:
                    System.out.println("Pulsa 'S + D'");
                    break;
                case 7:
                    System.out.println("Pulsa 'S + D + W' Imbecil~");
                    break;
                case 8:
                    System.out.println("Pulsa 'A'");
                    break;
                case 9:
                    System.out.println("Pulsa 'A + W'");
                    break;
                case 10:
                    System.out.println("Pulsa 'A + D' Imbecil~");
                    break;
                case 11:
                    System.out.println("Pulsa 'A + D + W' Imbecil~");
                    break;
                case 12:
                    System.out.println("Pulsa 'A + S'");
                    break;
                case 13:
                    System.out.println("Pulsa 'A + S + W' Imbecil~");
                    break;
                case 14:
                    System.out.println("Pulsa 'A + S + D' Imbecil~");
                    break;
                case 15:
                    System.out.println("Pulsa 'A + S + D + W' Mas tonto y no naces");
                    break;
                case 0:
                    System.out.println("No pulsaste Nada");
                    break;
                // es per a compensar la variabilitat dels FPS i que el moviment siga continu en temps real encara que els FPS varien
                    // entonces? No voldras dir que 0,03 es el ideal? moviment/ideal * real tio fes cast a double del (((double) moviment)/ ideal) * real
            }            //al pasar moviment a double el que fas es que no es quede com a 0, perque al ferse en ints
                        //necesites el desplaçament/velo, el delta i el valor ideal que ja el saps pero la velocitat la deus de saber(?)
                        //no es de 16 en el nostre cas?
        }   else {
            System.out.println("Entrada Inactiva \n");
        }       
    }
    
    
}
