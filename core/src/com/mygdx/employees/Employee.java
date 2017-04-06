package com.mygdx.employees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.UI.EmpMenu;
import com.mygdx.game.ChaosCompany;
import com.mygdx.game.OfficeState;
import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

import java.util.ArrayList;

public abstract class Employee extends Actor {

    //texture of the employee
    protected Texture           sheet;

    //Temporary Array to store all the frames
    protected TextureRegion[][] tmp;

    //direction
    private int         dir = 2;

    //boolean which tells if employee is moving
    private boolean     moving = false;

    //boolean which tells if employee is hired or not
    private boolean     hired = false;

    //split into 4 different arrays for 4 different animations
    protected TextureRegion[]   frames0;
    protected TextureRegion[]   frames1;
    protected TextureRegion[]   frames2;
    protected TextureRegion[]   frames3;

    //Animation array to store the animations for each direction
    protected Animation<TextureRegion>[]       animations;

    //Current frame that is being drawn
    protected TextureRegion     currentFrame;

    //Float that is used to change texture
    private float               stateTime;

    //Number of columns and rows in Animation sheet
    private final int           FRAME_COLS = 4;
    private final int           FRAME_ROWS = 4;

    protected EmpMenu menu = null;
    protected String profession = null;

    //The float that determines how well the employee can do his/her job 0-5
    public float skill;

    //Boolean to determine if the employee is free to work
    private boolean isAvailable;

    //Pathfinding variables
    private Tile        currentTile = null;
    private Vector2     targetPosition = null;
    private int         currentTileIndex = 0;
    private boolean     walking = false;

    private Pathfinding pathfinding = null;
    private ArrayList<Tile> path = new ArrayList<Tile>();

    //test
    private TileMap map;
    private TextureRegion[] regions;

    public Employee(Texture texture, TileMap tileMap, Tile startTile, float width, float height, float skill){
        this.skill = skill;
        isAvailable = true;
        setSize(width, height);
        setPosition(startTile.getX(), startTile.getY());
        setBounds(getX(), getY(), getWidth(), getHeight());
        currentTile = startTile;
        pathfinding = new Pathfinding(tileMap);
        this.addListener(new EmployeeListener());

        //Load sheet and use Texture Filter
        sheet = texture;
        sheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //Split sheet to 2D TextureRegion array
        tmp = TextureRegion.split(
                sheet,
                sheet.getWidth() / FRAME_COLS,
                sheet.getHeight() / FRAME_ROWS
        );

        //Split different animations into different 1d arrays
        frames0 = transformTo1D(tmp,0);
        frames1 = transformTo1D(tmp,1);
        frames2 = transformTo1D(tmp,2);
        frames3 = transformTo1D(tmp,3);

        animations = new Animation[4];

        //Store different animations into different arrays
        animations[0] = new Animation(1/4f, frames0);
        animations[1] = new Animation(1/4f, frames1);
        animations[2] = new Animation(1/4f, frames2);
        animations[3] = new Animation(1/4f, frames3);

        //Float animations are using to change the frame
        stateTime = 1f;

        currentFrame = animations[0].getKeyFrame(stateTime, true);

        //test
        map = tileMap;
    }

    @Override
    public void draw(Batch batch, float alpha){
        act(Gdx.graphics.getDeltaTime());
        stateTime += Gdx.graphics.getDeltaTime();

        //Get frame from animation frame according direction where employee is going
        if(moving) {
            currentFrame = animations[dir].getKeyFrame(stateTime, true);
        }else{
            //If employee is not moving, get texture from orginal TextureRegion arrays.
            switch(dir){
                case 0:
                    currentFrame = frames0[0];
                    break;
                case 1:
                    currentFrame = frames1[0];
                    break;
                case 2:
                    currentFrame = frames2[0];
                    break;
                case 3:
                    currentFrame = frames3[0];
                    break;
            }
        }
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());

        //Move if position is given and update moving status
        if(targetPosition != null) {
            move();
            moving = true;
        }else{
            moving = false;
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }


    public abstract void hire();

    private void move(){
        float speed = Gdx.graphics.getDeltaTime() * 1.5f;
        Vector2 currentPosition = new Vector2(getX(), getY());
        float distance = currentPosition.dst(targetPosition);
        Vector2 direction = new Vector2(targetPosition.x - currentPosition.x, targetPosition.y - currentPosition.y).nor();
        Vector2 velocity = new Vector2(direction.x * speed, direction.y * speed);

        //Change direction of Employee
        if(direction.x > 0 && direction.y > 0){
            dir = 1;
        }else if(direction.x > 0){
            dir = 2;
        }else if(direction.x < 0 && direction.y > 0){
            dir = 0;
        }else{
            dir = 3;
        }

        if(distance >= 0.02f){
            setPosition(currentPosition.x + velocity.x, currentPosition.y + velocity.y);
        }
        else if(currentTileIndex < path.size() - 1){
            currentTileIndex++;
            targetPosition = new Vector2(path.get(currentTileIndex).getX(), path.get(currentTileIndex).getY());
        }
        else {
            currentTileIndex = 0;
            targetPosition = null;
            currentTile = path.get(path.size() - 1);
            path.clear();
            walking = false;
        }
    }

    public void giveDestination(Tile targetTile){

        boolean setFull = targetTile.getIsFull();

        if (walking) {
            targetPosition = null;
            currentTile = path.get(currentTileIndex);
            currentTileIndex = 0;
        }

        targetTile.setIsFull(false);
        path = pathfinding.Path(currentTile, targetTile);
        walking = true;
        if(setFull)
            targetTile.setIsFull(true);

        if(path != null && path.size() > 0)
            targetPosition = new Vector2(path.get(0).getX(), path.get(0).getY());

    }

    private class EmployeeListener extends InputListener{

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            if(Employee.this.menu == null){
                if(hired) {
                    Employee.this.menu = new EmpMenu(Employee.this, ChaosCompany.officeState.getMovingUiStage(), getX()+1,getY());
                }else{
                    Employee.this.menu = new EmpMenu(Employee.this, ChaosCompany.hireState.getMovingUiStage(), getX()+1, getY());
                }
            }
        }
    }

    public ArrayList<Tile> getPath(){
        return path;
    }


    //Method that transforms one row of the 2D TextureRegion Array into 1D TextureRegion array
    private TextureRegion[] transformTo1D(TextureRegion[][] tmp, int row){
        TextureRegion [] frames = new TextureRegion[FRAME_COLS];
        int index = 0;

        for (int i = 0; i < FRAME_ROWS; i++) {
                frames[index] = tmp [row][i];
                index++;
        }
        return frames;
    }

    public boolean getHired(){
        return this.hired;
    }
    public void setHired(boolean hired) {
        this.hired = hired;
    }
    public void setMenu(EmpMenu menu){
        this.menu = menu;
    }
    public void setUiStage(Stage uiStage){

    }

    public void findRandomPlace(){
        int x = 0;
        int y = 0;

        while(map.getTiles()[x][y].getIsFull()){
            x = MathUtils.random(0, map.getTiles().length - 1);
            y = MathUtils.random(0, map.getTiles()[0].length - 1);
        }

        giveDestination(map.getTiles()[x][y]);
    }

    public Pathfinding getPathfinding() {
        return pathfinding;
    }

    public void setMap(TileMap map){
        this.map = map;
    }

    public void setPathfinding(Pathfinding pathfinding) {
        this.pathfinding = pathfinding;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public boolean getIsAvailabel(){
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

}
