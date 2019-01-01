package com.mygdx.game.Cubos;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

import static com.mygdx.game.Cubos.Block3D.textures;

public class Deformed extends Block {

    private Vector3 v00; //(0,0,0)
    private Vector3 v01; //(0,1,0)
    private Vector3 v02; //(1,1,0)
    private Vector3 v03; //(1,0,0)

    private Vector3 v10; //(0,0,1)
    private Vector3 v11; //(0,1,1)
    private Vector3 v12; //(1,1,1)
    private Vector3 v13; //(1,0,1)

    public Deformed(int x, int y, int z, byte t, byte o) {
        super(x, y, z, t);
        this.orient = 0;

        this.v00 = new Vector3(0,0,0);
        this.v01 = new Vector3(1,0,0);
        this.v02 = new Vector3(0,0,1);
        this.v03 = new Vector3(1,0,1);

        this.v10 = new Vector3(0,1,0);
        this.v11 = new Vector3(1,1,0);
        this.v12 = new Vector3(0,1,1);
        this.v13 = new Vector3(1,1,1);

        /*
            v10----v11
             |      |
             |      |
            v12----v13
         */
    }

    public Deformed(byte t, byte o) {
        this(0,0,0,t,o);
    }


    public float[] getHeights() {
        return new float[]{v10.y,v11.y,v12.y,v13.y};
    }
    public void setHeight(float h0, float h1, float h2, float h3) {
        this.v10.y = h0;
        this.v11.y = h1;
        this.v12.y = h2;
        this.v13.y = h3;
    }
    public void setV0 (float h) {   this.v10.y = h; }
    public void setHeightByAngles(Vector3 i, Vector3 j) {
        this.v11.y = v10.y + i.y;
        this.v12.y = v10.y + j.y;
        this.v13.y = v10.y + i.y + j.y;
    }

    @Override
    public void build3DPart(int x, int y, int z) { //Podriamos añadir variaciones para que un mismo bloque tenga variantes
        this.b3D = new p3D('D', this.type, this.orient, x, y , z);

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;

        meshBuilder = modelBuilder.part("base", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,

                new Material(TextureAttribute.createDiffuse(textures[1])));

        meshBuilder.rect(v01,v00,v10,v11,Vector3.Z);
        meshBuilder.rect(v03,v01,v11,v13,Vector3.X);
        meshBuilder.rect(v02,v03,v13,v12,Vector3.X);
        meshBuilder.rect(v00,v02,v12,v10,Vector3.Z);

        meshBuilder.rect(v12,v13,v11,v10,Vector3.Y);

        this.b3D.setModelInstance(new ModelInstance(modelBuilder.end()));
    }

    @Override
    public boolean rectify(NPC ent, int dist, boolean a) {
        if(this.colision(ent)) {
            if (ent.coord.z >= this.coord.z + this.v10.z
                    && ent.coord.z >= this.coord.z + this.v11.z
                    && ent.coord.z >= this.coord.z + this.v12.z
                    && ent.coord.z >= this.coord.z + this.v13.z) {
                return false;
            } else {
                ent.move(ent.getAngle() + 180, (byte)dist,a);
                return true;
            }
        } else {
            return false;
        }
    }

    public int reduce(){
        int height = 0;

        while(this.v10.y > 1 && this.v11.y > 1 && this.v12.y > 1 && this.v13.y > 1) {
            --this.v10.y;
            --this.v11.y;
            --this.v12.y;
            --this.v13.y;
            ++height;
        }
        return height;
    }


    @Override
    public boolean rectify(Block b, byte dir) {
        if(dir != 5) {
            if (this.colision(b)) {
                /* Versión simplificada mas abajo
                if (b.coord.z >= this.coord.z + this.v10.z
                        && b.coord.z >= this.coord.z + this.v11.z
                        && b.coord.z >= this.coord.z + this.v12.z
                        && b.coord.z >= this.coord.z + this.v13.z) {
                    return true;
                } else {
                    return super.rectify(b, dir);
                }*/
                return b.coord.z >= this.coord.z + this.v10.z && b.coord.z >= this.coord.z + this.v11.z && b.coord.z >= this.coord.z + this.v12.z && b.coord.z >= this.coord.z + this.v13.z || super.rectify(b, dir);
            } else { return false; }

        } else {
            if (this.colision(b)) {
                if (b.coord.z > this.coord.z + this.deep * this.v10.z
                        && b.coord.z > this.coord.z + this.deep * this.v11.y
                        && b.coord.z > this.coord.z + this.deep * this.v12.y
                        && b.coord.z > this.coord.z + this.deep * this.v13.y) {
                    return false;
                } else {
                    b.coord.z = this.coord.z + this.deep * Math.min(Math.min(this.v10.y,this.v11.y),Math.min(this.v12.y,this.v13.y));
                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
