package com.mygdx.employees;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.chaoscompany.ChaosCompany;
import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

public class Programmer extends Employee{

    /**
     * Constructor
     * @param tileMap Tilemap Employee is spawned in
     * @param startTile Starting tile
     * @param width Width of employee
     * @param height Height of employee
     * @param skill Skill of employee
     */
    public Programmer(TileMap tileMap, Tile startTile, float width, float height, float skill) {
        super(new Texture("empAnimation.png"), tileMap, startTile, width, height, skill);
        this.profession = "PROGRAMMER";


    }

    /**
     * Called when employee is hired
     */
    @Override
    public void hire() {
            System.out.println(getManager().getEmployees());
            remove();
            setHired(true);
            getManager().setSalaries(getManager().getSalaries() - (int)salary);
            int x = 11;
            int y = 11;

            getManager().setProgrammingPower(getManager().getProgrammingPower() + (int)(skill*200));
            while (ChaosCompany.officeState.getTileMap().getTiles()[x][y].getIsFull() == true) {
                x = MathUtils.random(0, 9);
                y = MathUtils.random(0, 9);
            }
            ChaosCompany.officeState.getTileMap().getTiles()[x][y].setIsFull(true);

            setMap(ChaosCompany.officeState.getTileMap());
            setCurrentTile(ChaosCompany.officeState.getTileMap().getTiles()[x][y]);
            setLastDestination(ChaosCompany.officeState.getTileMap().getTiles()[x][y]);
            setPosition(getCurrentTile().getX(), getCurrentTile().getY());
            setPathfinding(new Pathfinding(ChaosCompany.officeState.getTileMap()));
            ChaosCompany.officeState.getobjectStage().addActor(this);
            getManager().setEmployees(getManager().getEmployees() + 1);
    }
}
