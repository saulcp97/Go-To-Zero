package map;


/**
 * contiene un array de ints con la forma de columna desde el centro del planeta, siendo este el 0, hasta el punto mas alto de la atmosfera, siendo este el array.length - 1.
 * 
 * @author Saúl Cerdá Peris
 * @version 26/01/2017
 * 
 */


public class NodoG1 {
    public NodoG1 next;
    private byte[] data;
    
    public NodoG1 (int altura) {
        this.next = null;
        this.data = new byte[altura];
        for (int i = 0; i < altura; i++) {
            this.data[i] = 0;
        }
    }
    
    public NodoG1(int altura, NodoG1 siguiente) {
        this.next = siguiente;
        this.data = new byte[altura];
        for (int i = 0; i < altura; i++) {
            this.data[i] = 0;
        }
    }
    
    public int getInt(int i) {
        return data[i];
    }
    
    public void setContent(byte[] a) {
        for (int i = 0; i < a.length && i < data.length; i++) {
            data[i] = a[i];    
        }
    }
    
    public void setContent(int i, byte n) {
        this.data[i] = n;
    }
    
    
}
