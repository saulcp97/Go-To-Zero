package com.mygdx.game.Cubos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Cubos.Entidades.Entity;
import com.mygdx.game.Cubos.Entidades.vida.NPC.NPC;

public class Escaleras extends Block {

    public static Texture escaleraSimple0 = new Texture(Gdx.files.internal("data/img/escaleraSimple.png"));
    public static Texture escaleraSimple1 = new Texture(Gdx.files.internal("data/img/escaleraLateral.png"));

    //Las escaleras son de 4 escalones
    public Escaleras(int x, int y, int z, byte t, byte Orient) {
        super(x, y, z, Byte.MIN_VALUE);
        this.orient = Orient;
        switch (this.orient) {
            case 1:
                this.setTexture(escaleraSimple1);
                break;
            case 0:
            default:
                this.setTexture(escaleraSimple0);
                break;
        }
    }

    public Escaleras (byte t, byte Orient) {
        super(0, 0, 0, Byte.MIN_VALUE);
        this.orient = Orient;
        switch (this.orient) {
            case 1:
                this.setTexture(escaleraSimple1);
                break;
            case 0:
            default:
                this.setTexture(escaleraSimple0);
                break;
        }
    }

    @Override
    public boolean rectify(Block b, byte dir) {
        if (this.colision(b)) {
            switch (this.orient) {
                case 0:
                    //if((dir == 1 || dir == 3) && !b.colision()){}
                    if (dir == 5 || dir == 0) {
                        if (16 > b.coord.y + b.getHeight() - this.coord.y) {
                            if(b.coord.z < this.coord.z + 16) {
                                b.coord.z = this.coord.z + 16;
                                return true;
                            } else {
                                return false;
                            }
                        } else if (32 > b.coord.y + b.getHeight() - this.coord.y) {
                            if(b.coord.z < this.coord.z + 32) {
                                b.coord.z = this.coord.z + 32;
                                return true;
                            } else {
                                return false;
                            }
                        } else if (48 > b.coord.y + b.getHeight() - this.coord.y) {
                            if(b.coord.z < this.coord.z + 48) {
                                b.coord.z = this.coord.z + 48;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            b.coord.z = this.coord.z + this.deep;
                        }

                    } else if (dir == 1 || dir == 3 || dir == 2) {
                        if (this.coord.z >= b.coord.z) {
                            super.rectify(b, dir);
                        }
                    }
                    break;
                case 1:
                    //if((dir == 1 || dir == 3) && !b.colision()){}
                    if (dir == 5 || dir == 1) {
                        if (16 > b.coord.x + b.getWidth() - this.coord.x) {
                            if(b.coord.z < this.coord.z + 16) {
                                b.coord.z = this.coord.z + 16;
                                return true;
                            } else {
                                return false;
                            }
                        } else if (32 > b.coord.x + b.getWidth() - this.coord.x) {
                            if(b.coord.z < this.coord.z + 32) {
                                b.coord.z = this.coord.z + 32;
                                return true;
                            } else {
                                return false;
                            }
                        } else if (48 > b.coord.x + b.getWidth() - this.coord.x) {
                            if(b.coord.z < this.coord.z + 48) {
                                b.coord.z = this.coord.z + 48;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            b.coord.z = this.coord.z + this.deep;
                        }
                    } else if (dir == 0 || dir == 2 || dir == 3) {
                        if (this.coord.z >= b.coord.z) {
                            super.rectify(b, dir);
                        }
                    }
                    break;

                case 3:
                    //if((dir == 1 || dir == 3) && !b.colision()){}
                    if (dir == 5 || dir == 3) {
                        if (16 > b.coord.x - this.coord.x) {
                            b.coord.z += this.coord.z + this.deep - b.coord.z;
                        } else if (32 > b.coord.x - this.coord.x) {
                            b.coord.z += this.coord.z + 48 - b.coord.z;
                        } else if (48 > b.coord.x - this.coord.x) {
                            b.coord.z += this.coord.z + 32 - b.coord.z;
                        } else {
                            b.coord.z += this.coord.z + 16 - b.coord.z;
                        }
                    } else if (dir == 0 || dir == 1 || dir == 2) {
                        if (this.coord.z >= b.coord.z) {
                            super.rectify(b, dir);
                        }
                    }
                    break;
                case 2:
                    //if((dir == 1 || dir == 3) && !b.colision()){}
                    if (dir == 5 || dir == 2) {
                        if (16 > b.coord.y - this.coord.y) {
                            b.coord.z += this.coord.z + this.deep - b.coord.z;
                        } else if (32 > b.coord.y - this.coord.y) {
                            b.coord.z += this.coord.z + 48 - b.coord.z;
                        } else if (48 > b.coord.y - this.coord.y) {
                            b.coord.z += this.coord.z + 32 - b.coord.z;
                        } else {
                            b.coord.z += this.coord.z + 16 - b.coord.z;
                        }
                    } else if (dir == 1 || dir == 3 || dir == 0) {
                        if (this.coord.z >= b.coord.z) {
                            super.rectify(b, dir);
                        }
                    }
                    break;
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean rectify(Entity ent) {
        if (this.colision(ent)) {
            System.out.println("Escalera 2");

            switch (this.orient) {
                case 0:
                    if (16 > ent.coord.y + ent.getHeight() - this.coord.y && ent.coord.z < this.coord.z + 16) {
                        ent.coord.z = this.coord.z + 16;
                        return false;
                    } else if (32 > ent.coord.y + ent.getHeight() - this.coord.y && ent.coord.z < this.coord.z + 32) {
                        ent.coord.z = this.coord.z + 32;
                        return false;
                    } else if (48 > ent.coord.y + ent.getHeight() - this.coord.y && ent.coord.z < this.coord.z + 48) {
                        ent.coord.z = this.coord.z + 48;
                        return false;
                    } else if(ent.coord.z < this.coord.z + this.deep){
                        ent.coord.z = this.coord.z + this.deep;
                        return false;
                    }
                case 1:
                    if (16 > ent.coord.x - this.coord.x) {
                        ent.coord.z += this.coord.z + this.deep - ent.coord.z;
                        return true;
                    } else if (32 > ent.coord.x - this.coord.x) {
                        ent.coord.z += this.coord.z + 48 - ent.coord.z;
                        return true;
                    } else if (48 > ent.coord.x - this.coord.x) {
                        ent.coord.z += this.coord.z + 32 - ent.coord.z;
                        return true;
                    } else {
                        ent.coord.z += this.coord.z + 16 - ent.coord.z;
                        return true;
                    }
                case 2:
                    if (16 > ent.coord.y - this.coord.y) {
                        ent.coord.z += this.coord.z + this.deep - ent.coord.z;
                        return true;
                    } else if (32 > ent.coord.y - this.coord.y) {
                        ent.coord.z += this.coord.z + 48 - ent.coord.z;
                        return true;
                    } else if (48 > ent.coord.y - this.coord.y) {
                        ent.coord.z += this.coord.z + 32 - ent.coord.z;
                        return true;
                    } else {
                        ent.coord.z += this.coord.z + 16 - ent.coord.z;
                        return true;
                    }
                case 3:
                    if (16 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 16 - ent.coord.z;
                        return false;
                    } else if (32 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 32 - ent.coord.z;
                        return false;
                    } else if (48 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 48 - ent.coord.z;
                        return false;
                    } else {
                        ent.coord.z += this.coord.z + this.deep - ent.coord.z;
                        return false;
                    }
            }

            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean rectify(NPC ent, int dist,boolean a) {
        if(colision(ent)){
            System.out.println("Colision escaleras 2.0");
            switch (this.orient) {
                case 0:
                    if (16 > ent.coord.y + ent.getHeight() - this.coord.y) {
                        ent.coord.z = this.coord.z + 16;
                        return true;
                    } else if (32 > ent.coord.y + ent.getHeight() - this.coord.y) {
                        ent.coord.z = this.coord.z + 32;
                        return true;
                    } else if (48 > ent.coord.y + ent.getHeight() - this.coord.y) {
                        ent.coord.z = this.coord.z + 48;
                        return true;
                    } else if(ent.coord.z < this.coord.z + this.deep){
                        ent.coord.z = this.coord.z + this.deep;
                        return true;
                    }
                case 1:
                    if (16 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 16 - ent.coord.z;
                        return true;
                    } else if (32 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 32 - ent.coord.z;
                        return true;
                    } else if (48 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 48 - ent.coord.z;
                        return true;
                    } else {
                        ent.coord.z += this.coord.z + this.deep - ent.coord.z;
                        return true;
                    }
                case 2:
                    if (16 > ent.coord.y - this.coord.y) {
                        ent.coord.z += this.coord.z + this.deep - ent.coord.z;
                        return true;
                    } else if (32 > ent.coord.y - this.coord.y) {
                        ent.coord.z += this.coord.z + 48 - ent.coord.z;
                        return true;
                    } else if (48 > ent.coord.y - this.coord.y) {
                        ent.coord.z += this.coord.z + 32 - ent.coord.z;
                        return true;
                    } else {
                        ent.coord.z += this.coord.z + 16 - ent.coord.z;
                        return true;
                    }
                case 3:
                    if (16 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 16 - ent.coord.z;
                        return true;
                    } else if (32 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 32 - ent.coord.z;
                        return true;
                    } else if (48 > ent.coord.x + ent.getWidth() - this.coord.x) {
                        ent.coord.z += this.coord.z + 48 - ent.coord.z;
                        return true;
                    } else {
                        ent.coord.z += this.coord.z + this.deep - ent.coord.z;
                        return true;
                    }
            }

            ent.move(ent.getAngle() + 180, (byte)dist,a);
            return true;
        }
        return false;
    }

    @Override
    public void build3DPart(int x, int y, int z) { //Podriamos añadir variaciones para que un mismo bloque tenga variantes
        this.b3D = new p3D('E', this.type, this.orient, x, y , z);
    }

    @Override
    public boolean colision(Block b) {
        return super.colision(b);
    }
}