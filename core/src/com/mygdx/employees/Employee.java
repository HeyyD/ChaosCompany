package com.mygdx.employees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.OfficeState;
import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

import java.util.ArrayList;

public abstract class Employee extends Actor {

    //texture of the employee
    protected Texture texture;
    protected EmployeeMenu menu = null;
    //The float that determines how well the employee can do his/her job 0-1
    private float skill;
    //Boolean to determine if the employee is free to work
    private boolean isAvailable;

    //Pathfinding variables
    private Tile        currentTile = null;
    private Vector2     targetPosition = null;
    private int         currentTileIndex = 0;

    private Pathfinding pathfinding = null;
    private ArrayList<Tile> path = new ArrayList<Tile>();

    public Employee(Texture texture, TileMap tileMap, Tile startTile, float width, float height, float skill){
        this.skill = skill;
        isAvailable = true;
        this.texture = texture;
        setSize(width, height);
        setPosition(startTile.getX(), startTile.getY());
        setBounds(getX(), getY(), getWidth(), getHeight());
        currentTile = startTile;
        pathfinding = new Pathfinding(tileMap);
        this.addListener(new EmployeeListener());
    }

    @Override
    public void draw(Batch batch, float alpha){
        act(Gdx.graphics.getDeltaTime());
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if(targetPosition != null)
            move();
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    private void move(){
        float speed = Gdx.graphics.getDeltaTime() * 1.5f;
        Vector2 currentPosition = new Vector2(getX(), getY());
        float distance = currentPosition.dst(targetPosition);
        Vector2 direction = new Vector2(targetPosition.x - currentPosition.x, targetPosition.y - currentPosition.y).nor();
        Vector2 velocity = new Vector2(direction.x * speed, direction.y * speed);

        if(distance >= 0.1f){
            setPosition(currentPosition.x + velocity.x, currentPosition.y + velocity.y);
        }
        else if(currentTileIndex < path.size() - 1){
            currentTileIndex++;
            targetPosition = new Vector2(path.get(currentTileIndex).getX(), path.get(currentTileIndex).getY());
        }
        else{
            currentTileIndex = 0;
            targetPosition = null;
            path.clear();
        }
    }

    public void giveDestination(Tile targetTile){

        path = pathfinding.Path(currentTile, targetTile);
        targetPosition = new Vector2(path.get(0).getX(), path.get(0).getY());

    }

    private class EmployeeListener extends InputListener{

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            if(Employee.this.menu == null){
                Employee.this.menu = new EmployeeMenu(Employee.this, ChaosCompany.officeState.getobjectStage(), ChaosCompany.officeState.getCam());
            }
        }
    }
}
