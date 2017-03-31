package map;

/**
 * Write a description of class Zona here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zona {
    public static int MAX_WIDTH = 249;
    public static int MAX_HEIGHT = 249;
    
    private NodoG2 primero;
    private int l;
    public Zona() {
        this.primero = new NodoG2();
        this.primero.addFila(MAX_WIDTH, MAX_HEIGHT);
        l = MAX_WIDTH + 1;
    }
    
    public void add(){
        l++;
    }
    
    public int length() {
        return l;
    }
    
    public int getInfo(int i, int j, int k) {
        return this.getNodeByIndex(i).getNodeByIndex(k).getInt(j);
    }
    
    public NodoG2 getNodeByIndex(int i){
        NodoG2 aux = primero;
        while(i > 0) {
            aux = aux.next;
            i--;
        }
        return aux;
    }
    public NodoG2 getFirst() { return primero; }
}
