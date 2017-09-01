package gotozero.Personas;

import gotozero.Main;
import gotozero.NPC;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Trabajador extends NPC {

    private final byte Genere;//1 mujer -> -1 Hombre
    private final byte Raza;
    private byte productividad; //0 productividad = 100% , 10 productividad  = 110%

    private byte Habilidad; //O destruccion planetaria, 1 base, 5 prometedora, 10 prodigio
    private byte Comunicacion; //"Lindura Chica" 0 Mud@,1 timid@ inexpert@, 5 Popular, 10 Idol de Idols
    private final byte Excentricidad; //Val

    public int Salario;

    private Image faceImage;

    private final byte EstiloPelo;
    private final byte ColorPelo;
    private final byte ColorOjos;

    public Trabajador() {
        super();
        this.Excentricidad = 0;
        this.Genere = 1;
        this.Raza = 0;
        this.EstiloPelo = 0;
        this.ColorOjos = 0;
        this.ColorPelo = 0;

        this.Habilidad = 1;
        this.Comunicacion = 1;

        this.Salario = 5;
    }

    public Trabajador(byte genere, String Name) {
        super();
        this.Excentricidad = 0;
        this.productividad = 100;
        this.Genere = genere;
        this.Name = Name;
        this.Raza = 0;
        this.EstiloPelo = 0;
        this.ColorOjos = 0;
        this.ColorPelo = 0;

        this.Habilidad = 1;
        this.Comunicacion = 1;

        this.Salario = 5;
    }

    public Trabajador (byte genere, String name, byte raza, byte estilopelo, byte colorpelo, byte colorojos) {
        super();

        this.Excentricidad = (byte)(1 + (9 * Math.random()));

        this.Genere = genere;
        this.Name = name;
        this.Raza = raza;

        this.productividad = 100;

        this.EstiloPelo = estilopelo;
        this.ColorPelo = colorpelo;
        this.ColorOjos = colorojos;

        this.Habilidad = (byte)(10 * Math.random());
        this.Comunicacion = (byte)(10 * Math.random());

        this.Salario = (int)(2.5 * (1 + this.Habilidad + this.Comunicacion) * Math.sqrt(this.Excentricidad));

        try {
            this.faceImage = ImageIO.read(Main.class.getResource("img/face/Yuriko.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte getProductividad() { return productividad; }
    public byte getHabilidad() { return Habilidad; }
    public byte getComunicacion() { return Comunicacion; }
    public int getSalario() { return Salario; }

    public static Trabajador getCamareraRandom() {
        return new Trabajador((byte)-1, getRandomName(true), (byte)0, (byte)0, (byte)0,(byte)0);
    }

    @Override
    public String toString(){
        return "" + this.Genere + "_" + this.Raza + "_" + this.EstiloPelo + "_" + this.ColorPelo + "_" + this.ColorOjos;
    }

    public Image getFaceImage() {
        return faceImage;
    }

    private static String getRandomName(boolean tr){
        //tr = true -> Chica
        String res = "";
        if(tr){
            String[] baseNombres = new String[]{"Mia", "Sora", "Haruko", "Yuki", "Urenai", "Darenis", "Fumiko", "Fivi", "Sara", "Sakura", "Kaminari",
                "Tsui", "Lala", "Annie", "Hima", "Hime", "Suura", "Kali", "Laya", "Rika", "Ericka", "Kymberly", "Viki", "Lisa", "Lucy", "Saya",
                "Sayaka"};

            res = baseNombres[(int)(Math.random() * baseNombres.length)];
        }
        return res;
    }






}
