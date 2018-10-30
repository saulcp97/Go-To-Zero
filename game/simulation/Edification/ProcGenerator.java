package com.mygdx.game.simulation.Edification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Chunk;
import com.mygdx.game.Cubos.Decoration;
import com.mygdx.game.Cubos.Deformed;

public class ProcGenerator {
    //                              0b00000000
    public static byte NoInBounds = 0b00000000;
    public static byte DiscretDis = 0b00000001;
    public static byte NormalDis  = 0b00000010;
    public static byte ANormalDis = 0b00000011;
    public static byte XFormPosi  = 0b00000100;
    public static byte X3FormPosi = 0b00000101;


    private static int seed;

    public static float RandomS(int seed) {
        ProcGenerator.seed = seed;
        return (float) Math.abs(Math.cos(ProcGenerator.seed++));
    }

    public static float RandomS() {
        return (float) Math.abs(Math.cos(ProcGenerator.seed++));
    }

    public static void GenerateProc(Chunk entry) {
        int xGen = (int) (31 * ProcGenerator.RandomS(24));
        int yGen = (int) (31 * ProcGenerator.RandomS());

        House.addPosition(entry, (byte) -128, xGen, yGen, 0);
        xGen = (int) (31 * ProcGenerator.RandomS());
        yGen = (int) (31 * ProcGenerator.RandomS());
        House.addPosition(entry, (byte) -128, xGen, yGen, 1);

        House.addPosition(entry, (byte) -126, 0, 0, 0);
    }

    public static void GenerateProcForest(Chunk entry, byte ... Attribute) {
        Decoration aux;
        ProcGenerator.RandomS(71);
        for (int i = 0; i < 32; ++i) {
            float val = Cardinal.normalProb(i,32);
            if(val > 1) {
                System.out.println("Error en la distribución normal, valor obtenido: " + val);
            } else {
                System.out.println("Valor obtenido para " + i + ": " + val);
            }

            for (int j = 0; j < 32; ++j) {
                entry.tryToAdd(i, j, 1, (byte) 1);

                if (ProcGenerator.RandomS() < .05) {
                    if(!((i == 0 || j == 0 || i == 31 || j == 31) && Cardinal.containsNumber(Attribute,NoInBounds))
                            || !Cardinal.containsNumber(Attribute,NoInBounds)) {
                        aux = new Decoration();
                        aux.setType((byte) 10);
                        aux.setOrient((byte) 3);
                        entry.tryToAddBlock(aux, i, j, 2);
                    }
                }
            }
        }
    }

    public static void GenerateProcTerrain(Chunk entry) {
        int v = (int) (15 * ProcGenerator.RandomS(2));
        Deformed d;
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                d = new Deformed((byte) 1, (byte) 2);
                int height = 1;
                float h0 = .5f + Math.abs(MathUtils.cosDeg(i * v)) * 8.5f * Math.abs(MathUtils.sinDeg(j * v)),
                        h1 = .5f + Math.abs(MathUtils.cosDeg((i + 1) * v)) * 8.5f * Math.abs(MathUtils.sinDeg(j * v)),
                        h2 = .5f + Math.abs(MathUtils.cosDeg(i * v)) * 8.5f * Math.abs(MathUtils.sinDeg((j + 1) * v)),
                        h3 = .5f + Math.abs(MathUtils.cosDeg((i + 1) * v)) * 8.5f * Math.abs(MathUtils.sinDeg((j + 1) * v));

                while (h0 > 1 && h1 > 1 && h2 > 1 && h3 > 1) {
                    --h0;
                    --h1;
                    --h2;
                    --h3;
                    ++height;
                }

                d.setHeight(h0, h1, h2, h3);

                entry.tryToAddBlock(d, i, j, height);
            }
        }
    }


    public static void GeneratePreviusMap(Chunk entry, String map, int mapX, int mapY) {
        //De momento vamos a suponer que todos los mapas son Draknia

        if (mapX == 0) {
            for (int j = 0; j < 32; ++j) {
                entry.tryToAdd(0, j, 1, (byte) 2);
            }
        }
        if (mapY == 0) {
            for (int i = 0; i < 32; ++i) {
                entry.tryToAdd(i, 0, 1, (byte) 2);
            }
        }
        if (mapX == 7) {
            for (int j = 0; j < 32; ++j) {
                entry.tryToAdd(31, j, 1, (byte) 2);
            }
        }
        if (mapY == 7) {
            for (int i = 0; i < 32; ++i) {
                entry.tryToAdd(i, 31, 1, (byte) 2);
            }
        }

        String structure;
        String[] chains;
        String AttributeSpec;
        byte[] Atributes = new byte[0];
        FileHandle file = Gdx.files.local("data/txt/" + map + "Map.txt");
        if(file.exists()) {
            structure = file.readString();
            int x = 0, y = 0;
            structure = structure.replaceAll("\n"," ");
            chains = structure.split(" ");
            if(chains[mapX + mapY * 8].contains("-")) {
                AttributeSpec = chains[mapX + mapY * 8].split("-")[1];
                chains[mapX + mapY * 8] = chains[mapX + mapY * 8].substring(0, chains[mapX + mapY * 8].indexOf("-"));
                System.out.println("Cadena extraida: " + chains[mapX + mapY * 8]);

                System.out.println("Especificaciones: " + AttributeSpec);
                String[] atributReading = AttributeSpec.split("\\.");
                if (atributReading.length >= 1) {
                    System.out.println("He detectado atributos");
                    Atributes = new byte[Integer.parseInt(atributReading[0])];
                    for (int i = 0; i < Atributes.length; ++i) {
                        switch (atributReading[i + 1]) {
                            case "In":
                                Atributes[i] = NoInBounds;
                                break;
                            case "DDis":
                                Atributes[i] = DiscretDis;
                                break;
                            case "NDis":
                                Atributes[i] = NormalDis;
                                break;
                            case "ANDis":
                                Atributes[i] = ANormalDis;
                                break;
                            case "XForm":
                                Atributes[i] = XFormPosi;
                                break;
                            case "X3Form":
                                Atributes[i] = X3FormPosi;
                                break;
                            default:
                                System.out.println("Atributos extraidos:" + Atributes[i]);
                        }
                    }
                }
            }
            switch (chains[mapX + mapY * 8]){
                case "F":
                    GenerateProcForest(entry, Atributes);
                    break;
                default:
                    GenerateProcTerrain(entry);
            }
        } else {
            System.out.println("El mapa especificado no existe: " + map);
        }
    }
}
