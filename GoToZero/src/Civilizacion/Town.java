/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Civilizacion;

/**
 *
 * @author saulc
 */
public class Town {
    public static final Town Capital = new Town(0,0,0,(byte)1);
    
    private int cordX;
    private int cordY;
    private int cordZ;
    
    private byte Type;
    
    public Town(int x, int y, int z, byte T) {
        this.cordX = x;
        this.cordY = y;
        this.cordZ = z;
        
        this.Type = T;
    }
    
    
}
