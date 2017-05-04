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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.UI.*;
import com.mygdx.development.Game;
import com.mygdx.map.TileMap;

import java.util.ArrayList;
import java.util.Comparator;

public class OfficeState implements GestureDetector.GestureListener, Screen{

    private ChaosCompany        game;
    private StatsManager        manager;
    private OfficeStateUI       UI;
    private Matrix4 			isoTransform = null;
    private Matrix4				invIsotransform = null;
    private Matrix4				id = null;
    private SpriteBatch			spriteBatch = null;
    private int[][]				map = null;

    //CAMERA
    private float               cameraDistance = 1f;
    private float               zoomSpeed = 0.0001f;
    private float               panSpeed = 0.01f;
    private float               maxCameraDistance = 3f;
    private float               minCameraDistance = 0.7f;
    private Vector3             cameraPosition = null;
    private OrthographicCamera	cam = null;
    private OrthographicCamera  uiCam = null;
    private OrthographicCamera  textCam = null;


    private Vector3				touch = null;

    //Stage for menu buttons that stays still
    private Stage               stage;
    //Stage  for objects like furniture and employees
    private Stage               objectStage;
    //Stage for things that has to move with camera
    private Stage               movingUiStage;
    //Stage for labels etc.
    private Stage               textStage;

    private BuildMenu           buildMenu = null;
    private TextButton          buildMenuBtn = null;
    private TextButton          mapBtn = null;

    private Label               label = null;
    private Skin                labelskin = null;

    private boolean             isMoving = false;
    private boolean             isBuildMenuOpen = false;

    private TileMap             tileMap = null;

    //Input
    protected GestureDetector     input = null;
    private InputMultiplexer      multiplexer = null;

    //Font for Money UI
    private BitmapFont            font = null;

    //Font for Emp Slots
    private BitmapFont            font2 = null;


    private com.mygdx.UI.MoneyUi moneyUI = null;
    private EmpSlotUI             empUI = null;

    //Developed games
    public ArrayList<Game> games = new ArrayList<Game>();
    private ArrayList<Game> deleteGames = new ArrayList<Game>();
    private boolean developing;

    public OfficeState(ChaosCompany g) {

        game = g;
        developing = false;

        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glEnable(GL20.GL_TEXTURE_2D);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch = new SpriteBatch();

        cam = new OrthographicCamera();
        cameraPosition = new Vector3(5,0,0);
        uiCam = new OrthographicCamera();
        textCam = new OrthographicCamera();
        textCam.setToOrtho(false, 800, 480);


        stage = new Stage();
        objectStage = new Stage();
        movingUiStage = new Stage();
        textStage = new Stage();

        map = new int[][]{
                {0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 8},
                {1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 7},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 6},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 0}
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

        //touch vector
        touch = new Vector3();

        //Stats manager
        manager = game.getManager();

        //Create BuildMenu button
        buildMenuBtn = new GameButton(game, stage, 8.9f, 0.1f,"", new Texture("buildBtn.png")).getButton();
        buildMenuBtn.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 100 && y > 0 && y < 100 && isBuildMenuOpen == false){
                    new BuildMenu(game, 6.2f,1.1f);
                }
            }
        });

        //Create map button
        mapBtn = new GameButton(game, stage, 0.1f, 4.9f,"", new Texture("mapBtn.png")).getButton();
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

        input = new GestureDetector(this);

        //Setup multiplexer
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(movingUiStage);
        multiplexer.addProcessor(objectStage);
        multiplexer.addProcessor(input);

        //Setup fonts
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        font2 = new BitmapFont();
        font2.setColor(Color.BLACK);
        font2.getData().setScale(2,2);

        //Add money and employeeslot UI
        moneyUI = new com.mygdx.UI.MoneyUi();
        empUI = new EmpSlotUI();

        stage.addActor(moneyUI);
        stage.addActor(empUI);

        UI = new OfficeStateUI(stage, textStage, game);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);	//register this class as input processor
        stage.getViewport().setCamera(uiCam);
        objectStage.getViewport().setCamera(cam);
        movingUiStage.getViewport().setCamera(cam);
        textStage.getViewport().setCamera(textCam);
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update games
        for(Game game: games){
            game.update();
            if(game.moneyCycles <= 0)
                deleteGames.add(game);
        }

        for(Game game: deleteGames){
            games.remove(game);
        }

        deleteGames.clear();

        spriteBatch.setProjectionMatrix(cam.combined);
        stage.act(delta);
        objectStage.act(delta);
        movingUiStage.act(delta);
        textStage.act(delta);

        spriteBatch.setTransformMatrix(id);
        spriteBatch.begin();
        tileMap.renderMap();

        spriteBatch.end();

        objectStage.draw();
        movingUiStage.draw();
        stage.draw();
        textStage.draw();

        drawMoneyText(spriteBatch);
        empSlotText(spriteBatch);
        spriteBatch.setTransformMatrix(isoTransform);
        updateDrawingOrder();

        cam.update();
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

    public void updateDrawingOrder(){

        //get all actors in the objectStage
        Array<Actor> actorsList = objectStage.getActors();
        actorsList.sort(new ActorComparator());
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
        GL20 gl = Gdx.graphics.getGL20();
        gl.glDisable(GL20.GL_BLEND);
        gl.glDisable(GL20.GL_TEXTURE_2D);
        stage.dispose();
        objectStage.dispose();
        movingUiStage.dispose();
        textStage.dispose();
    }


    public Stage getobjectStage(){
        return objectStage;
    }
    public Stage getMovingUiStage() {
        return movingUiStage;
    }
    public Stage getTextStage(){return textStage;}
    public boolean getIsMoving(){ return isMoving; }
    public void setIsMoving(boolean isMoving){this.isMoving = isMoving;}
    public boolean getIsBuildMenuOpen(){ return isBuildMenuOpen; }
    public void setIsBuildMenuOpen(boolean isBuildMenuOpen){this.isBuildMenuOpen = isBuildMenuOpen;}

    public Stage getStage(){
        return stage;
    }
    public TileMap getTileMap(){
        return tileMap;
    }
    public OrthographicCamera getCam() {
        return cam;
    }
    public Matrix4 getInvIsotransform(){
        return invIsotransform;
    }
    public SpriteBatch getSpriteBatch(){ return spriteBatch; }
    public void setDeveloping(boolean developing){
        this.developing = developing;
    }
    public boolean getDeveloping(){
        return developing;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        touch.set(x, y, 0);
        cam.unproject(touch);
        touch.mul(invIsotransform);
        tileMap.pickedTileX = (int) touch.x;
        tileMap.pickedTileY = (int) touch.y;

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
}