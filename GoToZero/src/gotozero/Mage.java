package gotozero;

import java.awt.Image;
/**
 * Write a description of class Mage here.
 * 
 * @author Saúl Cerdá Peris
 * @version (a version number or a date)
 */
public class Mage extends lifes {
    private final byte Genero;
    private sprite Sprite;
    
    public Mage(byte g) {
        super((byte)0,0,1024,1024);    
        this.Genero = g;
        this.Sprite = new sprite(this);
        this.Sprite.setOutput(this.dir);
        this.Inventario.makeLink(this);
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
    
    @Override
    public void toRect(Rect entry){
        entry.setX(this.x - (this.TamanyoX >> 2));
        entry.setY(this.y - (this.z >> 1));
        entry.setWidth(this.TamanyoX);
        entry.setHeight(this.TamanyoY);
        entry.setImg(this.Sprite.getImg());
        entry.setSection(1, 1, 1);
    }
    
    public void setDir(byte i){
        if(dir != i) {
            this.dir = i;
            this.Sprite.setOutput(i);
        }
    }
}
