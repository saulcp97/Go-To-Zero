package map;

/**
 * Write a description of class Map here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Map {
    public final int MAX_WIDTH = 3;
    public final int MAX_HEIGHT = 3;
    private Zona[] Cargados;
    public Zona USO;
    public Map(){
        this.Cargados = new Zona[1];
        for (int i = 0; i < this.Cargados.length; i++) {
            this.Cargados[i] = new Zona();
        }
        this.USO = this.Cargados[0];
        System.out.println("Mapa: " + getDimX() + ", " + getDimY());
    }
    
    public int getDimX(){
        return this.USO.length();
    }
    
    public int getDimY() {
        return this.USO.getFirst().getSig();
        
        
    }
}
