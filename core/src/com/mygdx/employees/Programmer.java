package com.mygdx.employees;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

public class Programmer extends Employee{

    public Programmer(TileMap tileMap, Tile startTile, float width, float height, float skill) {
        super(new Texture("employee.png"), tileMap, startTile, width, height, skill);
        //test
        giveDestination(tileMap.getTiles()[8][8]);
    }
}
