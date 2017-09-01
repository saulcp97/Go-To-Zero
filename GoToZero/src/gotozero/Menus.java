package gotozero;

import gotozero.Constructor.Negocios.Negocio;
import gotozero.ObjectGestion.Inventary;
import gotozero.ObjectGestion.Item;
import gotozero.Personas.Trabajador;

import java.awt.*;

/**
 * Write a description of class Menus here.
 * 
 * @author Dreik Blake
 * @version 11/08/2017
 */
public class Menus <E> {
    private int x;
    private int y;
    private int width;
    private int height;
    
    // Tipo de Menu/Ventana del que se trata
    private byte Type;
    private Rect[] Conjuntos;

    private Rect Puntero;
    
    private int selected;
    
    private boolean visible;
    boolean controlCh;

    private String[] actions;

    private int filas;
    private int columnas;
    
    private Inventary font;
    private int[] existencias;

    private int lMove;
    public int desp = 0;


    private E auxiliar[];


    static Menus Inventario = new Menus ((byte) 0);
    static Menus Personaje = new Menus ((byte) 1);

    static Menus InventarioNegocio = new Menus((byte) 2);

    static Menus ContratacionTrabajadores = new Menus((byte) 5);

    public static Menus InfoObjetos = null;
    static Menus selectedOpened = null;


    private Mage m = null;

    private String name;

    private String[] description;

    /**
     * Tipo 0 -> Inventario
     * Tipo 1 -> Estadisticas
     * Tipo 2 -> Inventario del Negocio
     * Tipo 3 -> Informacion de Un Objeto/Item en todos los modos
     * Tipo 4 -> Menu de empleados del Negocio
     * Tipo 5 -> Menu de contratacion de Empleados
     *
     *
     * Tipo 99 -> Selector de Opciones
     * Tipo 100 -> Selecciones binarias si/no
     */

