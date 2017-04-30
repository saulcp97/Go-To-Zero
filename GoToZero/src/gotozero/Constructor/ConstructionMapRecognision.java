/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Constructor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author saulc
 */
public class ConstructionMapRecognision {
    
    public static void main(String[] args) {
        recognise("../img/Map/Chees.png");
    }
    
    private static Image load(String r){
         try {
              return ImageIO.read(ConstructionMapRecognision.class.getResource(r));
            } catch (IOException ex) {
                // handle exception...
            }
            return null;
        
    }
    
    public static void recognise(String readImg){
        int R=0; 
        int G=0; 
        int B=0;
            
        BufferedImage bufferPlano = (BufferedImage)(load(readImg)); 

        /*Recorro la imagen y voy mirando cada pixel*/ 
        for(int i=0; i < bufferPlano.getWidth(); ++i){ 
            for(int j=0; j < bufferPlano.getHeight(); ++j){ 

            try{ 
                R = (bufferPlano.getRGB(i,j)>>16) & 255;
                G = (bufferPlano.getRGB(i,j)>>8) & 255;
                B = (bufferPlano.getRGB(i,j)) & 255;
            } 
            catch(java.lang.ArrayIndexOutOfBoundsException e){} 
            

            
            if((R == G) && (G == B) && (B == 0)){
                System.out.println("Pixel: (" + i + "," + j + ") es de color negro");
            }
            
            }//Fin del for para recorrer pixeles 
        }//Fin del for para recorrer pixeles 
    }
    
    
    
    
    
}
