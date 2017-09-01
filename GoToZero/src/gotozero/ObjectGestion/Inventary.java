package gotozero.ObjectGestion;


import gotozero.Alimento;
import gotozero.Menus;
import gotozero.ObjectGestion.Item;
import gotozero.lifes;

/**
 * Write a description of class Inventary here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inventary {

    // instance variables - replace the example below with your own
    private Item[] Dropable;
    private byte[] Probability;
    
    private Item[] InventaryObjects;
    private byte[] Count;
    private lifes link;
    /**
     * Constructor for objects of class Inventary
     */

    public Inventary() {
        // initialise instance variables
        this.Dropable = new Item[16];
        this.Dropable[0] = Alimento.Agua;
        this.Dropable[1] = Alimento.Naranja;
        
        this.Probability = new byte[16];
        this.Probability[0] = 1;
        this.Probability[1] = 1;
        for(int i = 2; i < 16; ++i){
            this.Probability[i] = 0;
            this.Dropable[i] = null;
        }
        
        this.InventaryObjects = this.Dropable;
        this.Count = this.Probability;
    }

    public void addItemInPosition(Item obj, int index) {
        this.InventaryObjects[index] = obj;
        this.Count[index] = 1;
    }

    public Item substractByPosition(int index) {
        Item result = null;
        if(this.InventaryObjects[index] != null) {
            result = this.InventaryObjects[index];
            --this.Count[index];
            if(this.Count[index] <= 0) {
                this.InventaryObjects = null;
            }
        }
        return result;
    }

    public byte[] getCount() {
        return this.Count;
    }

    public void addItem(Item obj) {
        for(int i = 0; i < this.InventaryObjects.length; ++i) {
            if(this.InventaryObjects[i] != null && this.InventaryObjects[i].Name.equals(obj.Name)) {
                ++this.Count[i];
                return;
            }
        }
        for (int i = 0; i < this.InventaryObjects.length; ++i) {
            if(this.InventaryObjects[i] == null) {
                this.InventaryObjects[i] = obj;
                this.Count[i] = 1;
                return;
            }
        }
    }

    public Item getByPosition(int i) {
        return this.InventaryObjects[i];
    }
    
    public void makeLink(lifes l) {
        this.link = l;
    }
    
    public int getInventaryLength() {
        return this.InventaryObjects.length;
    }


    //la acción act 0 es comer, cada acción sobre un objeto viene especificada con un codigo aun no escrito XD
    public void doAction(int i, int act){
        if(act == 0) {
            if(this.Count[i] > 0){
                if(this.InventaryObjects[i] instanceof Alimento) {
                    link.Alimentacion((Alimento) this.InventaryObjects[i]);
                    this.Count[i]--;
                } else {
                    Menus.InfoObjetos = new Menus(this.InventaryObjects[i]);
                }

                if(this.Count[i] == 0) {
                    this.InventaryObjects[i] = null;
                    //this.eliminarCategoriaInventario(i);
                }
            }





            /*if(i == 0) {
                link.Alimentacion(Alimento.Agua);
                this.Count[i]--;
                if(this.Count[i] == 0) {
                    this.eliminarCategoriaInventario(i);
                }
            } else if (i == 1) {
                link.Alimentacion(Alimento.Naranja);
                this.Count[i]--;
            }*/

        }
    }

    private void eliminarCategoriaInventario(int index){
        Item[] inventario = new Item[this.InventaryObjects.length - 1];
        byte[] contador = new byte[this.Count.length - 1];
        /*for(int i = 0; i < index; ++i ){
            inventario[i] = this.InventaryObjects[i];
        }
        for(int i = index; i < inventario.length; ++i){
            inventario[i] = this.InventaryObjects[i + 1];
        }*/
        for (int i = 0; i < inventario.length; ++i) {
            if(i < index) {
                inventario[i] = this.InventaryObjects[i];
                contador[i] = this.Count[i];
            } else {
                inventario[i] = this.InventaryObjects[i + 1];
                contador[i] = this.Count[i + 1];
            }

        }

        this.InventaryObjects = inventario;
        this.Dropable = inventario;
        this.Count = contador;
    }

    public String getSelectedObjectName(int i) {
        return this.InventaryObjects[i].getName();
    }

    public Inventary(String s, int huecos){
        this.InventaryObjects = new Item[huecos];
        this.Count = new byte[huecos];

        for(int i = 0; i < this.Count.length; ++i) {
            this.Count[i] = 0;
        }
        
        this.Dropable = new Item[0];
        this.Probability = new byte[0];
        this.link = null;
    }
}
