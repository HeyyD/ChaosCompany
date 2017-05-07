package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by SamiH on 24.2.2017.
 */

public class MapState implements Screen {
    private ChaosCompany game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture map;
    private Texture companyUP;
    private Texture companyDOWN;
    private Texture jobCenterUP;
    private Texture jobCenterDOWN;
    private Texture trees;
    private StatsManager manager;

    private ImageButton company;
    private ImageButton jobCenter;
    private ImageButton.ImageButtonStyle style;

    private Skin skin;
    private Stage stage;

    public MapState(ChaosCompany g){
        game = g;
        batch = game.getSpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480f);
        batch.setProjectionMatrix(camera.combined);
        manager = ChaosCompany.manager;

        map = new Texture("MapState/EmptyMap.png");
        map.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stage = new Stage();
        stage.getViewport().setCamera(camera);

        companyUP = new Texture("MapState/OfficeButton.png");
        companyDOWN = new Texture("MapState/OfficeButton.png");
        company = new ImageButton(new TextureRegionDrawable(new TextureRegion(companyUP)),
                new TextureRegionDrawable(new TextureRegion(companyDOWN)));

        company.setPosition(0,camera.viewportHeight - companyUP.getHeight());
        company.setSize(company.getWidth(), company.getHeight());
        company.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < company.getWidth() && y > 0 && y < company.getHeight()){
                    game.setScreen(game.getOfficeState());
                }
            }
        });
        stage.addActor(company);

        jobCenterUP = new Texture("MapState/JobCenterButton.png");
        jobCenterDOWN = new Texture("MapState/JobCenterButton.png");
        jobCenter = new ImageButton(new TextureRegionDrawable(new TextureRegion(jobCenterUP)),
                new TextureRegionDrawable(new TextureRegion(jobCenterDOWN)));
        jobCenter.setSize(jobCenter.getWidth(), jobCenter.getHeight());
        jobCenter.setPosition(camera.viewportWidth - jobCenterUP.getWidth() ,camera.viewportHeight - jobCenterUP.getHeight());

        jobCenter.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 331 && y > 0 && y < 221){
                    game.setScreen(game.getHireState());
                }
            }
        });
        stage.addActor(jobCenter);

        trees = new Texture("MapState/Trees.png");
    }

    public void updateMap(){
        int karma = manager.getKarma();

        if(karma > 0){
            if(karma >= 30){
                trees = new Texture("MapState/GoodTrees6.png");
            } else if(karma >= 25){
                trees = new Texture("MapState/GoodTrees5.png");
            } else if(karma >= 20){
                trees = new Texture("MapState/GoodTrees4.png");
            } else if(karma >= 15){
                trees = new Texture("MapState/GoodTrees3.png");
            } else if(karma >= 10){
                trees = new Texture("MapState/GoodTrees2.png");
            } else{
                trees = new Texture("MapState/GoodTrees1.png");
            }
        }

        else if(karma < 0){
            if(karma <= -30){
                trees = new Texture("MapState/BadTrees6.png");
            } else if(karma <= -25){
                trees = new Texture("MapState/BadTrees5.png");
            } else if(karma <= -20){
                trees = new Texture("MapState/BadTrees4.png");
            } else if(karma <= -15){
                trees = new Texture("MapState/BadTrees3.png");
            } else if(karma <= -10){
                trees = new Texture("MapState/BadTrees2.png");
            } else{
                trees = new Texture("MapState/BadTrees1.png");
            }
        }

        else{
            trees = new Texture("MapState/Trees.png");
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            batch.draw(map, 0, 0);
        batch.end();

        stage.act(delta);
        stage.draw();

        batch.begin();
            batch.draw(trees, 0, 0);
        batch.end();
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
        stage.dispose();
    }
}