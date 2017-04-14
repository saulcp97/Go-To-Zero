/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gotozero.Constructor;

import gotozero.world;

/**
 *
 * @author saulc
 */
public class Edify {

    public static void addHouse(world w,int Type, int x, int y, int z){
    switch(Type){           
            case 0:
                w.addToWorld(x - 10,y - 5,z,1);
                w.addToWorld(x - 10,y    ,z,1);
                w.addToWorld(x - 5 ,y - 5,z,1);
                w.addToWorld(x - 5 ,y    ,z,1);
                w.addToWorld(x     ,y - 5,z,1);
                w.addToWorld(x + 5 ,y - 5,z,1);
                w.addToWorld(x + 5 ,y    ,z,1);
             
                w.addToWorld(x - 10,y - 5,z + 5,1);
                w.addToWorld(x - 10,y    ,z + 5,1);
                w.addToWorld(x - 5 ,y - 5,z + 5,1);
                w.addToWorld(x - 5 ,y    ,z + 5,1);
                w.addToWorld(x     ,y - 5,z + 5,1);
                w.addToWorld(x     ,y    ,z + 5,1);
                w.addToWorld(x + 5 ,y - 5,z + 5,1);
                w.addToWorld(x + 5 ,y    ,z + 5,1);
                
                break;
            case 1:
                w.addToWorld(x - 10, y - 10, z, 1);
                w.addToWorld(x - 5 , y - 10, z, 1);
                w.addToWorld(x     , y - 10, z, 1);
                w.addToWorld(x + 5 , y - 10, z, 1);
                w.addToWorld(x + 10, y - 10, z, 1);
                
                w.addToWorld(x - 10, y - 5, z, 1);
                w.addToWorld(x + 10, y - 5, z, 1);
                
                w.addToWorld(x - 10, y    , z, 1);
                w.addToWorld(x + 10, y    , z, 1);
                
                w.addToWorld(x - 10, y + 5, z, 1);
                w.addToWorld(x + 10, y + 5, z, 1);
                
                w.addToWorld(x - 10, y + 10, z, 1);
                w.addToWorld(x - 5 , y + 10, z, 1);
                w.addToWorld(x + 5 , y + 10, z, 1);
                w.addToWorld(x + 10, y + 10, z, 1);
                
                
                w.addToWorld(x - 10, y - 10, z + 5, 1);
                w.addToWorld(x - 5 , y - 10, z + 5, 1);
                w.addToWorld(x     , y - 10, z + 5, 1);
                w.addToWorld(x + 5 , y - 10, z + 5, 1);
                w.addToWorld(x + 10, y - 10, z + 5, 1);
                w.addToWorld(x - 10, y - 5, z + 5, 1);
                w.addToWorld(x + 10, y - 5, z + 5, 1);
                w.addToWorld(x - 10, y    , z + 5, 1);
                w.addToWorld(x + 10, y    , z + 5, 1);
                w.addToWorld(x - 10, y + 5, z + 5, 1);
                w.addToWorld(x + 10, y + 5, z + 5, 1);
                w.addToWorld(x - 10, y + 10, z + 5, 1);
                w.addToWorld(x - 5 , y + 10, z + 5, 1);
                w.addToWorld(x     , y + 10, z + 5, 1);
                w.addToWorld(x + 5 , y + 10, z + 5, 1);
                w.addToWorld(x + 10, y + 10, z + 5, 1);
                
                w.addToWorld(x - 5, y - 5, z + 10,1);
                w.addToWorld(x    , y - 5, z + 10,1);
                w.addToWorld(x + 5, y - 5, z + 10,1);
                w.addToWorld(x - 5, y    , z + 10,1);
                w.addToWorld(x + 5, y    , z + 10,1);
                w.addToWorld(x - 5, y + 5, z + 10,1);
                w.addToWorld(x    , y + 5, z + 10,1);
                w.addToWorld(x + 5, y + 5, z + 10,1);
                
                w.addToWorld(x    , y, z + 15,1);
                
                break;
            case 1000000://Mansion  51 * 51 10
                
                //addToWorld(x - 250, y - 250, z, 1);
                
                w.addToWorld(x - 240, y - 10, z, 1);
                w.addToWorld(x - 235, y - 10, z, 1);
                w.addToWorld(x - 230, y - 10, z, 1);
                
                w.addToWorld(x + 230, y - 10, z, 1);
                w.addToWorld(x + 235, y - 10, z, 1);
                w.addToWorld(x + 240, y - 10, z, 1);
                
                w.addToWorld(x - 245, y - 5, z, 1);
                w.addToWorld(x - 225, y - 5, z, 1);
                
                w.addToWorld(x + 225, y - 5, z, 1);
                w.addToWorld(x + 245, y - 5, z, 1);
                
                w.addToWorld(x - 250, y, z, 1);
                w.addToWorld(x + 250, y, z, 1);
                
                
                for(int i = -44; i <= 44; ++i){
                    w.addToWorld(x + i*5, y /*- 250*/, z, 1);                
                }
                
                w.addToWorld(x - 250, y + 5, z, 1);
                w.addToWorld(x + 250, y + 5, z, 1);
                w.addToWorld(x - 250, y + 10, z, 1);
                w.addToWorld(x + 250, y + 10, z, 1);

                for(int i = -45; i <= 45; ++i){
                    w.addToWorld(x + i*5, y + 10, z, 1);                
                }
                
                w.addToWorld(x - 245, y + 15, z, 1);
                w.addToWorld(x - 230, y + 15, z, 1);
                w.addToWorld(x - 200, y + 15, z, 1);
                w.addToWorld(x + 200, y + 15, z, 1);
                w.addToWorld(x + 230, y + 15, z, 1);
                w.addToWorld(x + 245, y + 15, z, 1);
                
                w.addToWorld(x - 240, y + 20, z, 1);
                w.addToWorld(x - 230, y + 20, z, 1);
                w.addToWorld(x - 200, y + 20, z, 1);
                w.addToWorld(x + 200, y + 20, z, 1);
                w.addToWorld(x + 230, y + 20, z, 1);
                w.addToWorld(x + 240, y + 20, z, 1);
                
                for(int i = 0; i <= 25; ++i) {
                    w.addToWorld(x - 240, y + 20 + i*5, z, 1);
                    w.addToWorld(x - 230, y + 20 + i*5, z, 1);
                    w.addToWorld(x - 200, y + 20 + i*5, z, 1);
                    w.addToWorld(x + 200, y + 20 + i*5, z, 1);
                    w.addToWorld(x + 230, y + 20 + i*5, z, 1);
                    w.addToWorld(x + 240, y + 20 + i*5, z, 1);                    
                }
                w.addToWorld(x - 195, y + 145, z, 1);
                w.addToWorld(x + 195, y + 145, z, 1);
                
                w.addToWorld(x - 190, y + 145, z, 1);
                w.addToWorld(x + 190, y + 145, z, 1);
                
                w.addToWorld(x - 185, y + 145, z, 1);
                w.addToWorld(x + 185, y + 145, z, 1);
                
                w.addToWorld(x - 180, y + 145, z, 1);
                w.addToWorld(x + 180, y + 145, z, 1);
                
                w.addToWorld(x - 175, y + 145, z, 1);
                w.addToWorld(x + 175, y + 145, z, 1);
                
                
                w.addToWorld(x - 240, y + 150, z, 1);
                w.addToWorld(x - 230, y + 150, z, 1);
                w.addToWorld(x - 175, y + 150, z, 1);
                w.addToWorld(x + 175, y + 150, z, 1);
                w.addToWorld(x + 230, y + 150, z, 1);
                w.addToWorld(x + 240, y + 150, z, 1);
                
                w.addToWorld(x - 240, y + 155, z, 1);
                w.addToWorld(x - 230, y + 155, z, 1);
                w.addToWorld(x - 175, y + 155, z, 1);
                w.addToWorld(x + 175, y + 155, z, 1);
                w.addToWorld(x + 230, y + 155, z, 1);
                w.addToWorld(x + 240, y + 155, z, 1);
                
                w.addToWorld(x - 240, y + 160, z, 1);
                w.addToWorld(x - 230, y + 160, z, 1);
                w.addToWorld(x - 175, y + 160, z, 1);
                w.addToWorld(x + 175, y + 160, z, 1);
                w.addToWorld(x + 230, y + 160, z, 1);
                w.addToWorld(x + 240, y + 160, z, 1);
                
                w.addToWorld(x - 240, y + 165, z, 1);
                w.addToWorld(x - 230, y + 165, z, 1);
                w.addToWorld(x - 175, y + 165, z, 1);
                w.addToWorld(x + 175, y + 165, z, 1);
                w.addToWorld(x + 230, y + 165, z, 1);
                w.addToWorld(x + 240, y + 165, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 170, z, 1);
                w.addToWorld(x - 230, y + 170, z, 1);
                
                w.addToWorld(x - 185, y + 170, z, 1);
                w.addToWorld(x - 180, y + 170, z, 1);
                w.addToWorld(x - 175, y + 170, z, 1); 
                w.addToWorld(x - 170, y + 170, z, 1);
                w.addToWorld(x - 165, y + 170, z, 1);
                
                w.addToWorld(x + 165, y + 170, z, 1);
                w.addToWorld(x + 170, y + 170, z, 1);
                w.addToWorld(x + 175, y + 170, z, 1);
                w.addToWorld(x + 180, y + 170, z, 1);
                w.addToWorld(x + 185, y + 170, z, 1);
                
                w.addToWorld(x + 230, y + 170, z, 1);
                w.addToWorld(x + 240, y + 170, z, 1);                
                


                
                w.addToWorld(x - 240, y + 175, z, 1);
                w.addToWorld(x - 230, y + 175, z, 1);
                
                w.addToWorld(x - 190, y + 175, z, 1);
                w.addToWorld(x - 160, y + 175, z, 1);
                
                w.addToWorld(x + 160, y + 175, z, 1);
                w.addToWorld(x + 190, y + 175, z, 1);
                
                w.addToWorld(x + 230, y + 175, z, 1);
                w.addToWorld(x + 240, y + 175, z, 1);  
                
                

                
                w.addToWorld(x - 240, y + 180, z, 1);
                w.addToWorld(x - 230, y + 180, z, 1);
                
                w.addToWorld(x - 195, y + 180, z, 1);
                w.addToWorld(x - 155, y + 180, z, 1);
                
                w.addToWorld(x + 155, y + 180, z, 1);
                w.addToWorld(x + 195, y + 180, z, 1);
                
                w.addToWorld(x + 230, y + 180, z, 1);
                w.addToWorld(x + 240, y + 180, z, 1);
                
                
                
                w.addToWorld(x - 240, y + 185, z, 1);
                w.addToWorld(x - 230, y + 185, z, 1);
                
                w.addToWorld(x - 200, y + 185, z, 1);
                w.addToWorld(x - 150, y + 185, z, 1);
                
                w.addToWorld(x + 150, y + 185, z, 1);
                w.addToWorld(x + 200, y + 185, z, 1);
                
                w.addToWorld(x + 230, y + 185, z, 1);
                w.addToWorld(x + 240, y + 185, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 190, z, 1);
                w.addToWorld(x - 230, y + 190, z, 1);
                
                w.addToWorld(x - 200, y + 190, z, 1);
                w.addToWorld(x - 150, y + 190, z, 1);
                
                w.addToWorld(x + 150, y + 190, z, 1);
                w.addToWorld(x + 200, y + 190, z, 1);
                
                w.addToWorld(x + 230, y + 190, z, 1);
                w.addToWorld(x + 240, y + 190, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 195, z, 1);
                w.addToWorld(x - 230, y + 195, z, 1);
                
                w.addToWorld(x - 200, y + 195, z, 1);
                w.addToWorld(x - 150, y + 195, z, 1);
                
                w.addToWorld(x - 145, y + 195, z, 1);
                w.addToWorld(x - 140, y + 195, z, 1);
                w.addToWorld(x - 135, y + 195, z, 1);
                w.addToWorld(x - 130, y + 195, z, 1);
                w.addToWorld(x - 125, y + 195, z, 1);
                
                w.addToWorld(x - 120, y + 195, z, 1);
                w.addToWorld(x - 115, y + 195, z, 1);
                w.addToWorld(x - 110, y + 195, z, 1);
                w.addToWorld(x - 105, y + 195, z, 1);
                w.addToWorld(x - 100, y + 195, z, 1);

                w.addToWorld(x + 100, y + 195, z, 1);
                w.addToWorld(x + 105, y + 195, z, 1);
                w.addToWorld(x + 110, y + 195, z, 1);
                w.addToWorld(x + 115, y + 195, z, 1);
                w.addToWorld(x + 120, y + 195, z, 1);
                
                w.addToWorld(x + 125, y + 195, z, 1);
                w.addToWorld(x + 130, y + 195, z, 1);
                w.addToWorld(x + 135, y + 195, z, 1);
                w.addToWorld(x + 140, y + 195, z, 1);
                w.addToWorld(x + 145, y + 195, z, 1);
                
                w.addToWorld(x + 150, y + 195, z, 1);
                w.addToWorld(x + 200, y + 195, z, 1);
                
                w.addToWorld(x + 230, y + 195, z, 1);
                w.addToWorld(x + 240, y + 195, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 200, z, 1);
                w.addToWorld(x - 230, y + 200, z, 1);
     
                w.addToWorld(x - 200, y + 200, z, 1);
                w.addToWorld(x - 150, y + 200, z, 1);

                w.addToWorld(x - 95, y + 200, z, 1);
                w.addToWorld(x + 95, y + 200, z, 1);

                w.addToWorld(x + 150, y + 200, z, 1);
                w.addToWorld(x + 200, y + 200, z, 1);
                
                w.addToWorld(x + 230, y + 200, z, 1);
                w.addToWorld(x + 240, y + 200, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 205, z, 1);
                w.addToWorld(x - 230, y + 205, z, 1);
     
                w.addToWorld(x - 200, y + 205, z, 1);
                w.addToWorld(x - 150, y + 205, z, 1);

                w.addToWorld(x - 95, y + 205, z, 1);
                w.addToWorld(x + 95, y + 205, z, 1);

                w.addToWorld(x + 150, y + 205, z, 1);
                w.addToWorld(x + 200, y + 205, z, 1);
                
                w.addToWorld(x + 230, y + 205, z, 1);
                w.addToWorld(x + 240, y + 205, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 210, z, 1);
                w.addToWorld(x - 230, y + 210, z, 1);
     
                w.addToWorld(x - 195, y + 210, z, 1);
                w.addToWorld(x - 155, y + 210, z, 1);

                w.addToWorld(x - 95, y + 210, z, 1);
                w.addToWorld(x + 95, y + 210, z, 1);

                w.addToWorld(x + 155, y + 210, z, 1);
                w.addToWorld(x + 195, y + 210, z, 1);
                
                w.addToWorld(x + 230, y + 210, z, 1);
                w.addToWorld(x + 240, y + 210, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 215, z, 1);
                w.addToWorld(x - 230, y + 215, z, 1);
     
                w.addToWorld(x - 190, y + 215, z, 1);
                w.addToWorld(x - 160, y + 215, z, 1);

                w.addToWorld(x - 95, y + 215, z, 1);
                w.addToWorld(x + 95, y + 215, z, 1);

                w.addToWorld(x + 160, y + 215, z, 1);
                w.addToWorld(x + 190, y + 215, z, 1);
                
                w.addToWorld(x + 230, y + 215, z, 1);
                w.addToWorld(x + 240, y + 215, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 220, z, 1);
                w.addToWorld(x - 230, y + 220, z, 1);
     
                w.addToWorld(x - 185, y + 220, z, 1);
                w.addToWorld(x - 180, y + 220, z, 1);
                w.addToWorld(x - 175, y + 220, z, 1);
                w.addToWorld(x - 170, y + 220, z, 1);
                w.addToWorld(x - 165, y + 220, z, 1);

                w.addToWorld(x - 95, y + 220, z, 1);
                w.addToWorld(x + 95, y + 220, z, 1);

                w.addToWorld(x + 165, y + 220, z, 1);
                w.addToWorld(x + 170, y + 220, z, 1);
                w.addToWorld(x + 175, y + 220, z, 1);
                w.addToWorld(x + 180, y + 220, z, 1);
                w.addToWorld(x + 185, y + 220, z, 1);
                
                w.addToWorld(x + 230, y + 220, z, 1);
                w.addToWorld(x + 240, y + 220, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 225, z, 1);
                w.addToWorld(x - 230, y + 225, z, 1);
                
                w.addToWorld(x - 95, y + 225, z, 1);
                w.addToWorld(x - 90, y + 225, z, 1);
                w.addToWorld(x - 85, y + 225, z, 1);
                w.addToWorld(x - 80, y + 225, z, 1);
                w.addToWorld(x - 75, y + 225, z, 1);
                w.addToWorld(x - 70, y + 225, z, 1);
                
                w.addToWorld(x + 70, y + 225, z, 1);
                w.addToWorld(x + 75, y + 225, z, 1);
                w.addToWorld(x + 80, y + 225, z, 1);
                w.addToWorld(x + 85, y + 225, z, 1);
                w.addToWorld(x + 90, y + 225, z, 1);
                w.addToWorld(x + 95, y + 225, z, 1);
                
                w.addToWorld(x + 230, y + 225, z, 1);
                w.addToWorld(x + 240, y + 225, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 230, z, 1);
                w.addToWorld(x - 230, y + 230, z, 1);
                
                w.addToWorld(x - 65, y + 230, z, 1);
                w.addToWorld(x + 65, y + 230, z, 1);
                
                w.addToWorld(x + 230, y + 230, z, 1);
                w.addToWorld(x + 240, y + 230, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 235, z, 1);
                w.addToWorld(x - 230, y + 235, z, 1);
                
                w.addToWorld(x - 65, y + 235, z, 1);
                w.addToWorld(x + 65, y + 235, z, 1);
                
                w.addToWorld(x + 230, y + 235, z, 1);
                w.addToWorld(x + 240, y + 235, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 240, z, 1);
                w.addToWorld(x - 230, y + 240, z, 1);
                
                w.addToWorld(x - 60, y + 240, z, 1);
                w.addToWorld(x + 60, y + 240, z, 1);
                
                w.addToWorld(x + 230, y + 240, z, 1);
                w.addToWorld(x + 240, y + 240, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 245, z, 1);
                w.addToWorld(x - 230, y + 245, z, 1);
                
                w.addToWorld(x - 60, y + 245, z, 1);
                w.addToWorld(x + 60, y + 245, z, 1);
                
                w.addToWorld(x + 230, y + 245, z, 1);
                w.addToWorld(x + 240, y + 245, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 250, z, 1);
                w.addToWorld(x - 230, y + 250, z, 1);
                
                w.addToWorld(x - 55, y + 250, z, 1);
                w.addToWorld(x + 55, y + 250, z, 1);
                
                w.addToWorld(x + 230, y + 250, z, 1);
                w.addToWorld(x + 240, y + 250, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 255, z, 1);
                w.addToWorld(x - 230, y + 255, z, 1);
                
                w.addToWorld(x - 55, y + 255, z, 1);
                w.addToWorld(x - 45, y + 255, z, 1);
                w.addToWorld(x - 40, y + 255, z, 1);
                w.addToWorld(x + 40, y + 255, z, 1);
                w.addToWorld(x + 45, y + 255, z, 1);
                w.addToWorld(x + 55, y + 255, z, 1);
                
                w.addToWorld(x + 230, y + 255, z, 1);
                w.addToWorld(x + 240, y + 255, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 260, z, 1);
                w.addToWorld(x - 230, y + 260, z, 1);
                
                w.addToWorld(x - 50, y + 260, z, 1);
                w.addToWorld(x - 35, y + 260, z, 1);
                w.addToWorld(x + 35, y + 260, z, 1);
                w.addToWorld(x + 50, y + 260, z, 1);
                
                w.addToWorld(x + 230, y + 260, z, 1);
                w.addToWorld(x + 240, y + 260, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 265, z, 1);
                w.addToWorld(x - 230, y + 265, z, 1);
                
                w.addToWorld(x - 50, y + 265, z, 1);
                w.addToWorld(x - 35, y + 265, z, 1);
                w.addToWorld(x - 30, y + 265, z, 1);
                w.addToWorld(x - 25, y + 265, z, 1);
                
                w.addToWorld(x + 25, y + 265, z, 1);
                w.addToWorld(x + 30, y + 265, z, 1);
                w.addToWorld(x + 35, y + 265, z, 1);
                w.addToWorld(x + 50, y + 265, z, 1);
                
                w.addToWorld(x + 230, y + 265, z, 1);
                w.addToWorld(x + 240, y + 265, z, 1);
                
                
                
                
                w.addToWorld(x - 240, y + 270, z, 1);
                w.addToWorld(x - 230, y + 270, z, 1);
                
                w.addToWorld(x - 45, y + 270, z, 1);
                w.addToWorld(x - 40, y + 270, z, 1);
                w.addToWorld(x - 20, y + 270, z, 1);
                w.addToWorld(x + 20, y + 270, z, 1);
                w.addToWorld(x + 40, y + 270, z, 1);
                w.addToWorld(x + 45, y + 270, z, 1);

                w.addToWorld(x + 230, y + 270, z, 1);
                w.addToWorld(x + 240, y + 270, z, 1);
                break;
        }    
    }   
}
