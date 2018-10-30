package com.mygdx.game.simulation;

public class RPG {
    private int F;//Fuerza
    private int A;//Agilidad
    private int V;//Velocidad
    private int P;//Poder
    private int R;//Resistencia/Regeneración
    private int I;//Inteligencia/Control
    public RPG(){
        this.F = 0;
        this.A = 0;
        this.V = 0;
        this.P = 0;
        this.R = 0;
        this.I = 0;
    }

    public int getA() { return A; }
    public int getF() { return F; }
    public int getP() { return P; }
    public int getR() { return R; }
    public int getV() { return V; }
    public int getI() { return I; }

    public void setA(int a) { A = a; }
    public void setF(int f) { F = f; }
    public void setP(int p) { P = p; }
    public void setR(int r) { R = r; }
    public void setV(int v) { V = v; }
    public void setI(int i) { I = i; }

    public void add(RPG o) {
        this.F += o.F;
        this.A += o.A;
        this.V += o.V;
        this.P += o.P;
        this.R += o.R;
        this.I += o.I;
    }

    public void sub(RPG o) {
        this.F -= o.F;
        this.A -= o.A;
        this.V -= o.V;
        this.P -= o.P;
        this.R -= o.R;
        this.I -= o.I;
    }
}
