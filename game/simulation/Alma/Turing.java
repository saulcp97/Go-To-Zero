package com.mygdx.game.simulation.Alma;

public class Turing {
    private final int finalState;
    private final int initState;
    private final char[] alphabet;
    private final TransitionStates[] transitions;
    private int Puntero;
    private int State;
    private String cintaS;
    private char[] cinta;

    public Turing(int initState, TransitionStates[] transitions, char[] alphabet, int finalEstate){
        this.initState = initState;
        this.State = this.initState;
        this.transitions = transitions;
        this.alphabet = alphabet;
        this.finalState = finalEstate;

        this.Puntero = 0;
        this.cintaS = "";
        for(int i = 0; i < 128; ++i) {
            this.cintaS = this.cintaS.concat("B");
        }
    }

    public void passValue(String input) {
        this.cinta = input.toCharArray();
        this.Puntero = 0;
        this.State = this.initState;
        TransitionStates auxTrans = null;
        while(this.State != this.finalState) {
            for(int i = 0; i < this.transitions.length; ++i) {
                if(this.transitions[i].State == this.State) {
                    auxTrans = this.transitions[i];
                    break;
                }
            }
            if(auxTrans == null) {
                this.State = this.finalState;
            } else {
                int tr = -1;
                for(int i = 0; i < auxTrans.leido.length; ++i) {
                    if(auxTrans.leido[i] == this.cinta[this.Puntero]){
                        tr = i;
                        break;
                    }
                }
                if(tr == -1) {
                    this.State = this.finalState;
                } else {
                    this.cinta[this.Puntero] = auxTrans.escrito[tr];
                    this.State = auxTrans.estados[tr];

                    /*
                    *
                    * Switch con las transiciones y acciones.
                    *
                    *
                     */

                }
            }
        }
    }

    public class TransitionStates {
        public final int State;
        public final char[] leido;
        public final char[] escrito;
        public final char[] Mov;
        public final int[] estados;
        //cada Transition solo hace referencia a un estado de turing y lo que muestrea es cada posible entrada a este.
        //Si no hay transiciones para cierto valor, no se representa.
        //FOrmato de String -State:2,(leido-escrito-Movimiento-Estado),4,
        public TransitionStates(String Input){
            this.State = Integer.parseInt(Input.split(":")[0]);
            String[] estad = Input.split(":")[1].split(",");
            this.leido = new char[estad.length];
            this.escrito = new char[estad.length];
            this.Mov = new char[estad.length];
            this.estados = new int[estad.length];

            for(int i= 0; i < estad.length; ++i) {
                this.leido[i] = estad[i].split("-")[0].charAt(0);
                this.escrito[i] = estad[i].split("-")[1].charAt(0);
                this.Mov[i] = estad[i].split("-")[2].charAt(0);
                this.estados[i] = Integer.parseInt(estad[i].split("-")[3]);
            }
        }
    }
}