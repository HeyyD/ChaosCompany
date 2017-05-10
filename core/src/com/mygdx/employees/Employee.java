package com.mygdx.employees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.UI.EmpMenu;
import com.mygdx.chaoscompany.ChaosCompany;
import com.mygdx.chaoscompany.StatsManager;
import com.mygdx.map.Tile;
import com.mygdx.map.TileMap;

import java.util.ArrayList;

/**
 * Employees of game
 */
public abstract class Employee extends Actor {

    /**Texture of the employee*/
    protected Texture           sheet;

    /**
     *Temporary Array to store all the frames
     */
    protected TextureRegion[][] tmp;

    /**
     * Direction of Employee
     */
    private int         dir = 2;

    /**
     * boolean which tells if employee is moving
     */
    private boolean     moving = false;

    /**
     * boolean which tells if employee is hired or not
     */
    private boolean     hired = false;

    /**
     * Employee animation for each direction
     */
    protected TextureRegion[]   frames0;
    /**
     * Employee animation for each direction
     */
    protected TextureRegion[]   frames1;
    /**
     * Employee animation for each direction
     */
    protected TextureRegion[]   frames2;
    /**
     * Employee animation for each direction
     */
    protected TextureRegion[]   frames3;

    /**
     * Animations of employee
     */
    protected Animation<TextureRegion>[]       animations;

    /**
     * Current frame that is being drawn
     */
    protected TextureRegion     currentFrame;

    /**
     * Used to change texture
     */
    private float               stateTime;

    //Number of columns and rows in Animation sheet
    private final int           FRAME_COLS = 4;
    private final int           FRAME_ROWS = 4;

    /**
     * Employees menu
     */
    protected EmpMenu menu = null;
    /**
     * Employees profession (ARTIST, PROGRAMMER or MARKETING)
     */
    protected String profession = null;

    /**The float that determines how well the employee can do his/her job 1-5*/
    public float skill;

    /**
     * salary of employee
     */
    float salary;
    /**
     * multiplier that gives a bit randomness to salary
     */
    float expensiveness;

    /**
     * Is employee free to work
     */
    private boolean isAvailable;

    /**
     * Tile that employee is currently standing on
     */
    private Tile        currentTile = null;
    /**
     * Last destination of employee
     */
    private Tile        lastDestination = null;
    /**
     * Position that is given to path finding.
     */
    private Vector2     targetPosition = null;
    /**
     * Current tiles index number.
     */
    private int         currentTileIndex = 0;
    /**
     * Is employee walking or not
     */
    private boolean     walking = false;

    /**
     * PathFinding
     */
    private Pathfinding pathfinding = null;
    /**
     * Tiles for employees pathfinding
     */
    private ArrayList<Tile> path = new ArrayList<Tile>();

    /**
     * Are employee in officestates or hirestates tilemap
     */
    private TileMap map;


    /**
     * manager of game
     */
    private StatsManager manager = null;

