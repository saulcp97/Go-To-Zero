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
    
    /*
    Tipos de construcciones:
    0: casa simple//Unicamente decorativas
    1: casa con espacio minimo
    
    100: habitación de 20*20 * 8 + 2 Salida, la colocación del cubo padre se hará siguiendo el patron de coordenasdas de los cubos
    
    
    
    84857: Torre de Babel
    1000000: Mansión colosal
    */
    public static void addHouse(world w,int Type, int x, int y, int z){
    switch(Type){           
            case 0:
                
                FillZone(5, 1, x - 10, y - 5, x + 5, y - 5, z, w);
                
                w.addToWorld(x - 10,y    ,z,1);
                w.addToWorld(x - 5 ,y    ,z,1);
                w.addToWorld(x + 5 ,y    ,z,1);
                
                FillZone(5, 1, x - 10, y - 5, x + 5, y, z + 5, w);
                
                break;
            case 1:
                
//--------------Primer Nivel----------------------------------------
                
                //Pared Norte
                FillZone(5, 1, x - 10, y - 10, x + 10, y - 10, z, w);
                //Pared Oeste
                FillZone(5, 1, x - 10, y - 5, x - 10, y + 10, z, w);
                // Pared Este
                FillZone(5, 1, x + 10, y - 5, x + 10, y + 10, z, w);

                //Pared Sur
                w.addToWorld(x - 5 , y + 10, z, 1);
                w.addToWorld(x + 5 , y + 10, z, 1);
                
//--------------Segundo Nivel----------------------------------------               
                FillZone(5, 1, x - 10, y - 10, x + 10, y + 10, z + 5, w);
//--------------Tercer Nivel----------------------------                 
                FillZone(5,1,x - 5, y - 5, x + 5, y + 5, z + 10,w);            
//--------------Pico superior de la casa-----------------                
                w.addToWorld(x    , y, z + 15,1);
                
                
                break;           
            case 100:
                for(int j = 0; j < 8; ++j) {
                    for(int i = 0; i < 20; ++i){
                        w.addToWorld(x + i * 5, y, z + j * 5, 1);
                    }
                    for(int i = 1; i < 19; ++i) {
                        w.addToWorld(x, y + i * 5, z + j * 5, 1);
                        w.addToWorld(x + 95, y + i * 5, z + j * 5, 1);
                    }
                    for(int i = 0; i < 20; ++i){
                        if(j > 2 || (i != 9 && i != 10)){
                            w.addToWorld(x + i * 5, y + 95, z + j * 5, 1);
                        }
                    }
                }             
                break;   
            case 84857://Babel Tower!
                
                break;
            case 1000000://Mansion  51 * 51 10
                
//--------------Suelo Mansión--------------------
                //Torre NorOeste
                FillZone(5, 1, x - 245, y - 5, x - 225, y + 15, z, w);
                FillZone(5, 1, x - 240, y - 10, x - 230, y - 10, z, w);
                FillZone(5, 1, x - 250, y, x - 250, y + 10, z, w);
                //Torre NorEste
                FillZone(5, 1, x + 230, y - 10, x + 240, y - 10, z, w);
                FillZone(5, 1, x + 225, y - 5, x + 245, y + 15, z, w);
                FillZone(5, 1, x + 250, y, x + 250, y + 10, z, w);

                //Muro Norte de la Mansión
                FillZone(5, 1, x - 220, y, x + 220, y + 10, z, w);
                
                //Cimientos Mansion
                FillZone(5, 1, x - 200, y + 15, x + 200, y + 145, z, w);


                FillZone(5, 1, x - 195, y + 145, x - 175, y + 145, z, w);
                FillZone(5, 1, x + 175, y + 145, x + 195, y + 145, z, w);
                
                //Muro Exterior Oeste de la Mansión
                FillZone(5, 1, x - 240, y + 20, x - 230, y + 460, z, w);
                //Muro Exterior Este de la Mansión
                FillZone(5, 1, x + 230, y + 20, x + 240, y + 460, z, w);

                //Cimientos Mansion
                FillZone(5, 1, x - 175, y + 150, x + 175, y + 165, z, w);
                
                w.addToWorld(x - 185, y + 170, z, 1);
                w.addToWorld(x - 180, y + 170, z, 1);
                  
                //Torre y cimientos de la mansión
                //#1
                FillZone(5, 1, x - 185, y + 170, x + 185, y + 170, z, w);
                //#2
                FillZone(5, 1, x - 190, y + 175, x + 190, y + 175, z, w);
                //#3
                FillZone(5, 1, x - 195, y + 180, x + 195, y + 180, z, w);
                //#4
                FillZone(5, 1, x - 200, y + 185, x + 200, y + 195, z, w);
                //#5
                FillZone(5, 1, x - 200, y + 200, x - 150, y + 205, z, w);
                FillZone(5, 1, x + 150, y + 185, x + 200, y + 205, z, w);
                //#6
                FillZone(5, 1, x - 195, y + 210, x - 155, y + 210, z, w);
                FillZone(5, 1, x + 155, y + 210, x + 195, y + 210, z, w);
                //#7
                FillZone(5, 1, x - 190, y + 215, x - 160, y + 215, z, w);
                FillZone(5, 1, x + 160, y + 215, x + 190, y + 215, z, w);
                //#8
                FillZone(5, 1, x - 185, y + 220, x - 165, y + 220, z, w);
                FillZone(5, 1, x + 165, y + 220, x + 185, y + 220, z, w);

                //Cimientos de la Mansión
                //#1
                FillZone(5, 1, x - 95, y + 200, x + 95, y + 225, z, w);
                //#2
                FillZone(5, 1, x - 65, y + 230, x + 65, y + 235, z, w);
                //#3
                FillZone(5, 1, x - 60, y + 240, x + 60, y + 245, z, w);
                //#4
                FillZone(5, 1, x - 55, y + 250, x + 55, y + 255, z, w);
                //#5
                FillZone(5, 1, x - 50, y + 260, x + 50, y + 265, z, w);
                //#6
                FillZone(5, 1, x - 20, y + 270, x + 20, y + 270, z, w);
                //MiniTorres a las puertas de la Mansión
                FillZone(5, 1, x - 45, y + 270, x - 40, y + 270, z, w);
                FillZone(5, 1, x + 40, y + 270, x + 45, y + 270, z, w);
                //Muro Oeste
                FillZone(5, 1, x - 240, y + 270, x - 230, y + 460, z, w);
                //Muro Este
                FillZone(5, 1, x + 230, y + 270, x + 240, y + 460, z, w);
                //Muro Sur, con parte de las torres
                FillZone(5, 1, x - 220, y + 470, x - 50, y + 480, z, w);
                FillZone(5, 1, x + 50, y + 470, x + 220, y + 480, z, w);   
                //Torre Sur-Oeste
                FillZone(5, 1, x - 250, y + 470, x - 250, y + 480, z, w);
                FillZone(5, 1, x - 245, y + 465, x - 225, y + 485, z, w);
                FillZone(5, 1, x - 240, y + 490, x - 230, y + 490, z, w);
                //Torre Sur Este                
                FillZone(5, 1, x + 250, y + 470, x + 250, y + 480, z, w);
                FillZone(5, 1, x + 225, y + 465, x + 245, y + 485, z, w);
                FillZone(5, 1, x + 230, y + 490, x + 240, y + 490, z, w);
                //Torres entrada Jardin
                //Oeste
                FillZone(5, 1, x - 60, y + 485, x - 50, y + 490, z, w);
                FillZone(5, 1, x - 45, y + 475, x - 45, y + 485, z, w);
                //Este
                FillZone(5, 1, x + 50, y + 485, x + 60, y + 490, z, w);
                FillZone(5, 1, x + 45, y + 475, x + 45, y + 485, z, w);
                //Base de cesped para rellenar el Jardin
                for(int i = -48; i <= 48; ++i){
                    for(int j = 0; j <= 96; ++j){
                        w.addToWorld(x + i*5, y + j*5 /*- 250*/, z, 4);                
                    }
                }
//--------------Primer Piso sobre los cimientos De la Mansión-------------------
                /*
                * En este nivel y posteriores me he decidido a cambiar la estructura de la construcción de la Mansión,
                * Ahora la construcción esta dividida en bloques:
                * --> Muros y Torres exterioriores
                * --> Parte Interna del edificio.
                *
                */
//          --->Muros y Torres exteriores
                //Torre NorOeste
                FillZone(5, 1, x - 245, y - 5, x - 225, y + 15, z + 5, w);
                FillZone(5, 1, x - 240, y - 10, x - 230, y - 10, z + 5, w);
                FillZone(5, 1, x - 250, y, x - 250, y + 10, z + 5, w);
                //Muro Norte
                FillZone(5, 1, x - 220, y, x + 220, y + 10, z + 5, w);
                //Torre NorEste
                FillZone(5, 1, x + 230, y - 10, x + 240, y - 10, z + 5, w);
                FillZone(5, 1, x + 225, y - 5, x + 245, y + 15, z + 5, w);
                FillZone(5, 1, x + 250, y, x + 250, y + 10, z + 5, w);
                //Muro Exterior Oeste
                FillZone(5, 1, x - 240, y + 20, x - 230, y + 460, z + 5, w);
                //Muro Exterior Este
                FillZone(5, 1, x + 230, y + 270, x + 240, y + 460, z + 5, w);
                //Torre Sur-Oeste
                FillZone(5, 1, x - 250, y + 470, x - 250, y + 480, z + 5, w);
                FillZone(5, 1, x - 245, y + 465, x - 225, y + 485, z + 5, w);
                FillZone(5, 1, x - 240, y + 490, x - 230, y + 490, z + 5, w);            
                //Torre Sur Este                
                FillZone(5, 1, x + 250, y + 470, x + 250, y + 480, z + 5, w);
                FillZone(5, 1, x + 225, y + 465, x + 245, y + 485, z + 5, w);
                FillZone(5, 1, x + 230, y + 490, x + 240, y + 490, z + 5, w);
                //MuroS Sur, con parte de las torres
                    //Oeste
                FillZone(5, 1, x - 220, y + 470, x - 50, y + 480, z + 5, w);
                    //Este
                FillZone(5, 1, x + 50, y + 470, x + 220, y + 480, z + 5, w);       
                //Torres entrada Jardin
                    //Oeste
                FillZone(5, 1, x - 60, y + 485, x - 50, y + 490, z + 5, w);
                FillZone(5, 1, x - 45, y + 475, x - 45, y + 485, z + 5, w);
                    //Este
                FillZone(5, 1, x + 50, y + 485, x + 60, y + 490, z + 5, w);
                FillZone(5, 1, x + 45, y + 475, x + 45, y + 485, z + 5, w);
//           --> Parte Interna del edificio.
                //Pared del cuerpo profundo de la Mansión
                    //Oeste
                FillZone(5, 1, x - 200, y + 15, x - 200, y + 145, z + 5, w);
                    //Este
                FillZone(5, 1, x + 200, y + 15, x + 200, y + 145, z + 5, w);
                //Muro Final del cuerpo Profundo de la Mansión
                    //Oeste
                FillZone(5, 1, x - 195, y + 145, x - 175, y + 145, z + 5, w);
                    //Este
                FillZone(5, 1, x + 175, y + 145, x + 195, y + 145, z + 5, w);
                //Muro Lateral Medio de la Mansión al norte de las Grandes TOrres
                    //Oeste
                FillZone(5, 1, x - 175, y + 150, x - 175, y + 170, z + 5, w);
                    //Este
                FillZone(5, 1, x + 175, y + 150, x + 175, y + 170, z + 5, w);
                //Muro Norte de las Grandes Torres
                    //Oeste
                FillZone(5, 1, x - 185, y + 170, x - 180, y + 170, z + 5, w);
                    //Este
                FillZone(5, 1, x + 180, y + 170, x + 185, y + 170, z + 5, w);
                //Diagonal superior Grandes Torres
                    //Oeste
                w.addToWorld(x - 190, y + 175, z + 5, 1);
                w.addToWorld(x - 195, y + 180, z + 5, 1);
                    //Este
                w.addToWorld(x + 190, y + 175, z + 5, 1);
                w.addToWorld(x + 195, y + 180, z + 5, 1);
                //Lados laterales de las Grandes Torres
                    //Oeste
                FillZone(5, 1, x - 200, y + 185, x - 200, y + 205, z + 5, w);
                FillZone(5, 1, x - 150, y + 200, x - 150, y + 205, z + 5, w);
                    //Este
                FillZone(5, 1, x + 200, y + 185, x + 200, y + 205, z + 5, w);
                FillZone(5, 1, x + 150, y + 200, x + 150, y + 205, z + 5, w);
                //Diagonales Inferiores Grandes Torres
                    //Oeste
                        //Oeste
                w.addToWorld(x - 195, y + 210, z + 5, 1);
                w.addToWorld(x - 190, y + 215, z + 5, 1);
                        //Este
                w.addToWorld(x - 155, y + 210, z + 5, 1);
                w.addToWorld(x - 160, y + 215, z + 5, 1);
                    //Este
                        //Oeste
                w.addToWorld(x + 155, y + 210, z + 5, 1);
                w.addToWorld(x + 160, y + 215, z + 5, 1);
                        //Este
                w.addToWorld(x + 195, y + 210, z + 5, 1);
                w.addToWorld(x + 190, y + 215, z + 5, 1);
                //Muro sur de las Grandes Torres
                    //Oeste
                FillZone(5, 1, x - 185, y + 220, x - 165, y + 220, z + 5, w);  
                    //Este
                FillZone(5, 1, x + 165, y + 220, x + 185, y + 220, z + 5, w);  
                //La parte sur del cuerpo de la mansión.
                    //Oeste
                FillZone(5, 1, x - 150, y + 195, x - 95, y + 195, z + 5, w);
                FillZone(5, 1, x - 95, y + 200, x - 95, y + 225, z + 5, w);
                FillZone(5, 1, x - 90, y + 225, x - 65, y + 225, z + 5, w);
                    //Este
                FillZone(5, 1, x + 95, y + 195, x + 150, y + 195, z + 5, w);
                FillZone(5, 1, x + 95, y + 200, x + 95, y + 225, z + 5, w);
                FillZone(5, 1, x + 65, y + 225, x + 90, y + 225, z + 5, w);
                //Diagonales Muro Mansion
                    //Oeste
                FillZone(5, 1, x - 65, y + 230, x - 65, y + 235, z + 5, w);
                FillZone(5, 1, x - 60, y + 240, x - 60, y + 245, z + 5, w);
                FillZone(5, 1, x - 55, y + 250, x - 55, y + 255, z + 5, w);
                FillZone(5, 1, x - 50, y + 260, x - 50, y + 265, z + 5, w);
                    //Este
                FillZone(5, 1, x + 65, y + 230, x + 65, y + 235, z + 5, w);
                FillZone(5, 1, x + 60, y + 240, x + 60, y + 245, z + 5, w);
                FillZone(5, 1, x + 55, y + 250, x + 55, y + 255, z + 5, w);
                FillZone(5, 1, x + 50, y + 260, x + 50, y + 265, z + 5, w);
                //Mini Torre Sur
                    //Oeste
                FillZone(5, 1, x - 45, y + 270, x - 40, y + 270, z + 5, w);
                    //Este
                FillZone(5, 1, x + 40, y + 270, x + 45, y + 270, z + 5, w);
                //Puerta Del edifcio
                    //Oeste
                FillZone(5, 1, x - 35, y + 265, x - 25, y + 265, z + 5, w);
                w.addToWorld(x - 20, y + 270, z + 5, 1);
                    //Este
                FillZone(5, 1, x + 25, y + 265, x + 35, y + 265, z + 5, w);
                w.addToWorld(x + 20, y + 270, z + 5, 1);
                

                
//--------------2o NIVEL-------------------
                //Muro Norte
                FillZone(5, 1, x - 220, y, x + 220, y + 10, z + 10, w);                                
//--------------3er NIVEL-------------------
                //Muro Norte
                FillZone(5, 1, x - 220, y, x + 220, y + 10, z + 15, w);                     
//--------------4to NIVEL-------------------               
                //Muro Norte
                FillZone(5, 1, x - 220, y, x + 220, y + 10, z + 20, w);      
//--------------5to NIVEL-------------------
                //Muro Norte
                FillZone(5, 1, x - 220, y, x + 220, y + 10, z + 25, w);   
                break;
        }    
    }
    
    public static void FillZone(int factor, int Tipo, int xi, int yi, int xf, int yf, int z, world w){
        for(int i = xi; i <= xf; i += factor){
            for(int j = yi; j <= yf; j += factor){
                w.addToWorld(i, j, z, Tipo);
            }
        }
    }
}
