package com.mygdx.furniture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.map.Tile;

/**
 * Created by SamiH on 4.3.2017.
 */

public abstract class Furniture extends Actor{

    private int price = 0;
    private int sellPrice = 0;
    //Transparency
    private float alpha = 0.5f;
    private Texture sheet;
    private boolean bought = false;
    private boolean isMoving = false;
    private Tile tile;

    private FurnitureButtons    buttons;

    //check if computer is available for use
    private boolean isAvailable = true;

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

    public float getAlpha(){
        return alpha;
    }
    public void setAlpha(float alpha){
        this.alpha = alpha;
    }
    public boolean getIsMoving(){
        return isMoving;
    }
    public void setIsMoving(boolean isMoving){
        this.isMoving = isMoving;
    }

    public FurnitureButtons getButtons(){
        return buttons;
    }

    public void setButtons(FurnitureButtons buttons){
        this.buttons = buttons;
    }
    public void setTile(Tile tile){
        this.tile = tile;
    }
    public Tile getTile(){
        return tile;
    }

    public boolean getIsAvailabel(){
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
}
