package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by SamiH on 24.2.2017.
 */

public class HireState implements Screen {
    private ChaosCompany game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture img;
    private Texture mapBtnUp;
    private Texture mapBtnDown;
    private ImageButton mapBtn;

    private Stage stage;


    public HireState(ChaosCompany g){
        game = g;
        stage = new Stage();

        batch = game.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 4.8f);
        batch.setProjectionMatrix(camera.combined);
        stage.getViewport().setCamera(camera);

        img = new Texture("tyokkariState.png");

        mapBtnUp = new Texture("UI_BuildMarketingBtn.png");
        mapBtnDown = new Texture("UI_BuildProgrammingBtn.png");
        mapBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(mapBtnUp)),
                new TextureRegionDrawable(new TextureRegion(mapBtnDown)));
        mapBtn.setPosition(0.002f,4f);
        mapBtn.setSize(mapBtn.getWidth()/100, mapBtn.getHeight()/100);

        mapBtn.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 64 && y > 0 && y < 64){
                    game.setScreen(game.mapState);
                }
            }
        });
        stage.addActor(mapBtn);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        batch.begin();
        batch.draw(img, 0,0, 8f, 4.8f);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
}
