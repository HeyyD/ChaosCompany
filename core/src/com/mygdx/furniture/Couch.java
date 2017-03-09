package com.mygdx.furniture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.StatsManager;

/**
 * Created by SamiH on 5.3.2017.
 */

public class Couch extends Furniture {

    private StatsManager        manager;
    private Texture             couchImg;
    private ChaosCompany        game;
    private int                 welfare = 10;

    public Couch(ChaosCompany g, float x, float y){
        game = g;
        manager = game.getManager();
        //Add benfits
        manager.setWelfare(manager.getWelfare() + welfare);
        //Set position, price and sellPrice
        setX(x);
        setY(y);
        setPrice(100);
        setSellPrice(50);

        manager.setMoney(manager.getMoney() - getPrice());
        couchImg = new Texture("couch.png");
    }

    @Override
    public void draw(Batch batch, float alfa) {
        batch.draw(couchImg, getX(), getY(), 1f, 1f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }


    @Override
    void create() {

    }

    @Override
    public void sell() {
        //minus benfits
        manager.setWelfare(manager.getWelfare()-welfare);
        //add money
        manager.setMoney(manager.getMoney() + getSellPrice());
        //Destroy couch
        remove();
    }
}