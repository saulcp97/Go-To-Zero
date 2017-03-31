package map;

/**
 * Write a description of class NodoG2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NodoG2 {
    public final int PROFUNDIDAD = 100;
    public NodoG2 next;
    private NodoG1 data;

    private int len;
    
    public NodoG2() {
        this.next = null;
        this.data = new NodoG1(PROFUNDIDAD);
        this.len = 1;
    }

    public NodoG2(NodoG2 siguiente) {
        this.next = siguiente;
        this.data = new NodoG1(PROFUNDIDAD);
        this.len = siguiente.getSig() + 1;
    }

    public NodoG2(int prof) {
        this.next = null;
        this.data = new NodoG1(prof);
        this.len = 1;
    }

    public NodoG2(int prof, NodoG2 siguiente) {
        this.next = siguiente;
        this.data = new NodoG1(prof);
        this.len = siguiente.getSig() + 1;
    }
    
    public int getSig() { return this.len; }
    
    public void addColumn(int n){
        NodoG1 aux = this.data;
        NodoG1 sig = new NodoG1(PROFUNDIDAD);
        while(n > 0){
            aux.next = sig;
            aux = aux.next;
            sig = new NodoG1(PROFUNDIDAD);
            System.out.println("column: " + n);
            n--;
        }
    }

    public void addFila(int i,int j){
        NodoG2 aux = this;
        NodoG2 sig = new NodoG2(PROFUNDIDAD);
        while(i > 0){
            try{
                aux.next = sig;
                aux = aux.next;
                sig = new NodoG2(PROFUNDIDAD);
                System.out.println("creado fila " +i);
                sig.addColumn(j);
                i--;
                this.len++;
                Thread.sleep(50);
            }
            catch (Exception e) { System.out.println(e);}
        }
    }
    
    public NodoG1 getNodeByIndex(int i) {
        NodoG1 aux = data;
        while(i > 0) {
            aux = aux.next;
            i--;
        }
        
        return aux;
    }
}
