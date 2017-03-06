package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.furniture.Couch;

public class OfficeState implements InputProcessor, Screen{

    private ChaosCompany        game;
    private StatsManager        manager;
    private Matrix4 			isoTransform = null;
    private Matrix4				invIsotransform = null;
    private Matrix4				id = null;
    private SpriteBatch			spriteBatch = null;
    private int[][]				map = null;
    private Texture				textureTileset = null;
    private TextureRegion[]		tileSet = null;
    private OrthographicCamera	cam = null;
    private float				tileWidth = 1.0f;
    private float				tileHeight = .5f;
    private Vector3				touch = null;

    private int 				pickedTileX = -1, pickedTileY = -1;

    //menu
    private Stage               stage;
    private Stage               funitureStage;
    private BuildMenu           buildMenu = null;

    //TEST
    private Couch               couch;

    public OfficeState(ChaosCompany g) {

        game = g;

        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glEnable(GL20.GL_TEXTURE_2D);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera();
        stage = new Stage();
        funitureStage = new Stage();

        //load the tileset
        textureTileset = new Texture("tileset.png");
        textureTileset.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        tileSet = new TextureRegion[4];
        for(int x=0;x<4;x++){
            tileSet[x] = new TextureRegion(textureTileset, x*64, 0, 64, 32);
        }

        //create a 10x10 isometric map
        map = new int[][]{
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0 ,0, 0, 0, 0, 0, 0, 0}
        };

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

        //TEST
        couch = new Couch(game, 0f, 0f);
        funitureStage.addActor(couch);

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touch.set(screenX, screenY, 0);
        cam.unproject(touch);
        touch.mul(invIsotransform);
        pickedTileX = (int) touch.x;
        pickedTileY = (int) touch.y;
        return false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);	//register this class as input processor
        stage.getViewport().setCamera(cam);
        funitureStage.getViewport().setCamera(cam);
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(cam.combined);
        stage.act(delta);
        funitureStage.act(delta);

        spriteBatch.setTransformMatrix(id);
        spriteBatch.begin();

        renderMap();
        renderBuildMenu();


        spriteBatch.end();
        stage.draw();
        funitureStage.draw();
        spriteBatch.setTransformMatrix(isoTransform);

        cam.update();

    }

    @Override
    public void resize(int width, int height) {

        //the cam will show 10 tiles
        float camWidth = tileWidth * 10.0f;

        //for the height, we just maintain the aspect ratio
        float camHeight = camWidth * ((float)height / (float)width);

        cam.setToOrtho(false, camWidth, camHeight);
        cam.position.set(camWidth / 2.0f, 0, 0);
    }

    private void renderBuildMenu(){

        float offset = 0.5f;
        float x_pos = (pickedTileX * tileWidth /2.0f ) + (pickedTileY * tileWidth / 2.0f) + offset;
        float y_pos = - (pickedTileX * tileHeight / 2.0f) + (pickedTileY * tileHeight /2.0f) + offset;

        if((pickedTileX >= 0 || pickedTileY >= 0) && buildMenu == null){
            buildMenu = new BuildMenu(stage, x_pos, y_pos);
        }
    }

    private void renderMap(){
        for (int x = 0; x < 10; x++){
            for(int y = 10-1; y >= 0; y--){

                float x_pos = (x * tileWidth /2.0f ) + (y * tileWidth / 2.0f);
                float y_pos = - (x * tileHeight / 2.0f) + (y * tileHeight /2.0f);

                if(x==pickedTileX && y==pickedTileY)
                    spriteBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
                else
                    spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

                spriteBatch.draw(tileSet[map[x][y]], x_pos, y_pos, tileWidth, tileHeight);
            }
        }
    }

    public void resetBuildMenu(){
        pickedTileY = -1;
        pickedTileX = -1;
        stage.clear();
        buildMenu = null;
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
        funitureStage.dispose();
    }

    public Stage getFunitureStage(){
        return funitureStage;
    }

}
