import java.util.*;
import java.awt.Image;
import java.awt.Point;
import java.awt.MouseInfo;
/**
 * Write a description of class Mage here.
 * 
 * @author Saúl Cerdá Peris
 * @version (a version number or a date)
 */
public class Mage extends lifes {
    private byte Genero;
    private sprite Sprite;
    
    public Mage(byte g) {
        super((byte)0,0,0,640);    
        this.Genero = g;
        this.Sprite = new sprite(this);
        this.Inventario.makeLink(this);
    }
    
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
    
    public String getGenero() {
        String res = "";
        if (this.Genero < 0) {
            res = "ELLO";
        }
        else if (this.Genero > 0) {
            res = "FEMENINO";
        }
        else { res = "MASCULINO"; }
        return res;
    }
    
    public Image getImage() {
        return this.Sprite.getImg();
    }
}
