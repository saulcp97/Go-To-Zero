/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import java.awt.Point;

/**
 *
 * @author saulc
 */
public class Dijkstra {
    private double size;
    private final double SQRT_2 = Math.sqrt(2);
    private Point[] ocupado;
    private int mapWidth;
    private int mapHeight;
    
    
    public Dijkstra(int w, int h){
        this.size = 1;
        this.ocupado = new Point[0];
        
        this.mapWidth = w;
        this.mapHeight = h;
    }
    
    public double[] RayCast(int posFinX, int posFinY, int posIniX, int posIniY){
        double[] res = new double [2];
        res[0] = 0;
        res[1] = 0;
        //Point res = new Point(0, 0);
        double difX = posFinX - posIniX;
        double difY = posFinY - posIniY;
        
        boolean detected = false;
        if(difX == 0){
            res[1] = (int)Math.signum(difY);
            for(int i = 0; i <= difY; ++i){
            for(Point auxi:ocupado){
                    if(!detected){
                        detected = auxi.x == posIniX && auxi.y == posIniY + res[1] * i;
                    } else {
                        res[1] = 0;
                    }
                    
                }
            }
        } else if(difY == 0){
            res[0] = (int)Math.signum(difX);
            for(int i = 0; i <= difX; ++i){
            for(Point auxi:ocupado){
                    if(!detected){
                        detected = auxi.x == posIniX + res[0] * i && auxi.y == posIniY;
                    } else {
                        res[0] = 0;
                    }
                }
            }
        } else if(Math.abs(difX) < Math.abs(difY)){
            res[1] = Math.signum(difY);
            res[0] = (difX/difY * Math.signum(difX));
            for(int i = 1; i <= difY && !detected; ++i){
                for(Point auxi:ocupado){
                    if(!detected){
                        detected = auxi.y == posIniY + i && auxi.x == ((double)difX)/difY * i;
                    } else {
                        res[1] = 0;
                        res[0] = 0;
                    }
                }
            }   
        } else{
            res[0] = Math.signum(difX);
            res[1] =(difY/difX * Math.signum(difY));
            for(int i = 1; i <= difX && detected; ++i){
                for(Point auxi:ocupado){
                    if(!detected){
                        detected = auxi.x == posIniX + i && auxi.y == ((double)difY)/difX * i;
                    } else {
                        res[0] = 0;
                        res[1] = 0;
                    }
                    
                }
            }
        }  
        return res;
    }
    
    
    public Dijkstra(int w, int h, double s){
        this.size = s;
        this.ocupado = new Point[0];
        
        this.mapWidth = w;
        this.mapHeight = h;
    }
    
    public void addPoint(int x, int y){
        Point[] aux = new Point[this.ocupado.length + 1];
        for(int i = 0; i < this.ocupado.length; ++i) {
            aux[i] = this.ocupado[i];
        }
        aux[this.ocupado.length] = new Point(x, y);
        this.ocupado = aux;
    }
    
    
    public static void main(String[] args){
        Dijkstra map = new Dijkstra(21, 21);
        System.out.println(map.Algoritmo(0, 0, 10, 10));
        
        System.out.println(map.RayCast(0, 0, 5, 0).toString());
        
    }
    