    /**
     * Gives the employee its texture, where he is, starting position on the map, draw width and height and how
     * skilfull he is
     * @param texture Texture of the employee
     * @param tileMap In what tile map the employee is on (office or job center)
     * @param startTile First tile where the employee is on
     * @param width
     * @param height
     * @param skill Skill level of the employee
     */
    public Employee(Texture texture, TileMap tileMap, Tile startTile, float width, float height, float skill){
        this.skill = skill;
        expensiveness = MathUtils.random(0.7f, 1.3f);
        salary = skill * 1000 * expensiveness;

        isAvailable = true;
        setSize(width, height);
        setPosition(startTile.getX(), startTile.getY());
        setBounds(getX(), getY(), getWidth(), getHeight());
        currentTile = startTile;
        lastDestination = startTile;
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

        map = tileMap;

        manager = ChaosCompany.manager;
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

    /**
     * Employees can be hired
     */
    public abstract void hire();


    /**
     * Employees can be fired
     */
    public void fire(){
        if(!isAvailable) {
            currentTile.setIsFull(false);
        }
        remove();
        setHired(false);
        manager.setEmployees(manager.getEmployees()-1);
        manager.setSalaries(manager.getSalaries() + (int)salary);

        if(this.getClass() == Programmer.class){
            manager.setProgrammingPower(manager.getProgrammingPower() - (int)(skill*100));
        }else if(this.getClass() == Artist.class){
            manager.setWellBeing(manager.getWellBeing()-(int)(skill*250));
        }else{
            manager.setMarketingPower(manager.getMarketingPower()-(int)(skill*80));
        }

    }

    /**
     * Employees move on their given path. Everytime the employee get's to a target tile on its path
     * the target tile is changed until the employee reaches its destination. The animations are also
     * changed here according to the direction that the employee is moving.
     */
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
            currentTile = path.get(currentTileIndex);
            currentTileIndex++;
            targetPosition = new Vector2(path.get(currentTileIndex).getX(), path.get(currentTileIndex).getY());
        }
        else {
            currentTileIndex = 0;
            targetPosition = null;
            currentTile = path.get(path.size() - 1);
            setPosition(currentTile.getX(), currentTile.getY());
            path = null;
            walking = false;
        }
    }

    /**
     * Gives the employee a new destination where to walk.
     * @param targetTile The tile that the employee wants to reach.
     */
    public void giveDestination(Tile targetTile){

        setLastDestination(targetTile);
        if (walking) {
            targetPosition = null;
            if(path != null && path.size() > 0)
                currentTile = path.get(currentTileIndex);
            currentTileIndex = 0;
        }

        path = pathfinding.Path(currentTile, targetTile);

        if(path != null && path.size() > 0) {
            walking = true;
            targetPosition = new Vector2(path.get(0).getX(), path.get(0).getY());
        }

    }

    /**
     * Listener for the employee that opens a menu if the employee is tapped.
     */
    private class EmployeeListener extends InputListener{

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            if(Employee.this.menu == null){
                if(hired) {
                    //Find all employees of officeState and set their menu as null
                    for (int i = 0;
                         i < ChaosCompany.officeState.getobjectStage().getActors().size; i++) {
                            if(ChaosCompany.officeState.getobjectStage().getActors().get(i).getClass() == Programmer.class ||
                               ChaosCompany.officeState.getobjectStage().getActors().get(i).getClass() == Artist.class ||
                               ChaosCompany.officeState.getobjectStage().getActors().get(i).getClass() == MarketingExecutive.class){
                                    Employee temp = (Employee)ChaosCompany.officeState.getobjectStage().getActors().get(i);
                                    if(temp.menu != null) {
                                        temp.menu.hideMenu();
                                        temp.menu = null;
                                    }
                            }
                    }
                    //If development menu is not open, add Employee menu
                    if(ChaosCompany.officeState.getUI().getDevelopMenu() == null){
                        Employee.this.menu = new EmpMenu(Employee.this, ChaosCompany.officeState.getStage(),
                                ChaosCompany.officeState.getTextStage(), 0.2f, 1);
                    }else if(ChaosCompany.officeState.getUI().getDevelopMenu().getVisible() == false) {
                        Employee.this.menu = new EmpMenu(Employee.this, ChaosCompany.officeState.getStage(),
                                ChaosCompany.officeState.getTextStage(), 0.2f, 1);
                    }
                }else{

                    //Find all employees of hireState and set their menu as null
                    for (int i = 0;
                         i < ChaosCompany.hireState.getobjectStage().getActors().size; i++) {
                        if(ChaosCompany.hireState.getobjectStage().getActors().get(i).getClass() == Programmer.class ||
                                ChaosCompany.hireState.getobjectStage().getActors().get(i).getClass() == Artist.class ||
                                ChaosCompany.hireState.getobjectStage().getActors().get(i).getClass() == MarketingExecutive.class){
                            Employee temp = (Employee)ChaosCompany.hireState.getobjectStage().getActors().get(i);
                            if(temp.menu != null) {
                                temp.menu.hideMenu();
                                temp.menu = null;
                            }
                        }
                    }

                    Employee.this.menu = new EmpMenu(Employee.this, ChaosCompany.hireState.getStage(),
                            ChaosCompany.hireState.getTextStage(), 0.2f, 1);
                }
            }
        }
    }

    /**
     * Method that transforms one row of the 2D TextureRegion Array into 1D TextureRegion array
     * @param tmp 2D-array texture region that we want to make into a 1D-array.
     * @param row how many rows the texture has.
     * @return Animation frames
     */
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

    public Tile getLastDestination() {
        return lastDestination;
    }

    public void setLastDestination(Tile lastDestination) {
        this.lastDestination = lastDestination;
    }

    /**
     * Looks for a random tile to move on.
     */
    public void findRandomPlace(){
        int x = 0;
        int y = 0;

        while(map.getTiles()[x][y].getIsFull()){
            x = MathUtils.random(0, map.getTiles().length - 1);
            y = MathUtils.random(0, map.getTiles()[0].length - 1);
        }

        giveDestination(map.getTiles()[x][y]);
        map.getTiles()[x][y].setIsFull(true);
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

    public StatsManager getManager(){
        return manager;
    }

    public String getProfession(){ return profession; }

    public float getSkill(){ return skill; }

    public void setSkill(float skill) {
        this.skill = skill;
    }

    public int getSalary() { return (int) salary; }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public EmpMenu getMenu() {
        return menu;
    }
}