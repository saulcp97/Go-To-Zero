import java.net.*;
import java.io.*;
/**
 * Write a description of class Server here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Server extends Thread{
    // instance variables - replace the example below with your own
    private int id;
    private String accesTo;
    
    private Socket conect;
    private OutputStream os;
    private ObjectOutput obj;
    public Server(String Clave) {
        this.id = 1;
        this.accesTo =  Clave;
        
        
        try {
            this.conect = new Socket(this.accesTo,2500);
            this.os = this.conect.getOutputStream();
            this.obj = new ObjectOutputStream(this.os);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void setOutput(Object O) {
        try {
            this.obj.writeObject(O);
        } catch (Exception e) { System.out.println("ERROR SETOUTPUT"); }
    }
    
    public void enviarOutput() {
        try {
            this.obj.flush();
        } catch (Exception e) { System.out.println("Error al enviar"); }
    }
}
