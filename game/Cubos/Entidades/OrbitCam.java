package com.mygdx.game.Cubos.Entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GoToZero;

public class OrbitCam {
    private Vector3 position; //Simplemente en un radio de ~1m al rededor del personaje, la rotacion de 3 vectores
    private Vector3 rotation; //Punto al que mira, de momento no lo uso
    private Vector3 orbitCenter;
    private int anglH, anglV;
    private Camera cam; //Se tiene en cuenta el tamaño del mundo
    private float dist;
    private int chSize;

    public OrbitCam(Camera cam, int mapS, Vector3 playerPos){
        this.cam = cam;
        this.orbitCenter = playerPos;
        this.position = new Vector3();
        this.rotation = new Vector3();
        this.anglH = -90;
        this.anglV = 15;
        this.dist = 2;
        this.chSize = mapS / 2 + 1;
    }

    public void Tick(int PX, int PY) {
        if(Gdx.input.getX() < Gdx.graphics.getWidth()*.33f) {
            float a = (1f - ((float)Gdx.input.getX()) / (Gdx.graphics.getWidth()*.33f));
            this.angleHor((int)(8f * a));
        }
        this.cam.far = GoToZero.options.getRenderDist();
        if(Gdx.input.getX() > Gdx.graphics.getWidth()*.66f) {
            float a = ((((float)Gdx.input.getX() - Gdx.graphics.getWidth()*.66f)/ ((float)Gdx.graphics.getWidth() * .33f) ));
            this.angleHor((int)(-8f * (a)));
        }

        this.position.set(MathUtils.cosDeg(anglH) * MathUtils.cosDeg(anglV) * this.dist, MathUtils.sinDeg(anglV) * this.dist, MathUtils.sinDeg(anglH) * MathUtils.cosDeg(anglV) * this.dist);
        this.cam.position.set(position.x + (((float)40)/64)/2 + orbitCenter.x/64 + PX * 32 + .5f, position.y + orbitCenter.z/64 + 2f, 31 - position.z - orbitCenter.y/64 + ((2) - PY)*32 - (((float)20)/64)/2  + .5f);
        //this.cam.lookAt(orbitCenter.x /64 + PX * 32 + .5f, orbitCenter.z / 64 + 2f, orbitCenter.y / 64 + (2-PY)*32 + .5f);
        this.cam.normalizeUp();
        //cam.position.set(10,0,0);
        //cam.lookAt(0, 0, 0);

        //cam.rotateAround(this.cam.position, Vector3.Y, 90 - anglH);
    }

    public void angleHor(int i) {
        this.anglH += i;
        cam.rotateAround(this.cam.position, Vector3.Y, i);
    }

    public void angleVer(int j) {
        this.anglV += j;
        //cam.rotateAround(this.cam.position, Vector3.Y, j);
    }

    public void Zoom(float f) {
        if(dist + f> 1 && dist + f < 10) {
            this.dist += f;
        }
    }

    public int getAnglH() { return anglH; }

    /*this.deformationCam.set((float)Math.cos(this.pl.getAngle()*conv),(float)Math.sin(this.camAngle * conv),(float)Math.sin(this.pl.getAngle()*conv));
       this.camera.position.set(
                this.pl.coord.x/64  + .5f + this.game.mundo.getPunteroX() * 32 + this.deformationCam.x * 2,
            this.pl.coord.z/64 + .5f + 1.5f - this.deformationCam.y * 2,
            31 + .5f - (this.pl.coord.y/64 + (this.pl.getHeight()/64)/2) + (2 - this.game.mundo.getPunteroY()) * 32 - this.deformationCam.z * 2);

        this.camera.lookAt(
                this.camera.position.x - this.deformationCam.x,
            this.camera.position.y + this.deformationCam.y,
            this.camera.position.z + this.deformationCam.z);
    */



}