    public double[] paso(int posFinX, int posFinY, int posIniX, int posIniY){
        double[] result = RayCast(posFinX, posFinY, posIniX, posIniY);
        if(result[0] == 0 && result[1] == 0){
            System.out.println(this.Algoritmo(posFinX, posFinY, posIniX, posIniY));
        }
        return result;
    }
    
    
    /**
     * Movimiento 7 direcciones (8 normales, menos la de origen)
     * @param posFinX
     * @param posFinY
     * @param posIniX
     * @param posIniY
     * @return
     */
//    public double Algoritmo(int posFinX, int posFinY, int posIniX, int posIniY) {
//        if(posFinX != posIniX && posFinY != posIniY){
//            Point[] visitado = new Point[0];
//            visitado = addPointToArrayPoint(visitado, posIniX, posIniY);
//            
//            double[] distanciaVisitado = new double[0];         
//            distanciaVisitado = addPointToArrayDouble(distanciaVisitado, 0);
//            int i = 0;
//            double res = 0;
//            while(i < visitado.length){
//                // (0,-1)
//                
//                        
//                if(visitado[i].x != posFinX || visitado[i].y - 1 != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x, visitado[i].y - 1)){
//                        visitado = addPointToArrayPoint(visitado, visitado[i].x, visitado[i].y - 1);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + 1;
//                    }
//                }
//                // (1,-1) 
//                if(visitado[i].x + 1 != posFinX || visitado[i].y - 1 != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x + 1, visitado[i].y - 1)){
//                        visitado = addPointToArrayPoint(visitado, visitado[i].x + 1, visitado[i].y - 1);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + SQRT_2;
//                    }
//                }
//                // (1,0)
//                if(visitado[i].x + 1 != posFinX || visitado[i].y != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x + 1, visitado[i].y)){
//                        visitado = addPointToArrayPoint(visitado, visitado[i].x + 1, visitado[i].y);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + 1;
//                    }
//                }
//                // (1,1)
//                if(visitado[i].x + 1 != posFinX || visitado[i].y + 1 != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x + 1, visitado[i].y + 1)){
//                        visitado = addPointToArrayPoint(visitado, visitado[i].x + 1, visitado[i].y + 1);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + SQRT_2;
//                    }
//                }
//                // (0,1)
//                if(visitado[i].x != posFinX || visitado[i].y + 1 != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x, visitado[i].y + 1)){
//                        visitado = addPointToArrayPoint(visitado, visitado[i].x, visitado[i].y + 1);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + 1;
//                    }
//                }
//                // (-1,1)
//                if(visitado[i].x - 1!= posFinX || visitado[i].y + 1 != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x - 1, visitado[i].y + 1)){
//                        visitado = addPointToArrayPoint(visitado, visitado[i].x - 1, visitado[i].y + 1);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + SQRT_2;
//                    }
//                }
//                // (-1,0)
//                if(visitado[i].x - 1!= posFinX || visitado[i].y != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x - 1, visitado[i].y)){
//                        visitado = addPointToArrayPoint(visitado,visitado[i].x - 1, visitado[i].y);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + 1;
//                    }
//                }
//                // (-1,-1)
//                if(visitado[i].x -1 != posFinX || visitado[i].y - 1 != posFinY){
//                    if(!estaVisto(visitado, visitado[i].x - 1, visitado[i].y - 1)){
//                        visitado = addPointToArrayPoint(visitado,visitado[i].x - 1, visitado[i].y - 1);
//                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
//                    }
//                } else {
//                    if(res == 0 || distanciaVisitado[i] + 1 < res){
//                        res = distanciaVisitado[i] + SQRT_2;
//                    }
//                }
//                if(res != 0) {return res; }
//                ++i;
//            } 
//        }
//        return 0;
//    }
    
    public boolean estaVisto(Point[] vis, int x, int y){
        for(int i = 0; i < vis.length; ++i){
            if(vis[i].x == x && vis[i].y == y) {
                return true;   
            }           
        }
        return false;
    }
    
    
    public Point[] addPointToArrayPoint(Point[] origen,int x, int y){
        Point[] aux = new Point[origen.length + 1];
        for(int i = 0; i < origen.length; ++i) {
            aux[i] = origen[i];
        }
        aux[origen.length] = new Point(x, y);
        return aux;
    }
    
    public double[] addPointToArrayDouble(double[] origen,double n){
        double[] aux = new double[origen.length + 1];
        for(int i = 0; i < origen.length; ++i) {
            aux[i] = origen[i];
        }
        aux[origen.length] = n;
        return aux;
    }
    