    public Menus(byte TipoMenu) {
        this.Type = TipoMenu;
        this.visible = false;
        this.controlCh = false;
        this.lMove = -1;
        this.selected = -1;
        this.font = null;

        this.actions = new String[2];
        
        switch (TipoMenu){
            case 0:
                this.x = 64;
                this.y = 64;
                this.width = 256;
                this.height =  256;

                this.filas = 4;
                this.columnas = 4;

                this.Conjuntos = new Rect[19];
                
                this.Conjuntos[0] = new Rect(x,y-16,this.width, this.height+16,99);
                this.Conjuntos[0].setImg(new sprite("img/fondoMuro.png").getImg());
                
                this.Conjuntos[17] = new Rect(x,y,this.width, this.height,99);
                this.Conjuntos[17].setImg(new sprite("img/cuadriculaItem.png").getImg());
                
                this.Puntero = new Rect(x,y,64,64,99);
                this.Puntero.setImg(new sprite("img/selected.png").getImg());
                break;
            case 1:
                this.x = 320;
                this.y = 64;

                this.filas = 4;
                this.columnas = 2;
                
                this.width = 320;            
                this.height = 448;
                
                this.Conjuntos =  new Rect[13];
                
                this.Conjuntos[0] = new Rect(x, y - 16, this.width, this.height - 128,99);
                this.Conjuntos[0].setImg(new sprite("img/fondoMuro.png").getImg());
        
                this.Conjuntos[1] = new Rect(x, y + this.height - 16 - 128, this.width, 128, 99);
                this.Conjuntos[1].setImg(new sprite("img/estadisticas.png").getImg());

                sprite imagenCasillero = new sprite("img/casilla.png");

                this.Conjuntos[6] = new Rect();
                this.Conjuntos[6].setImg(imagenCasillero.getImg());

                this.Conjuntos[7] = new Rect();
                this.Conjuntos[7].setImg(imagenCasillero.getImg());

                this.Conjuntos[8] = new Rect();
                this.Conjuntos[8].setImg(imagenCasillero.getImg());

                this.Conjuntos[9] = new Rect();
                this.Conjuntos[9].setImg(imagenCasillero.getImg());

                this.Conjuntos[10] = new Rect();
                this.Conjuntos[10].setImg(new sprite("img/basicMale.png").getImg());
                this.Conjuntos[10].setX(this.x + 64);
                this.Conjuntos[10].setY(this.y);
                this.Conjuntos[10].setWidth(this.width - 64);
                this.Conjuntos[10].setHeight(this.width - 64);

                this.Puntero = new Rect(x,y,64,16,99);
                this.Puntero.setImg(new sprite("img/selected.png").getImg());
                break;

            case 2:
                this.x = 64;
                this.y = 64;
                this.width = 256;
                this.height = 512;

                this.filas = Negocio.getLenghtList();
                this.columnas = 1;

                this.Conjuntos = new Rect[Negocio.getLenghtList() + 3];

                this.Conjuntos[0] = new Rect(x,y-16,this.width, this.height+16,99);
                this.Conjuntos[0].setImg(new sprite("img/menuCafeteria.png").getImg());

                this.Conjuntos[Negocio.getLenghtList() + 1] = new Rect(x,y,this.width, 256 /*this.height*/,99);
                this.Conjuntos[Negocio.getLenghtList() + 1].setImg(new sprite("img/cuadriculaItem.png").getImg());

                this.Puntero = new Rect(x,y,64,64,99);
                this.Puntero.setImg(new sprite("img/selected.png").getImg());
                break;

            case 5:
                this.x = 0;
                this.y = 0;

                this.width = 512;
                this.height = 512;

                this.filas = 5;
                this.columnas = 1;

                this.desp = 0;

                this.Conjuntos = new Rect[7];
                this.auxiliar = (E[]) new Trabajador[5];

                this.auxiliar[0] = (E) Trabajador.getCamareraRandom();
                this.auxiliar[1] = (E) Trabajador.getCamareraRandom();
                this.auxiliar[2] = (E) Trabajador.getCamareraRandom();
                this.auxiliar[3] = (E) Trabajador.getCamareraRandom();
                this.auxiliar[4] = (E) Trabajador.getCamareraRandom();

                this.Conjuntos[0] = new Rect(x, y, this.width, this.height+16,99);
                this.Conjuntos[0].setImg(new sprite("img/fondoMuro.png").getImg());

                this.Conjuntos[1] = new Rect(0,0,132,164,99);
                this.Conjuntos[1].setImg(new sprite("img/face/coronaDeCara.png").getImg());

                this.Conjuntos[2] = new Rect(0,0,412,168,99);
                this.Conjuntos[2].setImg(new sprite("img/fondoMenuContrato.png").getImg());


                this.Puntero = new Rect(x,y,64,64,99);
                this.Puntero.setImg(new sprite("img/selected.png").getImg());
                break;

        }
    }

    public Menus(Item mostral){
        this.Type = 3;

        this.x = 512;
        this.y = 128;
        this.width = 256;
        this.height = 256;

        this.visible = true;

        this.lMove = -1;
        this.selected = -1;
        this.font = null;

        this.filas = 1;
        this.columnas = 3;

        this.description = new String[]{
                mostral.getName(),
                mostral.getShortDescription(),
                mostral.getLongDescription(),
                mostral.getPoet()
        };

        this.Conjuntos = new Rect[3];

        this.Conjuntos[0] = new Rect(x,y,this.width,this.height,99);
        this.Conjuntos[0].setImg(new sprite("img/fondoMuro.png").getImg());

        this.Conjuntos[1] = mostral.getRect().clone();
        this.Conjuntos[1].setX(x);
        this.Conjuntos[1].setY(y + 16);

        this.Puntero = new Rect(x,y,64,64,99);
        this.Puntero.setImg(new sprite("img/selected.png").getImg());
    }

