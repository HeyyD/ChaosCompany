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
        manager.setWelfare(manager.getWelfare() + welfare);
        System.out.println(manager.getWelfare());
        setPosX(x);
        setPosY(y);
        setAlive(true);
        couchImg = new Texture("couch.png");
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(couchImg, getPosX(), getPosY(), 1f, 1f);
    }


    @Override
    void create() {

    }

    @Override
    void sell() {
        setAlive(false);
        System.out.println(manager.getWelfare());
    }
}
