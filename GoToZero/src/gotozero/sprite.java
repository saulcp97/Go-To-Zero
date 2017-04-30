package gotozero;

import javax.imageio.ImageIO;
import java.awt.image.*;

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

    public static final sprite Muro = new sprite("img/muroSimple.png");
    public static final sprite MuroMadera = new sprite("img/muroMadera.png");
    public static final sprite MuroBaldosas = new sprite("img/baldosasCeramica.png");
    public static final sprite Cesped = new sprite("img/cespedSimple.png");
    public static final sprite ColumnCoin = new sprite("img/columnaMonedas.png");
    public static final sprite FloorCoin = new sprite("img/sueloMoney.png");
    public static final sprite Suelo = new sprite("img/suelo.png");
    public static final sprite BotellaSed = new sprite("img/botellaSed.png");
    public static final sprite ConPollo = new sprite("img/alitaDePollo.PNG");
    public static final sprite SinPollo = new sprite("img/alitaDePolloSinPollo.PNG");
    
    
    
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
        ImagePanel(this.ruta[0]);
    }
    
    public sprite(Mage mago) {
        this.ruta = new String[8];
        this.Input = new BufferedImage[8];
        switch(mago.getGenero()){
            case "ELLO":
                this.ruta[0] = "img/ello.png";
                ImagePanel(this.ruta[0],0);
                break;
            case "FEMENINO":
                this.ruta[0] = "img/femina.png";
                break;
            case "MASCULINO":
                this.ruta[0] = "img/SpriteMen.png";
                this.ImagePanel(this.ruta[0],0);
                this.ImagePanel(this.ruta[0],1);
                this.ImagePanel(this.ruta[0],2);
                this.ImagePanel(this.ruta[0],3);
                break;
        }
    }
    
    public void setRuta(String[] rutas) {
        this.ruta = rutas;
    } 
    
    public void ImagePanel(String ruta) {
        if(ruta != null) {
            try {
              System.out.println(ruta);
              this.Input[0] = ImageIO.read(sprite.class.getResource(ruta));
              this.Output = this.Input[0];
            } catch (IOException ex) {
                // handle exception...
            }
        }
    } 
    
    public void ImagePanel(String ruta, int i) {
        if(ruta != null) {
            try {
              System.out.println(ruta);
              this.Input[i] = ImageIO.read(sprite.class.getResource(ruta)).getSubimage(0, i*64, 64, 64);
              this.Output = this.Input[i];
            } catch (IOException ex) {
                // handle exception...
            }
        }
    }
    
    public void setOutput(int dir){
        if(dir < this.Input.length){
            this.Output = this.Input[dir];
        }
    }
    
    public BufferedImage getImg() {
        return this.Output;
    }
}
