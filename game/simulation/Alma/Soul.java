package com.mygdx.game.simulation.Alma;

import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

public class Soul {
    private Action[] acciones;
    private int estado;
    private NPC sujeto;
    private byte Emocion;
    private String[] Options;
    private int[] jumpTo;


    public Soul(NPC s) {
        this.acciones = new Action[0];
        this.sujeto = s;
        this.estado = 0;
        this.Emocion = 0;
        this.Options = null;
    }

    public String[] getOptions() { return Options; }
    public void incrementState() {
        ++this.estado;
    }
    public void asked() {
        this.Emocion -= 1;
        this.Options = null;
    }

    public byte getEmocion() { return this.Emocion; }
    public void resetEmotion() { this.Emocion = 0; }
    public int getEstado() {
        return estado;
    }

    public String getMsg() {
        switch (this.estado) {
            case 0:
                return "Hola, soy " + this.sujeto.getName();
            case 1:
                return "Entonces, tu eres el fragmento de llama que ha reencarnado...";
            case 2:
                return "Oh, no me hagas caso *sonrie nerviosa*";
            case 3:
                return "¿Este lugar te debe de ser muy extraño no?";
            case 4:
                return "Deberias intentar reunir todas las almas de mundo para responder \na tus preguntas.";
            case 5:
                return "Oh pequeño, no pongas esa cara, he dicho todas las 'Almas de Mundo',\nno todas las almas del mundo. *Rie con ternura*";
            case 6:
                if(this.Emocion == 0) {
                    this.Emocion = -1;
                    this.Options = new String[] {"Como se llama este lugar?", "Como que un fragmento de llama?", "A donde debo ir?"};
                    this.jumpTo = new int[]{7,12,17};
                }
                return "¿Quieres preguntarme algo mas?";

            case 7:
                return "Este pueblo se llama Arnista.";
            case 8:
                return "Pero supongo que te refieres a este mundo corrupto.";
            case 9:
                return "Este mundo se llama 'Genkai' se llama asi por la lengua materna\nde los Pardos.";
            case 10:
                return "Supongo que tendrás algunos recuerdos de la raiz, donde empezó la corrupción,\nasi que te diré algo, no te fies de algo que se te haga conocido";


            case 12:
                return "Bueno en este mundo podrido existen grandes bestias y seres misticos.";
            case 13:
                return "Uno de ellos, el segundo en nacer, es el 'Fenix de la Oscuridad'\nque cubre el mundo con sus llamas de la desesperación.";
            case 14:
                return "Y no es muy raro que a partir de la unión de las llamas y los restos de otros seres nazcan criaturas incompletas,\n los fragmentos.";
            case 15:
                return "Aunque los fragmentos no llegan a un nivel de una bestia celestial, poseen un potencial\nde crecimiento ilimitado, tu incluido.";

            case 17:
                return "Si yo fuera tu iria a cazar algunas criaturas salvajes y aberraciones debiles, para descubrir\n tus limites actuales y sacar algo de dinero.";
            case 18:
                return "Luego me pasaria por las tiendas del pueblo a comprar lo mas a corde segun mi propio estilo,\ny quizas aprender alguna habilidad útil";
            case 19:
                return "Y si te llegara a interesar, podrias unirte a un gremio. Los hay de muchos tipos, de mercenarios, de magos, de artesanos...";
            case 20:
                return "Pero también puedes volverte el aprendiz de un maestro artesano y trabajar directamente para el,\neres libre de elegir en que gastar tu vida";
            case 21:
                return "Es curioso, en este mundo que los 3Grandes nos impusieron por la fuerza, cualquier persona es libre de elegir su propio destino";
        }
        return "<END>";
    }

    public void selectOption(byte option) {
        this.estado += option * 5 + 1;
    }
}
