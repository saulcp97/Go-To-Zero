package com.mygdx.game.Cubos.Entidades.vida.Bestia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cardinal;
import com.mygdx.game.Chunk;
import com.mygdx.game.Cubos.Entidades.Entity;
import com.mygdx.game.Cubos.Entidades.bullet.Projectile;
import com.mygdx.game.Cubos.Entidades.vida.Life;

import java.util.ArrayList;

public class Wild extends Life {

    protected static Model NaniteModel = new ObjLoader().loadModel(Gdx.files.internal("data/img/3DModels/nanite/nanite.obj"));
    protected static Texture Bodyskin = new Texture(Gdx.files.internal("data/img/3dTexture/baldosasCeramica.png"));

    protected final String name;
    protected int angle;
    private int colorSign;

    private static Vector3 center = null;
    public static void borrar() {
        Wild.center = null;
    }

    public Wild(int x, int y, int z, String t) {
        super(x, y, z, (byte) 0);
        this.name = t;
        this.angle = 0;

        this.width = 1;
        this.height = 1;
        this.deep = 1;
        this.colorSign = 1;
    }

    @Override
    public void actual(int X, int Y) {
        this.setPosiD(X * (32 * 64) + this.coord.x + this.width / 2 - this.getDrawWidth() / 2, Y * (32 * 64) + this.coord.y + this.coord.z / 2);
        if (this.mod != null) {
            //this.mod.transform.translate(this.coord.x/64 + X * 32,0,(31 - this.coord.y/64) + ((2 - Y) * 32));
            //System.out.println("COordenadas de entidad " + this.coord.toString());

            Vector3 pos = new Vector3(this.coord.x / 64 + X * 32 + .5f, this.coord.z / 64 + .5f, (31 - this.coord.y / 64) + ((2 - Y) * 32) + .5f);
            Quaternion quaternion = new Quaternion(0, 0, 0, 0);

            this.mod.transform.set(pos, quaternion);
            this.mod.transform.rotate(Vector3.Y, (float) Math.toRadians(this.angle += 3));
        }
    }

    public void moviment(){
        this.coord.x += Math.cos(Math.PI * Math.random()) * 2;
        this.coord.y += Math.cos(Math.PI * Math.random()) * 2;
    }

    @Override
    public void Soul(Chunk ch, ArrayList<Entity> entidadesOut, int index) {
        ArrayList<Entity> entidades = ch.getEntities();

        switch(this.name) {
            case "Nanaits":
                //this.moviment();
                Entity aux;
                if (Wild.center == null) {
                    Wild.center = new Vector3(0, 0, 0);

                    int wildEnt = 1;
                    for (int i = index; i < entidades.size(); ++i) {
                        if (index != i) {
                            aux = entidades.get(i);
                            if (aux instanceof Wild) {
                                ++wildEnt;
                                Wild.center.add(aux.coord.x, aux.coord.y, aux.coord.z);
                            }
                        }
                    }


                    //System.out.println("Suma Distancia x: " + centr.x);

                    Wild.center.x = Wild.center.x / wildEnt;
                    Wild.center.y = Wild.center.y / wildEnt;
                    Wild.center.z = Wild.center.z / wildEnt;

                }
                //System.out.println("Centro: " + centr.x);
                double dist = 1;
                if ((Wild.center.x - this.coord.x) != 0 || (Wild.center.y - this.coord.y) != 0) {
                    dist = Math.sqrt((Wild.center.x - this.coord.x) * (Wild.center.x - this.coord.x) + (Wild.center.y - this.coord.y) * (Wild.center.y - this.coord.y));
                }

                //System.out.println("Posicion: " + this.coord.x + "Distancia: " + dist);

                if ((Wild.center.x - this.coord.x) != 0) this.coord.x += (Wild.center.x - this.coord.x) / dist;
                if ((Wild.center.y - this.coord.y) != 0) this.coord.y += (Wild.center.y - this.coord.y) / dist;

                boolean colision = true;

                break;

            case "Slime":
                if(this.actualLife > this.maxLife / 4) {
                    /*Test de particulas*/
                    if(this.count == 0 && (entidades.size() + entidadesOut.size()) < 10000 && System.currentTimeMillis() % 10 == 0) {
                        this.count = 100;

                        Projectile Particula = Cardinal.bulletPool.obtain();
                        Particula.init((int) this.coord.x, (int) this.coord.y, (int) this.coord.z + Particula.getDeep());

                        //Particula.setVel(new Vector3(0, 0, 1f));
                        Particula.setVel(apuntar(ch.getPlayer()));
                        Particula.setForce(new Vector3(0, 0, 0));
                        Particula.setDespawnDist(64 * 30);
                        entidadesOut.add(Particula);
                    }
                }
                break;

        }

        if(this.count != 0) {
            --this.count;
        }


        /*for(int i = 0; i < input.length; ++i){
            if(input[i] != null && input[i].rectify(this)){
                colision = false;
                break;
            }
        }

        if(colision){
            for(Entity i : entidades){
                if(i.rectify(this)){
                    break;
                }
            }
        }*/


        /*if(dist != NaN) {
            System.out.println("Distancia: " + dist);
            this.coord.x += (centr.x - this.coord.x) / dist;
            System.out.println("a Recorrer" + ((centr.x - this.coord.x))/dist);
            this.coord.y += (centr.y - this.coord.y) / dist;
            this.coord.z += (centr.z - this.coord.z) / dist;
        }*/

        //if(this.coord.x < 64) this.coord.x = 64;
        //if(this.coord.x > (2048 - 64) + this.width) this.coord.x = (2048 - 64) + this.width;
        //if(this.coord.y < 64) this.coord.y = 64;
        //if(this.coord.y > (2048 - 64) + this.height) this.coord.y = (2048 - 64) + this.height;
    }

    @Override
    public void loadModel(int x, int y) {
        this.mod = new ModelInstance(Wild.NaniteModel);
        this.mod.transform.translate(this.coord.x / 64 + x * 32, this.coord.z / 64 + .5f, (31 - this.coord.y / 64) + ((2 - y) * 32));
        this.mod.materials.get(0).set(TextureAttribute.createDiffuse(Bodyskin));
    }
}