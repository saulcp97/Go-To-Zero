/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicBox;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
/**
 *
 * @author saulc
 */
public class Box {
    public static Synthesizer a;
    BufferedReader br;
    
    public static void main(String[] args) throws MidiUnavailableException {
        Box box = new Box();
        box.Musician();
        
    }
    
    public void Musician() throws MidiUnavailableException{
        a = MidiSystem.getSynthesizer();
        a.open();
        
        System.out.println("Declaracion y abertura");
        MidiChannel[] channels = a.getChannels();
        Instrument[] instrumentos = a.getDefaultSoundbank().getInstruments();
        System.out.println(" carga Instrumento canal");

        a.loadInstrument(instrumentos[90]);
        
       
        for (int i = 0; i < 12; i++) {
            channels[0].noteOn(60 + i,100);
        }
        
        try{
            Thread.sleep(12000);
        } catch (Exception e){
            
        }
        a.close();
    }

    public Box(){
        try {
            this.br = new BufferedReader(new FileReader("src/./gotozero/txt/partitura.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Box.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
