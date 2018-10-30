package com.mygdx.game.simulation.Alma;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Path {

    public static final byte D0 = 0b00000000;
    public static final byte D1 = 0b00000001;
    public static final byte D2 = 0b00000010;
    public static final byte D3 = 0b00000011;

    public static final byte D4 = 0b00000100;
    public static final byte D5 = 0b00000101;
    public static final byte D6 = 0b00000110;
    public static final byte D7 = 0b00000111;

    //Se suman vect.mulAdd(v0, n) se multiplica vect += v0 * n
    public static final Vector3 v0 = new Vector3(0,1,0);
    public static final Vector3 v1 = new Vector3(1,0,0);
    public static final Vector3 v2 = new Vector3(0,-1,0);
    public static final Vector3 v3 = new Vector3(-1,0,0);

    public static final Vector3 v4 = new Vector3( MathUtils.cosDeg(45), MathUtils.sinDeg(45),0);
    public static final Vector3 v5 = new Vector3( MathUtils.cosDeg(-45), MathUtils.sinDeg(-45),0);
    public static final Vector3 v6 = new Vector3(MathUtils.cosDeg(225), MathUtils.sinDeg(225),0);
    public static final Vector3 v7 = new Vector3(MathUtils.cosDeg(135), MathUtils.sinDeg(135),0);

    public float value;
    public ArrayList<Byte> dir;
    public Vector3 fin;

    public Path(Vector3 v) {
        this.value = 0f;
        this.dir = new ArrayList<Byte>();
        this.fin = v.cpy();
    }

    public Path(Path pre,byte d, float dist) {
        this.value = pre.value + dist;
        this.dir = (ArrayList)pre.dir.clone();
        this.dir.add(d);
        switch (d) {
            case D0:
                this.fin = pre.fin.cpy().mulAdd(Path.v0, dist);
                break;
            case D1:
                this.fin = pre.fin.cpy().mulAdd(Path.v1, dist);
                break;
            case D2:
                this.fin = pre.fin.cpy().mulAdd(Path.v2, dist);
                break;
            case D3:
                this.fin = pre.fin.cpy().mulAdd(Path.v3, dist);
                break;
            default:
                this.fin = pre.fin.cpy();
                break;
        }
    }

    public void addToValue(float f) {
        this.value += f;
    }

    public void addDir(byte b) {
        this.dir.add(b);
    }

    public Path[] getKids(int div, float dist, Vector3 end) {
        Path[] kids = new Path[4];

        float distance = this.fin.dst(end);

        kids[0] = new Path(this, D0, dist);
        kids[0].value = kids[0].fin.dst(end);

        kids[1] = new Path(this, D1, dist);
        kids[1].value = kids[1].fin.dst(end);

        kids[2] = new Path(this, D2, dist);
        kids[2].value = kids[2].fin.dst(end);

        kids[3] = new Path(this, D3, dist);
        kids[3].value = kids[3].fin.dst(end);
        return kids;
    }

    public String getPathString() {
        String res = "";
        for (int i = 0; i < this.dir.size(); ++i) {
            switch (this.dir.get(i)) {
                case 0:
                    res = res.concat("+y");
                    break;
                case 1:
                    res = res.concat("+x");
                    break;
                case 2:
                    res = res.concat("-y");
                    break;
                case 3:
                    res = res.concat("-x");
                    break;
            }
            if(i < this.dir.size() - 1) {
                res = res.concat(",");
            }
        }

        return res;
    }

}



/*

function SMA-star(problem): path
    queue: set of nodes, ordered by f-cost;
begin
    queue.insert(problem.root-node);

    while True do begin
        if queue.empty() then return failure; //there is no solution that fits in the given memory
        node := queue.begin(); // min-f-cost-node
        if problem.is-goal(node) then return success;

        s := next-successor(node)
        if !problem.is-goal(s) && depth(s) == max_depth then
            f(s) := inf;
            // there is no memory left to go past s, so the entire path is useless
        else
            f(s) := max(f(node), g(s) + h(s));
            // f-value of the successor is the maximum of
            //      f-value of the parent and
            //      heuristic of the successor + path length to the successor
        endif

        if no more successors then
            update f-cost of node and those of its ancestors if needed

        if node.successors ⊆ queue then queue.remove(node);
         // all children have already been added to the queue via a shorter way
        if memory is full then begin
            badNode := shallowest node with highest f-cost;
            for parent in badNode.parents do begin
                parent.successors.remove(badNode);
                if needed then queue.insert(parent);
            endfor
        endif

        queue.insert(s);
    endwhile
end



















 */