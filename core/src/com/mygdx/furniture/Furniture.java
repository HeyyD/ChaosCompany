package com.mygdx.furniture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SamiH on 4.3.2017.
 */

public abstract class Furniture extends Actor{

    private int price = 0;
    private int sellPrice = 0;
    private Texture sheet;
    private boolean bought = false;

    public abstract void sell();

    public abstract void buy();

    abstract void rotate();


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

    public Texture getSheet(){
        return sheet;
    }
    public void setSheet(Texture t){
        this.sheet = t;
    }
    public boolean getBought(){
        return bought;
    }

    public void setBought(boolean bought){
        this.bought = bought;
    }

}