    public Menus (Rect punt, String[] acciones) {
        this.Type = 99;
        this.visible = true;
        this.controlCh = true;
        this.lMove = -1;
        this.selected = -1;
        this.font = null;

        this.columnas = 1;
        this.filas = acciones.length;
        this.actions = acciones;

        this.x = punt.getX() + punt.getWidth();
        this.y = punt.getY();
        this.width = 96;
        this.height = 16 * acciones.length;
        
        this.Conjuntos = new Rect[2];
        
        this.Conjuntos[0] = new Rect(x,y,96,this.height,99);
        this.Conjuntos[0].setImg(new sprite("img/estadisticas.png").getImg());
        
        this.Puntero = new Rect(x,y,96,16,99);
        this.Puntero.setImg(new sprite("img/selected.png").getImg());
    }
    
    
    public void setFont(Mage a) {
        if(this.Type == 0) {
            this.font = a.getInventario();
            this.m = a;
            Item aux;
            for (int i = 1; i < 17; ++i) {
                Inventario.Conjuntos[i] = null;
                if (i - 1 < Inventario.font.getInventaryLength()) {
                    aux = Inventario.font.getByPosition(i - 1);
                    if (aux != null) {
                        Rect fontR = aux.getRect();
                        int plus = (i - 1) / 4;

                        fontR.setX(Inventario.x + 64 * ((i - 1) % 4));
                        fontR.setY(Inventario.y + 64 * plus);
                        Inventario.Conjuntos[i] = fontR;
                    }
                }
            }
        } else if(this.Type == 1) {
            this.font = a.getEquipamiento();
            this.m = a;
            Item aux;
            for(int i = 2; i < 12; ++i) {
                if(i - 2 < Personaje.font.getInventaryLength()){
                    aux = Personaje.font.getByPosition(i - 2);
                    if(aux != null) {
                        Rect fontR = aux.getRect();
                        int plus = (i - 2);

                        fontR.setX(Personaje.x);
                        fontR.setY(Personaje.y + 64 * plus);
                        Personaje.Conjuntos[i] = fontR;
                    }
                } else if(i > 5 && i < 10) {
                    Personaje.Conjuntos[i].setX(this.x);
                    Personaje.Conjuntos[i].setY(this.y + 64 * (i - 6));
                }
            }
        } else if(this.Type == 2) {
            this.existencias = a.getEmpresa().getExistencias();
            this.m = a;
            Item[] aux = Negocio.getListaDisponibles();
            for(int i = 1; i < this.Conjuntos.length; ++i) {
                if(i - 1 < Negocio.getLenghtList()){
                    Rect fontR = aux[i-1].getRect();
                    int plus = (i - 1);
                    fontR.setX(InventarioNegocio.x);
                    fontR.setY(InventarioNegocio.y + 64 * plus);
                    InventarioNegocio.Conjuntos[i] = fontR;
                }
            }
        }
    }

    public String[] getActions() {
        return this.actions;
    }

    public Rect[] toPaint() {
        if(this.selected == -1){
            this.Conjuntos[this.Conjuntos.length - 1] = null;
        } else {
           int rowS = 0;
           int posIniY = 0;
           switch(this.Type) {
               case 0://Inventario
                    rowS = 64;//Tamaño de la fila a selecciona
                    posIniY = y;
                    break;
               case 1://Personaje
                    rowS = 16;
                    posIniY = y + 448 - 96 - 16;
                    break;
               case 2://Inventario de Negocio
                   rowS = 64;
                   posIniY = y;
                   break;
               case 3://Informacion del objeto
                   rowS = 16;
                   posIniY = y;
                   break;
               case 5:
                   rowS = 32;
                   posIniY = y;
                   break;
               case 99:
                    rowS = 16;
                    posIniY = y;
                    break;
           }
           this.Puntero.setX(x+64*(this.selected % this.columnas));
           this.Puntero.setY(posIniY+rowS*(this.selected/this.columnas));
           this.Conjuntos[this.Conjuntos.length - 1] = this.Puntero;
        }
        return this.Conjuntos;        
    }
    
    public boolean getVisibility() {
        return this.visible;
    }
    
    public void setVisibility(boolean a) {
        this.visible = a;
        if(!a){ this.selected = -1; }
    }
    
    public int getLMove() {
        return this.lMove;
    }
    
    public void setLMove(int nLM) {
        this.lMove = nLM;
    }
    
    public void pRight() {
        switch(this.Type) {
            case 0:
                if(this.selected != 15) {   ++this.selected;   }
                break;
            case 1:
                if(this.selected % 2 == 0) { ++this.selected;   }
                break;
        }
        System.out.println("R");
    }
    
