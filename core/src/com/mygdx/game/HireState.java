package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.UI.*;
import com.mygdx.employees.Artist;
import com.mygdx.employees.Employee;
import com.mygdx.employees.MarketingExecutive;
import com.mygdx.employees.Programmer;
import com.mygdx.furniture.Computer;
import com.mygdx.map.TileMap;

import java.util.Comparator;

/**
 * Created by SamiH on 24.2.2017.
 */

public class HireState implements GestureDetector.GestureListener, Screen {
    private ChaosCompany                game;
    private SpriteBatch                 spriteBatch;

    //Camera settings
    private float                       cameraDistance = 1f;
    private float                       zoomSpeed = 0.0001f;
    private float                       panSpeed = 0.01f;
    private float                       maxCameraDistance = 3f;
    private float                       minCameraDistance = 0.7f;
    private OrthographicCamera          uiCam;
    private OrthographicCamera          cam;
    private OrthographicCamera          textCam;
    private Vector3                     cameraPosition;

    //Textures and buttons
    private TextButton                  mapBtn;
    private BitmapFont                  font;

    private com.mygdx.UI.MoneyUi        moneyUI;
    private EmpSlotUI                   empUI;
    private IncomeUI                    incUI;

    private BitmapFont                  font2;

    //Manager
    private StatsManager                manager;

    private Matrix4      		    	isoTransform = null;
    private Matrix4		    	    	invIsotransform = null;
    private Matrix4			        	id = null;

    private int[][]				        map = null;
    private TileMap                     tileMap = null;

    private Stage                       stage;
    private Stage                       objectStage;
    private Stage                       movingUiStage;
    private Stage                       textStage;
    private Stage                       movingTextStage;

    private GestureDetector             input;

    private InputMultiplexer            multiplexer;

    //First employee player gets
    private Programmer                  firstEmp;

    //Level of employees spawning to hireState
    private int                         level = 1;

    //Bundle
    private I18NBundle bundle = ChaosCompany.myBundle;

    //List of Employees you can hire
    Employee[] employees;

    public HireState(ChaosCompany g){
        game = g;

        stage = new Stage();
        objectStage = new Stage();
        movingUiStage = new Stage();
        textStage = new Stage();
        movingTextStage = new Stage();

        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glEnable(GL20.GL_TEXTURE_2D);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        spriteBatch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 8, 4.8f);
        cameraPosition = new Vector3(5,0,0);

        uiCam = new OrthographicCamera();
        textCam = new OrthographicCamera();
        textCam.setToOrtho(false, 800, 480);

        spriteBatch.setProjectionMatrix(cam.combined);


        map = new int[][]{
                {0, 1, 2, 2, 2, 2, 2, 2, 2, 8},
                {1, 4, 4, 4, 4, 4, 4, 4, 3, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 9, 6},
                {5, 5, 5, 5, 5, 5, 5, 5, 6, 0}
        };

        tileMap = new TileMap(map, spriteBatch);


        id = new Matrix4();
        id.idt();

