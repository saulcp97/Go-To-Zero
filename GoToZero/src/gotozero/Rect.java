package gotozero;

import gotozero.Organice.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Write a description of class rect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rect implements Actualizable{
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
    
    private static int WidthWorld;
    private static int HeightWorld;

    private int[] section = new int[3];
    
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
        
        this.setSection(1, 1, 1);
    }
    
    public Rect(Mage mago) {
        this.x = mago.getX();
        this.y = mago.getY();
        
        this.width = mago.getTamanyoX();
        this.height = mago.getTamanyoY();
        this.Type = 1;
        this.out = mago.getImage();

        
        this.setSection(1, 1, 1);
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
        
        this.setSection(1, 1, 1);
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
        
        this.setSection(1, 1, 1);
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setType(byte t) {
        this.Type = t;
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
        this.Type = 0;
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
    
    @Override
    public void actualizar(Object i) {
        if(i instanceof Rect){
            this.x = ((Rect) i).x;
            this.y = ((Rect) i).y;
            this.width = ((Rect) i).width;
            this.height = ((Rect) i).height;

            this.Type = ((Rect) i).Type;
            this.col =  ((Rect) i).col;

            this.section[0] = ((Rect) i).section[0];
            this.section[1] = ((Rect) i).section[1];
            this.section[2] = ((Rect) i).section[2];
            //this.setImg(((BufferedImage)((Rect) i).out).getSubimage(0, 0, ((BufferedImage)((Rect) i).out).getWidth(), ((BufferedImage)((Rect) i).out).getHeight()));
            if(((Rect)i).Type == 1) {
                this.setImg(((Rect)i).out);
            } else {
                this.out = null;
                this.rsc = null;
            }
        }
    }
    
    @Override
    public String toString(){
        String res = "";
        res += "Position: (" + this.x + "," + this.y +"), Size: (" + this.width + "," + this.height + "), Type: " + this.Type + "\n";
        return res;
    }

    @Override
    public Rect clone() {
        Rect n = new Rect(this.x, this.y, this.width, this.height, 0);
        n.col = this.col;
        n.Type = this.Type;
        n.section = this.section.clone();
        if(this.out != null){
            n.setImg(this.out);
        }
        return n;         
    }

    public void setSection(int X,int Y, int Z){
        this.section[0] = X;
        this.section[1] = Y;
        this.section[2] = Z;
    }

    public int getWS(){
        return this.section[0];
    }
    public int getHS(){
        return this.section[1];
    }
    public int getDS(){
        return this.section[2];
    }
    
    public void painTo (Graphics gc,int aX,int aY){
        for(int iS = 0; iS < this.section[0] && (iS * 64 + aX) <= Rect.WidthWorld; ++iS){
            for(int kS = 0; kS < this.section[2] - 1 && (aY + (this.section[1] - 1) * 64 - 32 * kS) <= Rect.HeightWorld && (aY + (this.section[1] - 1) * 64 - 32 * kS + 128) >= 0; ++kS){
                gc.drawImage(this.out, aX + 64 * iS, aY + (this.section[1] - 1) * 64 - 32 * kS,null);
            }
            for(int jS = 0; jS < this.section[1] && (aY + jS * 64 - 32 * (this.section[2] - 1) <= Rect.HeightWorld); ++jS){
                gc.drawImage(this.out, aX + 64 * iS, aY + jS * 64 - 32 * (this.section[2] - 1),null);
            }
        }
    }
    
    public static void setPantalla(int w, int h){
        Rect.WidthWorld = w;
        Rect.HeightWorld = h;
    }
}