    public void pLeft() {
        switch(this.Type) {
            case 0:
                if(this.selected != 0 && this.selected != -1) { --this.selected;  }
                break;
            case 1:
                if(this.selected % 2 == 1) { --this.selected; }
                break;
            case 99:
                selectedOpened = null;
                break;
        }
        System.out.println("L");
    }
    
    public void pUp() {
        switch(this.Type) {
            case 0:
                if(this.selected < 5) { this.selected = 0;  }
                else {  this.selected -=4; }
                break;
            case 1:
                if(this.selected > 1) { this.selected -= 2; }
                break;
            case 99:
                if(this.selected < 1) { this.selected = this.filas - 1; }
                else {  --this.selected;    }
                break;
        }
        System.out.println("Up");
    }
    
    public void pDown() {
        switch(this.Type) {
            case 0:
                if(this.selected > 10) { this.selected = 15;    }
                else if(this.selected == -1) {this.selected = 0;}
                    else {  this.selected += 4; }
                break;
            case 1:
                if(this.selected == -1) { ++this.selected; }
                else if(this.selected < 8) { this.selected += 2; }
                break;
            case 99:
                if(this.selected == this.filas - 1) { this.selected = 0;  }
                else { ++this.selected; }
                break;
        }
        System.out.println("Down");    
    }
    /*
    *   Codigo de acciones
    *   1 -> Usar
    *   2 -> Mostrar Información
    *
    *
    */
    public void Action() {
        this.recargar();
        switch(this.Type) {
            case 0:
                if (this.selected != -1) {
                    if (this.font != null && this.selected < this.font.getInventaryLength() && this.font.getByPosition(this.selected) != null) {
                        String[] options = this.font.getByPosition(this.selected).getActions();
                        selectedOpened = new Menus(this.Puntero,options);
                        selectedOpened.setName(this.font.getSelectedObjectName(this.selected));
                    }
                }
                break;
            case 99:
                if (this.selected == this.actions.length - 1) {
                    selectedOpened = null;
                } else {
                    Inventario.font.doAction(Inventario.selected, this.selected);
                    selectedOpened = null;
                }
        }
    }

    private void recargar(){
        for(int i = 1; i < 17; ++i) {
            Inventario.Conjuntos[i] = null;
            Item aux;
            if (i - 1 < Inventario.font.getInventaryLength()) {
                aux = Inventario.font.getByPosition(i - 1);
                if (aux != null) {
                    Rect fontR = aux.getRect();
                    int plus = 0;

                    fontR.setX(x + 64 * ((i - 1) % 4));
                    fontR.setY(Inventario.y + plus);
                    Inventario.Conjuntos[i] = fontR;
                }
            }
        }
    }

    public void setName(String name) {this.name = name;}
    public String getName() {return this.name;}

    static void drawMenuInventario(Graphics g, cam Camara) {
        Menus.Inventario.setFont(Camara.getMago());
        Rect[] r = Menus.Inventario.toPaint();
        byte[] count = Camara.getMago().getInventario().getCount();
        for(int i = 0; i < r.length; ++i) {
            if(r[i] != null){
                g.drawImage(r[i].getOutput(), r[i].getX(), r[i].getY(), r[i].getWidth(),r[i].getHeight(),null);
                if(i >= 1 && i <= count.length) {
                    g.setColor(Color.white);
                    g.drawString("Count: " + count[i - 1], r[i].getX(), r[i].getY());
                }
            }
        }
    }