        //create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();
        isoTransform.translate(0.0f, 0.25f, 0.0f);
        isoTransform.scale((float)(Math.sqrt(2.0) / 2.0), (float)(Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45.0f);

        //... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();

        //Stats manager
        manager = game.getManager();

        mapBtn = new GameButton(game, stage, 0.1f, 4.9f, "", new Texture("mapBtn.png")).getButton();
        mapBtn.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 100 && y > 0 && y < 100){
                    game.setScreen(game.mapState);
                }
            }
        });
        stage.addActor(mapBtn);

        input = new GestureDetector(this);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(input);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(movingUiStage);
        multiplexer.addProcessor(objectStage);

        font = new BitmapFont();
        font.setColor(Color.BLACK);

        font2 = new BitmapFont();
        font2.setColor(Color.BLACK);
        font2.getData().setScale(2, 2);

        moneyUI = new com.mygdx.UI.MoneyUi();
        empUI = new EmpSlotUI();
        incUI = new IncomeUI();

        stage.addActor(moneyUI);
        stage.addActor(empUI);
        stage.addActor(incUI);

        //Create hirable employees
        employees = new Employee[5];
        createEmployees();

        //Setup players first employee
        firstEmp = new Programmer(tileMap, tileMap.getTiles()[1][1], 1f, 1f, 1);
        firstEmp.setSalary(0);
        objectStage.addActor(firstEmp);

        firstEmp.hire();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);	//register this class as input processor
        stage.getViewport().setCamera(uiCam);
        objectStage.getViewport().setCamera(cam);
        movingUiStage.getViewport().setCamera(cam);
        textStage.getViewport().setCamera(textCam);
        movingTextStage.getViewport().setCamera(cam);
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(cam.combined);
        stage.act(delta);
        objectStage.act(delta);
        movingUiStage.act(delta);

        spriteBatch.setTransformMatrix(id);
        spriteBatch.begin();
        tileMap.renderMap();

        spriteBatch.end();

        objectStage.draw();
        movingUiStage.draw();
        movingTextStage.draw();
        stage.draw();
        textStage.draw();

        //Render texts
        drawMoneyText(spriteBatch);
        empSlotText(spriteBatch);
        drawIncome(spriteBatch);

        spriteBatch.setTransformMatrix(isoTransform);
        updateDrawingOrder();

        cam.update();
    }

    public void updateDrawingOrder(){

        //get all actors in the objectStage
        Array<Actor> actorsList = objectStage.getActors();
        actorsList.sort(new ActorComparator());
    }

    @Override
    public void resize(int width, int height) {

        //the cam will show 10 tiles
        float camWidth = tileMap.tileWidth * 10.0f * cameraDistance;

        //for the height, we just maintain the aspect ratio
        float camHeight = camWidth * ((float)height / (float)width);

        cam.setToOrtho(false, camWidth, camHeight);
        uiCam.setToOrtho(false, 10,6);
        cam.position.set(cameraPosition);

        uiCam.update();
        cam.update();
    }

    public void createEmployees(){

        if(level < 5)
            level++;


        for (int i = 0; i < employees.length; i++) {
            if(employees[i] != null){
                employees[i].getCurrentTile().setIsFull(false);
                if(!employees[i].getHired()) {
                    employees[i].remove();
                }
                employees[i] = null;
            }
        }


        int x = 9;
        int y = 9;
        while(tileMap.getTiles()[x][y].getIsFull()) {
            x = MathUtils.random(0, 7);
            y = MathUtils.random(0, 7);
        }
        employees[3] = new Programmer(tileMap, tileMap.getTiles()[x][y], 1f, 1f, MathUtils.random(1f, level));
        objectStage.addActor(employees[3]);
        tileMap.getTiles()[x][y].setIsFull(true);

        x = 9;
        y = 9;
        while(tileMap.getTiles()[x][y].getIsFull()) {
            x = MathUtils.random(0, 7);
            y = MathUtils.random(0, 7);
        }
        employees[4] = new Artist(tileMap, tileMap.getTiles()[x][y], 1f, 1f, MathUtils.random(1f, level));
        objectStage.addActor(employees[4]);
        tileMap.getTiles()[x][y].setIsFull(true);

        x = 9;
        y = 9;
        while(tileMap.getTiles()[x][y].getIsFull()) {
            x = MathUtils.random(0, 7);
            y = MathUtils.random(0, 7);
        }
        employees[2] = new MarketingExecutive(tileMap, tileMap.getTiles()[x][y], 1f, 1f, MathUtils.random(1f, level));
        objectStage.addActor(employees[2]);
        tileMap.getTiles()[x][y].setIsFull(true);

        for (int i = 0; i < 2; i++) {
            int xx = 9;
            int yy = 9;
            while(tileMap.getTiles()[xx][yy].getIsFull()) {
                xx = MathUtils.random(0, 7);
                yy = MathUtils.random(0, 7);
            }
            int random = MathUtils.random(1, 3);

            switch (random){
                case 1: employees[i] = new Programmer(tileMap, tileMap.getTiles()[xx][yy], 1f, 1f, MathUtils.random(1f, level));
                        objectStage.addActor(employees[i]);
                        break;
                case 2: employees[i] = new Artist(tileMap, tileMap.getTiles()[xx][yy], 1f, 1f, MathUtils.random(1f, level));
                        objectStage.addActor(employees[i]);
                        break;
                case 3: employees[i] = new MarketingExecutive(tileMap, tileMap.getTiles()[xx][yy], 1f, 1f, MathUtils.random(1f, level));
                        objectStage.addActor(employees[i]);
                        break;
            }
            objectStage.addActor(employees[i]);
            tileMap.getTiles()[xx][yy].setIsFull(true);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        cameraPosition.x -= deltaX * panSpeed;
        cameraPosition.y += deltaY * panSpeed;
        resize(800, 480);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        //calculates the zoom amount
        cameraDistance += (initialDistance - distance) * zoomSpeed;

        //Makes sure the user can't zoom too far or too close
        if(cameraDistance < minCameraDistance)
            cameraDistance = minCameraDistance;
        else if(cameraDistance > maxCameraDistance)
            cameraDistance = maxCameraDistance;

        //camera needs to be set again
        resize(800, 480);

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public void drawMoneyText(SpriteBatch batch){
        batch.setProjectionMatrix(textCam.combined);
        batch.begin();
        font.draw(batch,""+manager.getMoney(),710, 447);
        batch.end();
    }

    public void empSlotText(SpriteBatch batch){
        batch.setProjectionMatrix(textCam.combined);
        String text = ""+manager.getEmployees()+"/"+manager.getEmployeeSlots();
        batch.begin();
        font2.draw(batch,text, 730, 370);
        batch.end();
    }

    public void drawIncome(SpriteBatch batch){
        batch.setProjectionMatrix(textCam.combined);
        String text = bundle.get("income")+"\n"+manager.getIncome();
        if(manager.getIncome() < 0){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.FOREST);
        }
        batch.begin();
        font.draw(batch,text, 710, 420);
        batch.end();
        font.setColor(Color.BLACK);
    }

    class ActorComparator implements Comparator<Actor> {
        @Override
        //compares the Y-position of the furniture
        public int compare(Actor arg0, Actor arg1) {
            if (arg0.getY() < arg1.getY()) {
                return 1;
            } else if (arg0.getY() > arg1.getY()) {
                return -1;
            } else{
                return 0;
            }
        }
    }

    public OrthographicCamera getCam() {
        return cam;
    }
    public Stage getMovingUiStage(){
        return movingUiStage;
    }
    public Stage getTextStage(){ return textStage; }
    public Stage getStage(){ return stage;}
    public Stage getMovingTextStage(){ return movingTextStage; }
    public Stage getobjectStage() { return objectStage; }
}
