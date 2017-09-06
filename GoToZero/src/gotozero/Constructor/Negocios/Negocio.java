package gotozero.Constructor.Negocios;

import gotozero.Alimento;
import gotozero.Block;
import gotozero.ObjectGestion.Item;
import gotozero.Personas.Trabajador;
import gotozero.cam;
import gotozero.sprite;

import java.awt.*;

public class Negocio {
    private String Name;
    private long Presupuesto;

    private Block SpawnClients;

    private static Item[] ListaDisponibles;
    private int[] ContabilidadExistencias;

    private Trabajador[] ListaEmpleados;

    public static final Image SelectorCompra = (new sprite("img/buyCub.png")).getImg();

    private byte ModeloNegocio;

    public static void inicializeList() {
        ListaDisponibles = new Item[] {

                Alimento.Agua,
                Alimento.Huevo,
                Alimento.Naranja,
                Alimento.Limon,
                Alimento.Platano,

                Alimento.Sake,
                Alimento.Leche,
                Alimento.Tarta,
                Alimento.Ketchup,
                Alimento.Omelette,

                Alimento.Melon,
                Alimento.Flan,
                new Item("Levadura", "levadura"), //Harina es ingrediente
                new Item("Azucar", "azucar"),
        };
    }


    public Negocio() {
        this.Name = "Mimi's Coffe";
        this.Presupuesto = 1000;
        this.ModeloNegocio = 0;
        this.ListaEmpleados = new Trabajador[0];

        this.ContabilidadExistencias = new int[ListaDisponibles.length];

        //Prueba de funcionamiento correcto
        for(int i = 0; i < this.ContabilidadExistencias.length; ++i) {
            this.ContabilidadExistencias[i] = i;
        }
        this.SpawnClients = new Block(0,0,5,(byte)0);
    }

    public void act(int index, byte code) {
        switch (code) {
            case 0:
                this.Presupuesto += 10;
                break;
        }
    }

    public Block getSpawnClients() { return SpawnClients; }
    public void conversion(int indexMateria, int indexProduct) {

    }

    public static Item getItemByIndex(int index) { return ListaDisponibles[index]; }
    public static Item[] getListaDisponibles(){
        return ListaDisponibles;
    }
    public int[] getExistencias(){
        return this.ContabilidadExistencias;
    }
    public static int getLenghtList() {
        return ListaDisponibles.length;
    }
    public int getNumeroEmpleados() { return this.ListaEmpleados.length; }
    public long getPresupuesto() { return Presupuesto; }

    public static void drawCursorCompra(Graphics ctx, int X, int Y, int Z, cam camara) {
        ctx.drawImage(SelectorCompra,(X<<6)/ 5 -128/5 - camara.getXRest(),((Y<<6)/ 5 -128/5) - (((Z<<6)/ 5 -128/5)>>1) - camara.getYRest(),null);
    }

    public void contratarEmpleado(Trabajador novato) {
        Trabajador[] aux = new Trabajador[this.ListaEmpleados.length + 1];
        for (int i = 0; i < this.ListaEmpleados.length; ++i) {
            aux[i] = this.ListaEmpleados[i];


        }
        aux[aux.length - 1] = novato;
        this.ListaEmpleados = aux;
    }
}
