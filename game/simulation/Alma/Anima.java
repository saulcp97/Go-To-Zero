package com.mygdx.game.simulation.Alma;

import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

import java.util.ArrayList;

public class Anima {














    private class Precepts {
        private int prioridad;
        private final String Attribute;
        public Precepts(int p, String attribute) {
            this.prioridad = p;
            this.Attribute = attribute;
        }

        public int compareTo(Precepts p) {
            return this.prioridad - p.prioridad;
        }
    }

    private class Chanel {
        private final String id;
        private ArrayList<Message> input;
        private ArrayList<NPC> register;

        public Chanel(String name) {
            this.id = name;
            this.input = new ArrayList<Message>();
            this.register = new ArrayList<NPC>();
        }

        public void registerNPC(NPC npc) {
            if(!this.register.contains(npc)) {
                this.register.add(npc);
            }
        }

        public void removeNPC(NPC npc) {
            this.register.remove(npc);
        }

        public void WriteMSG(NPC npc, String str) {
            if(this.register.contains(npc)){
                this.input.add(new Message(npc,str));
            }
        }

        private class Message {
            private final NPC rm;
            private final String message;
            public Message(NPC id, String msg) {
                this.rm = id;
                this.message = msg;
            }

            public NPC getRm() {
                return rm;
            }

            public String getMessage() {
                return message;
            }
        }
    }
}