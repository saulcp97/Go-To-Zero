package Algorithm;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author saulc
 */
public class DrawBot extends JFrame{
    private Dijkstra map = new Dijkstra(41, 41);
    private Point Zombie = new Point(20, 20);
    
    private Point Player = new Point(12,30);
    
    public DrawBot(){
        super();

        
        JPanel content = new JPanel();
        content.setBounds(64,64,650,650);
        setContentPane(content);
        
        setBounds(0,0,750,750);
        
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        DrawBot frame = new DrawBot();
                        frame.setVisible(true);
                        

                        Thread.sleep(1000);
                        frame.repaint();
                    } catch (Exception e) {}
                }
            });
    }
    
    public void paint(Graphics g){
        super.paint(g);
        
        
        g.setColor(Color.darkGray);
        g.fillRect(50, 50, 64, 64);
        
        g.setColor(Color.black);
        for(int i = 0; i < 41; ++i) {
            for(int j = 0; j < 41; ++j) {
                g.drawRect(i*16 +50, j*16 + 50, 16, 16);
            }
        }
        
        g.setColor(Color.green);
        g.fillRect(this.Zombie.x*16 + 50,this.Zombie.y*16 + 50,16,16);
        
        g.setColor(Color.blue);
        double[] a = map.paso(this.Player.x, this.Player.y, this.Zombie.x, this.Zombie.y);
        Point aux = new Point((int)a[0]+Zombie.x,(int)a[1]+Zombie.y);
        //Point a = map.paso(this.Zombie.x, this.Zombie.y, this.Player.x, this.Player.y);

        while(!aux.equals(this.Player)){
            g.fillRect(aux.x*16 + 50,aux.y*16 + 50,16,16);
            a = map.paso(this.Player.x, this.Player.y, aux.x, aux.y);
            aux.setLocation(a[0] + aux.x, a[1] + aux.y);
        }
        /*
        while(!map.paso(this.Player.x, this.Player.y, this.Zombie.x, this.Zombie.y).equals(this.Player)){
            
            this.Zombie= map.paso(this.Player.x, this.Player.y, this.Zombie.x, this.Zombie.y);
            g.fillRect(this.Zombie.x*27 + 50,this.Zombie.y*27 + 50,25,25);
        }*/
        
        g.setColor(Color.yellow);
        g.fillRect(this.Player.x*16 + 50,this.Player.y*16 + 50,16,16);

        
    }
    
      
}
