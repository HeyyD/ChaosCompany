package com.mygdx.funiture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.StatsManager;

/**
 * Created by SamiH on 5.3.2017.
 */

public class Couch extends Funiture {

    private StatsManager        manager;
    private Texture             couchImg;
    private int                 welfare = 10;

    public Couch(StatsManager m, float x, float y){
        manager = m;
        //Add benfits
        manager.setWelfare(manager.getWelfare() + welfare);
        System.out.println(manager.getWelfare());
        //Set position, price and sellPrice
        setPosX(x);
        setPosY(y);
        setPrice(100);
        setSellPrice(50);

        manager.setMoney(manager.getMoney() - getPrice());
        //because this couch is living being, we set his alive attribute to true.
        setAlive(true);
        couchImg = new Texture("couch.png");
    }

    @Override
    public void draw(Batch batch, float alfa) {
        batch.draw(couchImg, getPosX(), getPosY(), 1f, 1f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }


    @Override
    void create() {

    }

    @Override
    void sell() {
        //Destroy couch >:)
        setAlive(false);
        //minus benfits
        manager.setWelfare(manager.getWelfare()-welfare);
        System.out.println(manager.getWelfare());
        //add money
        manager.setMoney(manager.getMoney() + getSellPrice());
    }
}