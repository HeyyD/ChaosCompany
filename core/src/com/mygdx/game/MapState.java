package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

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
    private StatsManager manager;

    private ImageButton company;
    private ImageButton jobCenter;
    private ImageButton.ImageButtonStyle style;

    private Skin skin;
    private Stage stage;

    //some variables that check which effects have taken place
    private Texture statue = null;
    private Texture trees;
    private Texture trash;
    private Texture right;

    private ArrayList<Texture> statueBadEffects = new ArrayList<Texture>();
    private ArrayList<Texture> rightBadEffects = new ArrayList<Texture>();
    private ArrayList<Texture> drawables = new ArrayList<Texture>();

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
        trash = new Texture("MapState/Trash1.png");
        right = new Texture("MapState/Right1.png");

        statueBadEffects.add(new Texture("MapState/Center1.png"));
        statueBadEffects.add(new Texture("MapState/Center4.png"));

        rightBadEffects.add(new Texture("MapState/Right3.png"));

        drawables.add(trees);
        drawables.add(trash);
        drawables.add(right);
    }

    public void updateMap(){
        int karma = manager.getKarma();

        if(karma > 0){

            if(statue != null)
                statue = new Texture("MapState/Center2.png");

            if(karma >= 35){
                trees = new Texture("MapState/GoodTrees6.png");
            } else if(karma >= 28){
                trees = new Texture("MapState/GoodTrees5.png");
                statue = new Texture("MapState/Center3.png");
            } else if(karma >= 21){
                trees = new Texture("MapState/GoodTrees4.png");
                statue = new Texture("MapState/Center2.png");
            } else if(karma >= 14){
                trees = new Texture("MapState/GoodTrees3.png");
                statue = new Texture("MapState/Center2.png");
            } else if(karma >= 7){
                trees = new Texture("MapState/GoodTrees2.png");
            } else{
                trees = new Texture("MapState/GoodTrees1.png");
                trash = new Texture("MapState/Trash1.png");
            }
        }

        else if(karma < 0){
            if(karma <= -35){
                trees = new Texture("MapState/BadTrees6.png");
            } else if(karma <= -28){
                trees = new Texture("MapState/BadTrees5.png");
            } else if(karma <= -21){
                trees = new Texture("MapState/BadTrees4.png");
            } else if(karma <= -14){
                trees = new Texture("MapState/BadTrees3.png");
                if(statue != null)
                    statue = statueBadEffects.get(MathUtils.random(0, statueBadEffects.size() - 1));
            } else if(karma <= -7){
                trees = new Texture("MapState/BadTrees2.png");
                trash = new Texture("MapState/Trash2.png");
                right = rightBadEffects.get(MathUtils.random(0, rightBadEffects.size() - 1));
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
            if(statue != null)
                batch.draw(statue,0,0);
            batch.draw(trees, 0, 0);
            batch.draw(trash,0, 0);
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
        trees.dispose();
        if(statue != null)
            statue.dispose();
        trash.dispose();
        stage.dispose();
    }
}