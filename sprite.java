import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.*;

import java.io.File;
import java.io.IOException;
/**
 * Write a description of class sprite here.
 * 
 * @author (your name) 
 * @version (a version numbor a date)
 */
public class sprite
{
    // instance variables - replace the example below with your own
    private String[] ruta;
    
    private BufferedImage[] Input;
    
    private BufferedImage Output;

    public static final sprite Muro = new sprite("/img/muroSimple.png");
    public static final sprite Cesped = new sprite("/img/cespedSimple.png");
    public static final sprite ColumnCoin = new sprite("/img/columnaMonedas.png");
    public static final sprite Suelo = new sprite("/img/suelo.png");
    public static final sprite BotellaSed = new sprite("/img/botellaSed.png");
    public static final sprite ConPollo = new sprite("/img/alitaDePollo.png");
    public static final sprite SinPollo = new sprite("/img/alitaDePolloSinPollo.png");
    /**
     * Constructor for objects of class sprite
     */
    public sprite() {
        this.ruta = new String[1];
        this.ruta[0] = "/img";
    }

    public sprite(String rut) {
        this.ruta = new String[1];
        this.ruta[0] = rut;
        this.Input = new BufferedImage[1];
        ImagePanel(rut,0);
    }
    
    public sprite(Mage mago) {
        this.ruta = new String[8];
        this.Input = new BufferedImage[8];
        switch(mago.getGenero()){
            case "ELLO":
                this.ruta[0] = "/img/ello.png";
                ImagePanel(this.ruta[0],0);
                break;
            case "FEMENINO":
                this.ruta[0] = "/img/femina.png";
                break;
            case "MASCULINO":
                this.ruta[0] = "/img/masculin.png";
                break;
        }
    }
    
    public void setRuta(String[] rutas) {
        this.ruta = rutas;
    }
    
    
    public void ImagePanel(String ruta, int i) {
       try {                
          this.Input[i] = ImageIO.read(sprite.class.getResource(ruta));
          this.Output = this.Input[i];
       } catch (IOException ex) {
            // handle exception...
       }
    }
    
    public BufferedImage getImg() {
        return this.Output;
    }
}
