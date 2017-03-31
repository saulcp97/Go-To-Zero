package map;


/**
 * Write a description of class MAPA here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MAPA {
    private int[][][] intis;

    /**
     * Constructor for objects of class MAPA
     */
    public MAPA() {
        this.intis = new int[250][][];
        for(int i = 0; i < this.intis.length; i++) {
            this.intis[i] = new int[250][];
            System.out.println(i);
            for(int j = 0; j < this.intis[i].length; j++) {
                this.intis[i][j] = new int[100];
                for(int k = 0; k < this.intis[i][j].length; k++) {
                    this.intis[i][j][k] = 0;
                    
                }
                
            }
            
        }
    }

}
