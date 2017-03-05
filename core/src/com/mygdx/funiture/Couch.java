package com.mygdx.funiture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
        //Test
        System.out.println(manager.getWelfare());

        manager.setWelfare(manager.getWelfare()+this.welfare);

        //Test
        System.out.println(manager.getWelfare());

        setPosX(x);
        setPosY(y);
        setAlive(true);
        couchImg = new Texture("couch.png");
    }

    @Override
    public void draw(Batch batch, float alpha) {
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
        setAlive(false);
        System.out.println(manager.getWelfare());
    }
}
