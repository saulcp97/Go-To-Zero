package gotozero.ObjectGestion;

import gotozero.*;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item {
    // instance variables - replace the example below with your own
    protected String Name;
    private Rect Visual;

    protected String[] actions;

    private String shortDescription;
    private String longDescription;
    private String poet;

    /**
     * Constructor for objects of class Item
     */
    public Item() {
        this.Name = "Cosa";
        this.Visual = new Rect();
        this.Visual.setImg(new sprite("img/object/anilloDiamanteAzul.png").getImg());
        this.loadDescriptions("anilloDiamanteAzul");
        this.actions = new String[2];
        this.actions[0] = "Info";
        this.actions[1] = "Cerrar";
    }
    
    public Item(String nombre,String ruta) {
        this.Name = nombre;
        this.Visual = new Rect();
        this.Visual.setImg(new sprite("img/object/"+ruta+".png").getImg());
        this.loadDescriptions(ruta);
        this.actions = new String[2];
        this.actions[0] = "Info";
        this.actions[1] = "Cerrar";
    }
    
    public String getName(){
        return this.Name;
    }
    
    public Image getImage() {
        return this.Visual.getOutput();
    }
    
    public Rect getRect() {
        return this.Visual;
    }
    
    public void setActions(String[] act) {
        this.actions = act;
    }
    
    public String[] getActions() {
        return this.actions;
    }

    protected void loadDescriptions(String str){
        //El Chunk es de 100, 100, indefinido
        File aux = new File("src/gotozero/txt/objects/" + str + ".txt");
        //if(aux.exists()){
        InputStream a = Main.class.getResourceAsStream("txt/objects/" + str + ".txt");
        if(a != null){
            Scanner bf = new Scanner(a);
            if(bf.hasNext()) {
                this.shortDescription = bf.nextLine();
                this.longDescription = bf.nextLine();
                this.poet = bf.nextLine();
                System.out.println("Se ha leido el objeto" + str);
            } else {
                this.shortDescription = "-> Longitud fichero: " + aux.getUsableSpace();
            }
            System.out.println("FicheroLeido");
            bf.close();

        }
        //}
    }

    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }
    public String getPoet() { return poet; }

    public void doAction(int i, lifes liv) {
        
    }
}
