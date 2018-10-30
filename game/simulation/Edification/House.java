package com.mygdx.game.simulation.Edification;


import com.mygdx.game.Chunk;
import com.mygdx.game.Cubos.Decoration;

public class House extends Edify {

    public static House casa1 = new House("0",(byte)-128,0,0,0);


    public static House cafe1 = new House("0",(byte)-126,0,0,0);

    //Start es el Chunk en el que empieza
    private House(String start, byte model, int x, int y, int z){
        this.nameEdify = "Casa";
        this.startChunkName = start;

        this.x = x;
        this.y = y;
        this.z = z;

        this.model = model;
        switch (model){
            case -128:
                this.blocksW = 10;
                this.blocksH = 6;
                this.blocksD = 5;

                this.blocks = new byte[300];
                //20 de amplaria / 2 -> 10

                for (int i = 0; i < this.blocks.length; ++i) {
                    this.blocks[i] = Byte.MIN_VALUE;
                }

                for(int i = 0; i < 60; ++i) {
                    this.blocks[i] = 2;
                }

                this.blocks[60] = 10;
                this.blocks[61] = 10;
                this.blocks[62] = 10;
                this.blocks[63] = 10;
                this.blocks[64] = 10;
                this.blocks[65] = 10;
                this.blocks[66] = 10;
                this.blocks[67] = 10;
                this.blocks[68] = 10;
                this.blocks[69] = 10;

                this.blocks[70] = 10;
                this.blocks[79] = 10;

                this.blocks[80] = 10;
                this.blocks[89] = 10;

                this.blocks[90] = 10;
                this.blocks[99] = 10;

                this.blocks[100] = 10;
                this.blocks[109] = 10;

                this.blocks[110] = 10;
                this.blocks[111] = 10;
                this.blocks[112] = 10;
                this.blocks[113] = 10;
                //this.blocks[114] = 10;//Parte inferior de la Puerta

                this.blocks[114] = -127;

                //FINAL de la Puerta

                this.blocks[115] = 10;
                this.blocks[116] = 10;
                this.blocks[117] = 10;
                this.blocks[118] = 10;
                this.blocks[119] = 10;

                //Segundo piso---------------------------------------------------------------------------------------------------

                this.blocks[120] = 14;
                this.blocks[121] = 14;
                this.blocks[122] = 14;
                this.blocks[123] = 14;
                this.blocks[124] = 14;

                this.blocks[125] = 14;
                this.blocks[126] = 14;
                this.blocks[127] = 14;
                this.blocks[128] = 14;
                this.blocks[129] = 14;

                this.blocks[130] = 14;
                this.blocks[139] = 14;

                this.blocks[140] = 14;
                this.blocks[149] = 14;

                this.blocks[150] = 14;
                this.blocks[159] = 14;

                this.blocks[160] = 14;
                this.blocks[169] = 14;

                this.blocks[170] = 14;
                this.blocks[171] = 14;
                this.blocks[172] = 14;
                this.blocks[173] = 14;
                //this.blocks[114] = 10;

                this.blocks[175] = 14;
                this.blocks[176] = 14;
                this.blocks[177] = 14;
                this.blocks[178] = 14;
                this.blocks[179] = 14;

                //Tercer piso---------------------------------------------------------------------------------------------------

                this.blocks[180] = 14;
                this.blocks[181] = 14;
                this.blocks[182] = 14;
                this.blocks[183] = 14;
                this.blocks[184] = 14;

                this.blocks[185] = 14;
                this.blocks[186] = 14;
                this.blocks[187] = 14;
                this.blocks[188] = 14;
                this.blocks[189] = 14;

                this.blocks[190] = 14;
                this.blocks[199] = 14;

                this.blocks[200] = 14;
                this.blocks[209] = 14;

                this.blocks[210] = 14;
                this.blocks[219] = 14;

                this.blocks[220] = 14;
                this.blocks[229] = 14;

                this.blocks[230] = 14;
                this.blocks[231] = 14;
                this.blocks[232] = 14;

                this.blocks[233] = 14;
                this.blocks[234] = 14;

                this.blocks[235] = 14;
                this.blocks[236] = 14;
                this.blocks[237] = 14;
                this.blocks[238] = 14;
                this.blocks[239] = 14;

                for(int i = 251; i < 290; ++i){
                    if(i % 10 != 0 && i % 10 != 9) {
                        this.blocks[i] = 12;
                    }
                }

                break;

            case -127:
                this.blocks = new byte[10];
                break;

            case -126: //Café 1
                this.blocksW = 15;
                this.blocksH = 15;
                this.blocksD = 5;

                this.blocks = new byte[this.blocksW * this.blocksH * this.blocksD];
                //20 de amplaria / 2 -> 10

                for (int i = 0; i < this.blocks.length; ++i) {
                    this.blocks[i] = Byte.MIN_VALUE;
                }

                for(int i = 0; i < this.blocksW * this.blocksH; ++i) {
                    this.blocks[i] = 2;
                }

                for (int i = this.blocksW * this.blocksH; i < this.blocks.length; ++i) {
                    if(i % this.blocksW == 0
                            || i % this.blocksW == this.blocksW - 1
                            || i % (this.blocksW * this.blocksH) < this.blocksW
                            || i % (this.blocksW * this.blocksH) > (this.blocksW * (this.blocksH - 1))) {
                        this.blocks[i] = 14;
                    }
                }

                for (int i  = 1; i < this.blocksD; ++i) {
                    this.blocks[225 * i + this.blocksW * 1 + 5] = 14;
                    this.blocks[225 * i + this.blocksW * 2 + 5] = 14;
                    this.blocks[225 * i + this.blocksW * 3 + 5] = 14;
                    this.blocks[225 * i + this.blocksW * 4 + 5] = 14;
                    this.blocks[225 * i + this.blocksW * 4 + 1] = 14;
                    this.blocks[225 * i + this.blocksW * 4 + 2] = 14;
                    this.blocks[225 * i + this.blocksW * 4 + 3] = 14;
                    this.blocks[225 * i + this.blocksW * 5 + 5] = 14;
                    this.blocks[225 * i + this.blocksW * 6 + 5] = 14;

                    this.blocks[225 * i + this.blocksW * 8 + 5] = 14;
                    this.blocks[225 * i + this.blocksW * 9 + 1] = 14;
                    this.blocks[225 * i + this.blocksW * 9 + 2] = 14;
                    this.blocks[225 * i + this.blocksW * 9 + 3] = 14;
                    this.blocks[225 * i + this.blocksW * 9 + 4] = 14;
                    this.blocks[225 * i + this.blocksW * 9 + 5] = 14;
                }


                this.blocks[225 + this.blocksW * 2 + 11] = -126;//Mesa
                this.blocks[225 + this.blocksW * 1 + 11] = -125;//Silla
                this.blocks[225 + this.blocksW * 2 + 10] = -125;//Silla
                this.blocks[225 + this.blocksW * 2 + 12] = -125;//Silla
                this.blocks[225 + this.blocksW * 3 + 11] = -125;//Silla

                this.blocks[225 + this.blocksW * 5 + 12] = -126;//Mesa
                this.blocks[225 + this.blocksW * 4 + 12] = -125;//Silla
                this.blocks[225 + this.blocksW * 5 + 11] = -125;//Silla
                this.blocks[225 + this.blocksW * 5 + 13] = -125;//Silla
                this.blocks[225 + this.blocksW * 6 + 12] = -125;//Silla

                this.blocks[225 + this.blocksW * 9 + 12] = -126;//Mesa
                this.blocks[225 + this.blocksW * 8 + 12] = -125;//Silla
                this.blocks[225 + this.blocksW * 9 + 11] = -125;//Silla
                this.blocks[225 + this.blocksW * 9 + 13] = -125;//Silla
                this.blocks[225 + this.blocksW * 10 + 12] = -125;//Silla

                this.blocks[225 + this.blocksW * 12 + 11] = -126;//Mesa
                this.blocks[225 + this.blocksW * 11 + 11] = -125;//Silla
                this.blocks[225 + this.blocksW * 12 + 10] = -125;//Silla
                this.blocks[225 + this.blocksW * 12 + 12] = -125;//Silla
                this.blocks[225 + this.blocksW * 13 + 11] = -125;//Silla

                this.blocks[225 + this.blocksW * 3 + 6] = 12;
                this.blocks[225 + this.blocksW * 3 + 8] = 12;
                this.blocks[225 + this.blocksW * 4 + 8] = 12;
                this.blocks[225 + this.blocksW * 5 + 8] = 12;
                this.blocks[225 + this.blocksW * 6 + 8] = 12;
                this.blocks[225 + this.blocksW * 7 + 8] = 12;
                this.blocks[225 + this.blocksW * 8 + 8] = 12;
                this.blocks[225 + this.blocksW * 9 + 6] = 12;
                this.blocks[225 + this.blocksW * 9 + 7] = 12;
                this.blocks[225 + this.blocksW * 9 + 8] = 12;


                this.blocks[225 * 2 - 7] = -127;
                this.blocks[225 * 3 - 7] = Byte.MIN_VALUE;


                break;
        }
    }