    static void drawMenuContratacion(Graphics g, cam Camara) {
        Rect[] r = Menus.ContratacionTrabajadores.toPaint();

        g.drawImage(r[0].getOutput(),r[0].getX(),r[0].getY(),r[0].getWidth(),r[0].getHeight(),null);
        for(int i = 0; i < ContratacionTrabajadores.auxiliar.length; ++i){
            g.drawImage(r[1].getOutput(),0, (168) * i + ContratacionTrabajadores.desp,null);
            g.drawImage(((Trabajador)ContratacionTrabajadores.auxiliar[i]).getFaceImage(),16, 16 + (136 + 32) * i + ContratacionTrabajadores.desp,null);

            g.drawImage(r[2].getOutput(),132,(168) * i + ContratacionTrabajadores.desp,null);

            g.drawString(((Trabajador)ContratacionTrabajadores.auxiliar[i]).getName(), 136, 16 + (136 + 32) * (i) + ContratacionTrabajadores.desp);
            g.drawString("Habilidad: " + ((Trabajador)ContratacionTrabajadores.auxiliar[i]).getHabilidad(), 136, 32 + (136 + 32) * (i) + ContratacionTrabajadores.desp);
            g.drawString("Comunicación: " + ((Trabajador)ContratacionTrabajadores.auxiliar[i]).getComunicacion(), 136, 48 + (136 + 32) * (i) + ContratacionTrabajadores.desp);
            g.drawString("Productividad: " + ((Trabajador)ContratacionTrabajadores.auxiliar[i]).getProductividad() + "%", 136, 64 + (136 + 32) * (i) + ContratacionTrabajadores.desp);
            g.drawString("Salario: " + ((Trabajador)ContratacionTrabajadores.auxiliar[i]).getSalario(), 136, 80 + (136 + 32) * (i) + ContratacionTrabajadores.desp);
        }
        g.drawString("Número de empleados: " + Camara.getMago().getEmpresa().getNumeroEmpleados(), Menus.ContratacionTrabajadores.x, Menus.ContratacionTrabajadores.y + 16);
    }

    static void drawMenuInventarioNegocio(Graphics g, cam Camara) {
        Menus.InventarioNegocio.setFont(Camara.getMago());
        Rect[] r = Menus.InventarioNegocio.toPaint();
        int count = InventarioNegocio.existencias.length;
        for(int i = 0; i < r.length; ++i) {
            if(r[i] != null){
                if(i >= 1 && i <= count) {
                    g.drawImage(r[0].getOutput(), r[0].getX(), r[i].getY() + InventarioNegocio.desp,512, 64, null);

                    g.drawImage(r[i].getOutput(), r[i].getX() + 1, r[i].getY() + InventarioNegocio.desp, r[i].getWidth(),r[i].getHeight(),null);
                    g.setColor(Color.darkGray);
                    g.fillRect(r[i].getX(), r[i].getY() + InventarioNegocio.desp, 16, 16);
                    g.setColor(Color.white);
                    g.setFont(new Font("Courier New", Font.CENTER_BASELINE, 12));
                    g.drawString("" + InventarioNegocio.existencias[i - 1], r[i].getX() + 1, r[i].getY() + 13 + InventarioNegocio.desp);

                    //Nombre del Item
                    g.setColor(Color.BLACK);
                    g.drawString("" + Negocio.getItemByIndex(i - 1).getName(), r[i].getX() + 8 + r[i].getWidth(), r[i].getY() + 16 + InventarioNegocio.desp);
                    g.drawString("" + Negocio.getItemByIndex(i - 1).getShortDescription(), r[i].getX() + 16 + r[i].getWidth(), r[i].getY() + 16 + 15 + InventarioNegocio.desp);

                }
            }
        }
    }