        public double Algoritmo(int posFinX, int posFinY, int posIniX, int posIniY) {
        if(posFinX != posIniX && posFinY != posIniY){
            Point[] visitado = new Point[0];
            visitado = addPointToArrayPoint(visitado, posIniX, posIniY);
            
            double[] distanciaVisitado = new double[0];         
            distanciaVisitado = addPointToArrayDouble(distanciaVisitado, 0);
            int i = 0;
            double res = 0;
            while(i < visitado.length){
                // (0,-1)
                if(visitado[i].x != posFinX || visitado[i].y - 1 != posFinY){
                    if(!estaVisto(visitado, visitado[i].x, visitado[i].y - 1) && visitado[i].y > 0){
                        visitado = addPointToArrayPoint(visitado, visitado[i].x, visitado[i].y - 1);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + 1;
                    }
                }
                // (1,-1) 
                if(visitado[i].x + 1 != posFinX || visitado[i].y - 1 != posFinY){
                    if(!estaVisto(visitado, visitado[i].x + 1, visitado[i].y - 1) && visitado[i].x + 1 < this.mapWidth && visitado[i].y - 1 > 0){
                        
                        visitado = addPointToArrayPoint(visitado, visitado[i].x + 1, visitado[i].y - 1);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + SQRT_2;
                    }
                }
                // (1,0)
                if(visitado[i].x + 1 != posFinX || visitado[i].y != posFinY){
                    if(!estaVisto(visitado, visitado[i].x + 1, visitado[i].y) && visitado[i].x + 1 < this.mapWidth){
                        visitado = addPointToArrayPoint(visitado, visitado[i].x + 1, visitado[i].y);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + 1;
                    }
                }
                // (1,1)
                if(visitado[i].x + 1 != posFinX || visitado[i].y + 1 != posFinY){
                    if(!estaVisto(visitado, visitado[i].x + 1, visitado[i].y + 1)){
                        visitado = addPointToArrayPoint(visitado, visitado[i].x + 1, visitado[i].y + 1);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + SQRT_2;
                    }
                }
                // (0,1)
                if(visitado[i].x != posFinX || visitado[i].y + 1 != posFinY){
                    if(!estaVisto(visitado, visitado[i].x, visitado[i].y + 1) && visitado[i].y + 1 < this.mapHeight){
                        visitado = addPointToArrayPoint(visitado, visitado[i].x, visitado[i].y + 1);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + 1;
                    }
                }
                
                // (-1,1)
                if(visitado[i].x - 1!= posFinX || visitado[i].y + 1 != posFinY){
                    if(!estaVisto(visitado, visitado[i].x - 1, visitado[i].y + 1) && visitado[i].x - 1 > 0 && visitado[i].y + 1 < this.mapWidth){
                        visitado = addPointToArrayPoint(visitado, visitado[i].x - 1, visitado[i].y + 1);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + SQRT_2;
                    }
                }
                // (-1,0)
                if(visitado[i].x - 1!= posFinX || visitado[i].y != posFinY){
                    if(!estaVisto(visitado, visitado[i].x - 1, visitado[i].y) && visitado[i].y - 1 > 0){
                        visitado = addPointToArrayPoint(visitado,visitado[i].x - 1, visitado[i].y);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + 1);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + 1;
                    }
                }
                // (-1,-1)
                if(visitado[i].x -1 != posFinX || visitado[i].y - 1 != posFinY){
                    if(!estaVisto(visitado, visitado[i].x - 1, visitado[i].y - 1) && visitado[i].x - 1 > 0 && visitado[i].y - 1 > 0){
                        visitado = addPointToArrayPoint(visitado,visitado[i].x - 1, visitado[i].y - 1);
                        distanciaVisitado = addPointToArrayDouble(distanciaVisitado, distanciaVisitado[i] + SQRT_2);
                    }
                } else {
                    if(res == 0 || distanciaVisitado[i] + 1 < res){
                        res = distanciaVisitado[i] + SQRT_2;
                    }
                }
                if(res != 0) {return res; }
                ++i;
            } 
        }
        return 0;
    }
    
}
