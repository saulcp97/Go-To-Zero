import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 * Write a description of class rect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rect {
    // instance variables - replace the example below with your own
    private int x;
    private int y;
    private int width;
    private int height;
    
    //
    private byte Type;
    //Para tipos planos
    private Color col;
    //Para tipos imagen
    private Image rsc;
    private Image out;
    
    /**
     * Constructor for objects of class rect
     */
    public Rect() {
        this.x = 0;
        this.y = 0;
        this.width = 64;
        this.height = this.width;
        this.Type = 0;
        this.col = Color.black;
        
        this.rsc = null;
        this.out = null;
    }
    
    public Rect(Mage mago) {
        this.x = mago.getX();
        this.y = mago.getY();
        
        this.width = mago.getTamanyoX();
        this.height = mago.getTamanyoY();
        this.Type = 1;
        this.out = mago.getImage();

        switch(mago.getGenero()){
            case "FEMENINO":
                this.col = Color.pink;
                break;
            case "MASCULINO":
                this.col = Color.blue;
                break;
            default:
                this.col = Color.gray;
        }
    }

    
    public void actualMage(Mage mago) {
        this.x = mago.getX() - (mago.getTamanyoX()>>2);
        this.y = mago.getY() - (mago.getTamanyoY()>>1)-(mago.getZ()>>1);
    }
    
    public byte getType() {
        return this.Type;
    }
    
    public Image getOutput(){
        return this.out;
    }
    
    public Rect(int i, int j, int w, int h, int cColor) {
        this.x = i;
        this.y = j;
        this.width = w;
        this.height = h;
        this.Type = 0;
        switch(cColor) {
            case 0:
                col = null;
                break;
            case 1:
                col = Color.red;
                break;
            case 2:
                col = Color.blue;
                break;
            case 3:
                col = Color.green;
                break;
            case 4:
                col = Color.gray;
                break;
            case 5:
                col = Color.orange;
                break;
            default:
                col = Color.black;
        }
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setImg(Image i){
        this.out = this.rsc = i;
        this.Type = 1;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setWidth(int w) {
        this.width = w;
    }
    
    public void setHeight(int h) {
        this.height = h;
    }
    
    public void setCol(Color c) {
        this.col = c;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Color getCol() {
        return this.col;
    }
    
    public int getImageWidth() {
        return ((BufferedImage)this.out).getWidth();
    }
}
