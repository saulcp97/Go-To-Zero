
/**
 * Write a description of class Menus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menus {
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
    public boolean controlCh;
    
    
    private int filas;
    private int columnas;
    
    private Inventary font;
    
    private int lMove;
    
    public static Menus Inventario = new Menus ((byte)0);
    public static Menus Personaje = new Menus ((byte) 1);
    
    public static Menus selectedOpened = null;
    public Mage m = null;
    /**
     * Tipo 0 -> Inventario
     * Tipo 1 -> Estadisticas
     * 
     * 
     */
    
    public Menus(byte TipoMenu) {
        this.Type = TipoMenu;
        this.visible = false;
        this.controlCh = false;
        this.lMove = -1;
        this.selected = -1;
        this.font = null;
        
        
        switch (TipoMenu){
            case 0:
                this.x = 64;
                this.y = 64;
                this.width = 256;
                this.height =  256;
                
                this.Conjuntos = new Rect[19];
                
                this.Conjuntos[0] = new Rect(x,y-16,this.width, this.height+16,99);
                this.Conjuntos[0].setImg(new sprite("/img/fondoMuro.png").getImg());
                
                this.Conjuntos[17] = new Rect(x,y,this.width, this.height,99);
                this.Conjuntos[17].setImg(new sprite("/img/cuadriculaItem.png").getImg());
                
                this.Puntero = new Rect(x,y,64,64,99);
                this.Puntero.setImg(new sprite("/img/selected.png").getImg());
                break;
                
            case 1:
                this.x = 320;
                this.y = 64;
                
                this.width = 320;            
                this.height = 448;
                
                this.Conjuntos =  new Rect[3];
                
                this.Conjuntos[0] = new Rect(x, y - 16, this.width, this.height - 128,99);
                this.Conjuntos[0].setImg(new sprite("/img/fondoMuro.png").getImg());
        
                this.Conjuntos[1] = new Rect(x, y + this.height - 16 - 128, this.width, 128, 99);
                this.Conjuntos[1].setImg(new sprite("/img/estadisticas.png").getImg());
                
                this.Puntero = new Rect(x,y,64,16,99);
                this.Puntero.setImg(new sprite("/img/selected.png").getImg());
                break;
        }
    }
    
    public Menus (Rect punt, String[] acciones) {
        this.Type = 99;
        this.visible = true;
        this.controlCh = true;
        this.lMove = -1;
        this.selected = -1;
        this.font = null;
        
        this.x = punt.getX() + punt.getWidth();
        this.y = punt.getY();
        this.width = 96;
        this.height = 16 * acciones.length;
        
        this.Conjuntos = new Rect[2];
        
        this.Conjuntos[0] = new Rect(x,y,96,this.height,99);
        this.Conjuntos[0].setImg(new sprite("/img/estadisticas.png").getImg());
        
        this.Puntero = new Rect(x,y,96,16,99);
        this.Puntero.setImg(new sprite("/img/selected.png").getImg());
        
    }
    
    
    public void setFont(Mage a) {
        this.font = a.getInventario();
        this.m = a;
        for(int i = 1; i < this.Conjuntos.length - 1; ++i) {
            if(i - 1< this.font.getInventaryLength()) {
                Rect fontR = this.font.getByPosition(i - 1).getRect();
                int plus = 0;

                fontR.setX(x+64*((i - 1)%4));
                fontR.setY(this.y + plus);
                this.Conjuntos[i] = fontR;
            }
        }
        
    }
    
    public Rect[] toPaint() {
        if(this.selected == -1){
            this.Conjuntos[this.Conjuntos.length - 1] = null;
        } else {
           int rowS = 0;
           int col = 0;
           int posIniY = 0;
           switch(this.Type) {
               case 0:
                    rowS = 64;//Numero de filas a seleccionar
                    col = 4;//Numero de Columnas a seleccionar
                    posIniY = y;
                    break;
               case 1:
                    rowS = 16;
                    col = 2;
                    posIniY = y + 448 - 96 - 16;
                    break;
               case 99:
                    rowS = 16;
                    col = 1;
                    posIniY = y;
                    break;
           }
            this.Puntero.setX(x+64*(this.selected%col));
            this.Puntero.setY(posIniY+rowS*(this.selected/col));

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
                if(this.selected < 1) { this.selected = this.height/16 - 1; }
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
                if(this.selected == this.height/16 - 1) {    this.selected = 0;  }
                else {  ++this.selected;   }
                break;
        }
        System.out.println("Down");    
    }
    
    public void Action() {
        switch(this.Type) {
            case 0:
                if (this.selected != -1) {
                    if (this.font != null && this.selected < this.font.getInventaryLength() && this.font.getByPosition(this.selected) != null) {
                        String[] options = this.font.getByPosition(this.selected).getActions();
                        selectedOpened = new Menus(this.Puntero,options);
                    }
                }
                break;
            case 99:
            
                if (this.selected == this.height/16 - 1) {
                    selectedOpened = null;
                }   else    {
                    Inventario.font.doAction(Inventario.selected, this.selected);
                    
                    
                }
                
        }
    }
    
    
}
