package gotozero;


/**
 * Write a description of class lifes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class lifes extends Block{
    // instance variables - replace the example below with your own
    protected String Name;
    
    protected byte Hambre;
    protected byte Sed;
    
    protected int TamanyoX;
    protected int TamanyoY;
    
    protected Magia magia;
    protected byte Tipo;
    
    protected byte dir;
    
    protected Inventary Inventario;
    
    protected byte grade;

    protected Stadistic estadisticas;
    /**
     * Constructor for objects of class lifes
     */
    public lifes(byte t) {
        super();

        this.Name = "";
        this.Hambre = 80;
        this.Sed = 100;
        this.Tipo = t;
        this.grade = 0;
        this.Inventario = new Inventary();

        this.estadisticas = new Stadistic();
        switch(t) {
            case 0:
                //Humano
                this.deep = 64;
                this.width = 32;
                this.height = 32;
                
                this.TamanyoX = 64;
                this.TamanyoY = 64;
                break;
            case 1:
                //Gigante
                this.deep = 64;
                this.width = 64;
                this.height = 64;
                
                this.TamanyoX = 128;
                this.TamanyoY = 128;
                break;
        }
    }
    
    public lifes(byte t, int x, int y, int z) {
        super(x,y,z);
        
        this.Name = "";
        this.Hambre = 80;
        this.Sed = 100;
        this.Tipo = t;
        this.grade = 0;
        this.Inventario = new Inventary();

        this.estadisticas = new Stadistic();
        switch(t) {
            case 0:
                //Humano
                this.deep = 64;
                this.width = 32;
                this.height = 32;
                this.dir = 2;
                this.TamanyoX = 64;
                this.TamanyoY = 64;
                break;
            case 1:
                //Gigante
                this.deep = 64;
                this.width = 64;
                this.height = 64;
                
                this.TamanyoX = 128;
                this.TamanyoY = 128;
                break;
        }
    }
    
    public void Alimentacion(Alimento a){
        int comida = a.getCalorias() + this.Hambre;
        if( comida >= 100) {this.Hambre = 100; }
        else { this.Hambre = (byte)comida; }

        int bebida = a.getHidratacion() + this.Sed;
        if( bebida >= 100) {this.Sed = 100; }
        else { this.Sed = (byte)bebida; }
    }
    
    public void Alimentacion(){
        this.Hambre += 10;
        //int aux = a.getHidratacion() + this.Sed;
        this.Sed = 100;
    }
    
    public void decrLife(){
        this.estadisticas.modifyHP(-1);
    }
    public void decrHambre() {
        this.Hambre--;
    }
    public void decrSed() {
        this.Sed--;
    }
    public byte getLife() {
        return this.estadisticas.getHP();
    }
    public byte getHambre() {
        return this.Hambre;
    }
    public byte getSed() {
        return this.Sed;
    }
    
    public byte getGrade() {
        return this.grade;
    }
    //Datos de relleno
    public int[] getVectorPos() {
        return new int[] {x,y};
    }
    
    public void setPosicion (int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setSpriteSize(int x,int y) {
        this.TamanyoX = x;
        this.TamanyoY = y;
    }
    
    public void setName(String name) {
        this.Name = name;
    }
    public String getName() {
        return this.Name;
    }
    
    public void caer(int n) {
        this.z -= n;
    }
    
    public int getTamanyoX() {
        return this.TamanyoX;
    }
    
    public int getTamanyoY() {
        return this.TamanyoY;
    }

    public Inventary getInventario() {
        return this.Inventario;
    }
    
    public void setGrade(byte b) {
        this.grade = b;
    }
    
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
