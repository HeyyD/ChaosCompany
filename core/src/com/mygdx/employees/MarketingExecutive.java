package com.mygdx.employees;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.ChaosCompany;
import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

/**
 * Created by SamiH on 20.4.2017.
 */

public class MarketingExecutive extends Employee{

    public MarketingExecutive(TileMap tileMap, Tile startTile, float width, float height, float skill) {
        super(new Texture("MarketingAnimation.png"), tileMap, startTile, width, height, skill);
        this.profession = "MARKETING";
    }

    @Override
    public void hire() {
        System.out.println(getManager().getEmployees());
        remove();
        setHired(true);
        int x = 11;
        int y = 11;
        //asetetaan tyontekija random paikkaan kentalla ja varataan sille tila
        while (ChaosCompany.officeState.getTileMap().getTiles()[x][y].getIsFull() == true) {
            x = MathUtils.random(0, 9);
            y = MathUtils.random(0, 9);
        }
        ChaosCompany.officeState.getTileMap().getTiles()[x][y].setIsFull(true);

        setMap(ChaosCompany.officeState.getTileMap());
        setMap(ChaosCompany.officeState.getTileMap());
        setCurrentTile(ChaosCompany.officeState.getTileMap().getTiles()[x][y]);
        setPosition(getCurrentTile().getX(), getCurrentTile().getY());
        setPathfinding(new Pathfinding(ChaosCompany.officeState.getTileMap()));
        ChaosCompany.officeState.getobjectStage().addActor(this);
        getManager().setEmployees(getManager().getEmployees() + 1);
    }
}