package com.mygdx.funiture;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamiH on 4.3.2017.
 */

abstract class Funiture extends Actor {

    private float posX = 0;
    private float posY = 0;
    private boolean alive = false;

    abstract void create();

    abstract void sell();

    public void setPosX(float x){
        this.posX = x;
    }

    public float getPosX(){
        return this.posX;
    }

    public void setPosY(float y){
        this.posY = y;
    }

    public float getPosY(){
        return this.posY;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