    public int getDeepCubes() {
        return this.blocksD;
    }

    public static void addPosition(Chunk chunk,byte model, int x, int y, int z){

        switch(model) {
            case -128:
                for (int i = 0; i < casa1.blocks.length; ++i) {//10x6
                    if (casa1.blocks[i] != Byte.MIN_VALUE && casa1.blocks[i] != -127) {
                        if (!chunk.tryToAdd(x + i % 10, y + i / 10 - ((i / 60) * 6), z + i / 60, casa1.blocks[i])) {
                            chunk.tryToSetBlock(x + i % 10, y + i / 10 - ((i / 60) * 6), z + i / 60, casa1.blocks[i], (byte) 0);
                        }
                    } else if(casa1.blocks[i] == -127) {
                        if (!chunk.tryToAddBlock(new Decoration(),x + i % casa1.blocksW, y + i / casa1.blocksW - ((i /(casa1.blocksW * casa1.blocksH)) * casa1.blocksH), z + i / (casa1.blocksW * casa1.blocksH))) {
                            System.out.println("Error sistema Procedural: Objeto presente en base de la puerta");
                        }
                    }
                }
                chunk.addZona(new Zona(casa1, x, y, z));
                break;
            case -126:
                for (int i = 0; i < cafe1.blocks.length; ++i) {//10x6
                    switch (cafe1.blocks[i]) {
                        case Byte.MIN_VALUE:
                            break;
                        case -127:
                            Decoration b = new Decoration(); //Puerta
                            b.setOrient((byte) -1);
                            if (!chunk.tryToAddBlock(b,x + i % cafe1.blocksW, y + i / cafe1.blocksW - ((i /(cafe1.blocksW * cafe1.blocksH)) * cafe1.blocksH), z + i / (cafe1.blocksW * cafe1.blocksH))) {
                                System.out.println("Error sistema Procedural: Objeto presente en base de la puerta");
                            }
                            break;
                        case -126:
                            b = new Decoration();
                            b.setOrient((byte)5);
                            if (!chunk.tryToAddBlock(b,x + i % cafe1.blocksW, y + i / cafe1.blocksW - ((i /(cafe1.blocksW * cafe1.blocksH)) * cafe1.blocksH), z + i / (cafe1.blocksW * cafe1.blocksH))) {
                                System.out.println("Error sistema Procedural: Objeto presente en base de la puerta");
                            }
                            break;
                        case -125:
                            b = new Decoration();
                            b.setOrient((byte)4);
                            if (!chunk.tryToAddBlock(b,x + i % cafe1.blocksW, y + i / cafe1.blocksW - ((i /(cafe1.blocksW * cafe1.blocksH)) * cafe1.blocksH), z + i / (cafe1.blocksW * cafe1.blocksH))) {
                                System.out.println("Error sistema Procedural: Objeto presente en base de la puerta");
                            }
                            break;
                        default:
                            if (!chunk.tryToAdd(x + i % cafe1.blocksW, y + i / cafe1.blocksW - ((i /(cafe1.blocksW * cafe1.blocksH)) * cafe1.blocksH), z + i / (cafe1.blocksW * cafe1.blocksH), cafe1.blocks[i])) {
                                chunk.tryToSetBlock(x + i % cafe1.blocksW, y + i / cafe1.blocksW - ((i / (cafe1.blocksW * cafe1.blocksH)) * cafe1.blocksH), z + i / (cafe1.blocksW * cafe1.blocksH), cafe1.blocks[i], (byte) 0);
                            }
                    }
                }
                chunk.addZona(new Zona(cafe1, x, y, z));
                break;
        }
    }
}