package com.mygdx.game.Libreria;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.HashMap;

public class MyReadJSON {
    private String content;
    private TreeJSON build;
    public MyReadJSON(FileHandle file) {
        this.content = file.readString();
        System.out.println("Lectura Exitosa");
        this.build = null;
        this.build();
    }

    public void build(){
        ArrayList openBrackets = ocurrences(this.content,"{");
        ArrayList closeBrackets = ocurrences(this.content,"}");
        System.out.println("Numero de parejas: " + openBrackets.size());

        ArrayList indexBlocks = linkBlocks(openBrackets,closeBrackets);

        this.build = new TreeJSON(this.content.split(":")[0]);
        this.build.contructTree(indexBlocks);
    }

    private ArrayList ocurrences(String main, String sub){
        ArrayList res = new ArrayList();
        for (int i = this.content.indexOf(sub); i >= 0; i = main.indexOf(sub, ++i)) {
            res.add(i);
        }
        return res;
    }

    public ArrayList linkBlocks(ArrayList<Integer> open, ArrayList<Integer> close) {
        ArrayList<Integer> O = (ArrayList<Integer>)open.clone();
        ArrayList<Integer> C = (ArrayList<Integer>)close.clone();
        ArrayList res = new ArrayList();
        Integer a,b;
        int j;
        while (close.size() != 0) {
            b = close.remove(0);
            j = open.size() - 1;
            while (open.size() != 0) {
                a = open.get(j);
                if (a < b) {
                    res.add(new Integer[]{open.remove(j), b});
                    break;
                }
                --j;
            }
        }
        return res;
    }

    private class TreeJSON {
        private LeafJSON root;

        private TreeJSON(String main){
            this.root = new LeafJSON(main);
        }

        private void contructTree(ArrayList<Integer[]> input) {
            this.root.setContent(new ArrayList<LeafJSON>());
            this.root.construct(input);
        }

        private class LeafJSON {
            private String ID;
            private Object Content;

            private LeafJSON(String ID) {
                this.ID = ID;
            }
            private LeafJSON() {
                this.ID = "";
            }

            private void setContent(Object content) {
                this.Content = content;
            }
            private void construct(ArrayList<Integer[]> input) {
                ArrayList<Integer[]> map = input;
                ArrayList<Integer> aux = new ArrayList<Integer>();
                String esquema = content;
                HashMap schema = new HashMap();
                boolean hasInner;
                for(int i = 0; i < map.size(); ++i) {
                    hasInner = false;
                    for(int j = 0; j < map.size(); ++j) {
                        if(i != j) {
                            if(((Integer[])map.get(i))[0] < ((Integer[])map.get(j))[0]
                                    && ((Integer[])map.get(j))[1] < ((Integer[])map.get(i))[1]) {
                                hasInner = true;
                                schema.put(i,j);
                                break;
                            }
                        }
                    }
                    if(!hasInner){
                        //This mean, that this leaf is in the las level
                        LeafJSON ultima = new LeafJSON();
                        ultima.setContent(esquema.substring(((Integer[])map.get(i))[0],((Integer[])map.get(i))[1]));
                        System.out.println("Ulitmas: " + esquema.substring(((Integer[])map.get(i))[0] + 1,((Integer[])map.get(i))[1]));
                        schema.put(i,ultima);
                    }
                }
                if(this.Content instanceof ArrayList) {
                    for (int i = 0; i < map.size(); ++i) {
                        if (schema.containsKey(i) && !schema.containsValue(i)) {
                            aux.add(i);
                            if (schema.get(i) instanceof LeafJSON) {
                                ((ArrayList) this.Content).add((LeafJSON) schema.get(i));
                            } else {
                                LeafJSON sig = new LeafJSON();
                            }
                            break;
                        }
                    }
                }
            }

            private void buildInTree(HashMap map, Integer im){

            }

        }
    }
}