    static void drawMenuPersonajes(Graphics g, cam Camara) {
        Menus.Personaje.setFont(Camara.getMago());
        Rect[] r = Menus.Personaje.toPaint();
        for(int i = 0; i < r.length; i++) {
            if(r[i] != null){
                g.drawImage(r[i].getOutput(), r[i].getX(), r[i].getY(), r[i].getWidth(),r[i].getHeight(),null);
            }
        }
        Stadistic estadisticas = Camara.getMago().getEstadisticas();
        g.setColor(Color.white);
        //HP:
        g.drawString("Vida: " + estadisticas.getVida() + " / " + Camara.getMago().getMaxVida(), r[1].getX() + 5,r[1].getY() + 15);
        //Nivel:
        g.drawString("Nivel: " + estadisticas.getLevel(), r[1].getX() + 5,r[1].getY() + 30);
        //Experiencia:
        g.setColor(Color.GRAY);
        g.fillRect(r[1].getX() + 5, r[1].getY() + 35, 100, 7);
        g.setColor(Color.GREEN);
        g.fillRect(r[1].getX() + 5, r[1].getY() + 35, (int)(100 * ((double)estadisticas.getExp())/estadisticas.getExpToNext()), 7);
        //Estadisticas
        //Defensa
        g.setColor(Color.YELLOW);
        g.drawString("Defensa Agua: " + estadisticas.getBlockAqua(), r[1].getX() + 5,r[1].getY() + 55);
        g.drawString("Defensa Fuego: " + estadisticas.getBlockFire(), r[1].getX() + 5,r[1].getY() + 70);
        g.drawString("Defensa Aire: " + estadisticas.getBlockAir(), r[1].getX() + 5,r[1].getY() + 85);
        g.drawString("Defensa Tierra: " + estadisticas.getBlockTerra(), r[1].getX() + 5,r[1].getY() + 100);
        g.drawString("Defensa Rayo: " + estadisticas.getBlockThunder(), r[1].getX() + 5,r[1].getY() + 115);
        //Ataque
        g.setColor(Color.ORANGE);
        g.drawString("Ataque Agua: +" + estadisticas.getPlusAqua() + "%", r[1].getX() + 5 + r[0].getWidth()/2,r[1].getY() + 55);
        g.drawString("Ataque Fuego: +" + estadisticas.getPlusFire() + "%", r[1].getX() + 5 + r[0].getWidth()/2,r[1].getY() + 70);
        g.drawString("Ataque Aire: +" + estadisticas.getPlusAir() + "%", r[1].getX() + 5 + r[0].getWidth()/2,r[1].getY() + 85);
        g.drawString("Ataque Tierra: +" + estadisticas.getPlusTerra() + "%", r[1].getX() + 5 + r[0].getWidth()/2,r[1].getY() + 100);
        g.drawString("Ataque Rayo: +" + estadisticas.getPlusThunder() + "%", r[1].getX() + 5 + r[0].getWidth()/2,r[1].getY() + 115);
    }

    static void drawSelection(Graphics g) {
        Rect[] r = Menus.selectedOpened.toPaint();
        String[] s = Menus.selectedOpened.getActions();
        g.setColor(Color.ORANGE);
        g.drawString(Menus.selectedOpened.getName(),r[0].getX(), r[0].getY() - 2);
        for(int i = 0; i < s.length; i++) {
            if(i < r.length && r[i] != null){
                g.drawImage(r[i].getOutput(), r[i].getX(), r[i].getY(), r[i].getWidth(),r[i].getHeight(),null);
            }
            g.drawString(s[i],r[0].getX(), r[0].getY() + 10 + i * 15);
        }
    }

    static void drawInfoObjeto(Graphics g){
        Rect[] r = Menus.InfoObjetos.toPaint();
        for(int i = 0; i < r.length; i++) {
            if(r[i] != null) {
                g.drawImage(r[i].getOutput(), r[i].getX(), r[i].getY(), r[i].getWidth(), r[i].getHeight(), null);
            }
        }
        g.drawString("Nombre: "+ Menus.InfoObjetos.description[0],Menus.InfoObjetos.Conjuntos[1].getWidth() + 8,Menus.InfoObjetos.Conjuntos[1].getHeight() + 8);
        g.drawString("Descripción: ",Menus.InfoObjetos.Conjuntos[1].getWidth() + 8,Menus.InfoObjetos.Conjuntos[1].getHeight() + 26);
        g.drawString(Menus.InfoObjetos.description[1],Menus.InfoObjetos.Conjuntos[1].getWidth() + 8,Menus.InfoObjetos.Conjuntos[1].getHeight() + 42);
        g.drawString(Menus.InfoObjetos.description[2],Menus.InfoObjetos.Conjuntos[1].getWidth() + 8,Menus.InfoObjetos.Conjuntos[1].getHeight() + 58);
        g.drawString(Menus.InfoObjetos.description[3],Menus.InfoObjetos.Conjuntos[1].getWidth() + 8,Menus.InfoObjetos.Conjuntos[1].getHeight() + 74);
    }



    public void click(cam Camara, int X, int Y) {
        if(this.auxiliar.length > 0 && X == 0 && Y == 0) {
            E[] aux = (E[])new Trabajador[this.auxiliar.length - 1];
            for (int i = 0; i < aux.length; ++i) {
                aux[i] = this.auxiliar[i + 1];
            }
            Camara.getMago().getEmpresa().contratarEmpleado((Trabajador)(this.auxiliar[0]));
            this.auxiliar = aux;
        }
    }

}