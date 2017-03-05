package com.mygdx.funiture;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamiH on 4.3.2017.
 */

abstract class Funiture extends Actor{

    private float posX = 0;
    private float posY = 0;
    private int price = 0;
    private int sellPrice = 0;

    abstract void create();

    public abstract void sell();


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

    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

    public void setSellPrice(int sellPrice){
        this.sellPrice = sellPrice;
    }

    public int getSellPrice(){
        return sellPrice;
    }
}
