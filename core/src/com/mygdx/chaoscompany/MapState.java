package com.mygdx.chaoscompany;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

/**
 * MapState in game
 * Created by SamiH on 24.2.2017.
 */

public class MapState implements Screen {
    /**
     * Game
     */
    private com.mygdx.chaoscompany.ChaosCompany game;
    /**
     * batch to draw textures
     */
    private SpriteBatch batch;
    /**
     * Camera of mapState
     */
    private OrthographicCamera camera;
    /**
     * Maps texture
     */
    private Texture map;
    /**
     * ChaosCompany buildings texture when not pushed
     */
    private Texture companyUP;

    /**
     * ChaosCompany buildings texture when pushed down
     */
    private Texture companyDOWN;
    /**
     * JobCenter buildings texture when not pushed
     */
    private Texture jobCenterUP;
    /**
     * JobCenter buildings texture when pushed down
     */
    private Texture jobCenterDOWN;
    /**
     * Manager of the game
     */
    private StatsManager manager;

    /**
     * Button for ChaosCompany
     */
    private ImageButton company;
    /**
     * Button for hireState
     */
    private ImageButton jobCenter;
    /**
     * ImageButtonStyle for buttons
     */
    private ImageButton.ImageButtonStyle style;

    /**
     * Skin for buttons
     */
    private Skin skin;
    /**
     * Stage
     */
    private Stage stage;

    //some variables that check which effects have taken place
    /**
     * Contains statue that is at map atm.
     */
    private Texture statue = null;
    /**
     * Trees in map atm.
     */
    private Texture trees;
    /**
     * Right content of map atm.
     */
    private Texture right;
    /**
     * Left content of map atm.
     */
    private Texture left;
    /**
     * Top content of map atm.
     */
    private Texture top;

    /**
     * All bad effects of statue
     */
    private ArrayList<Texture> statueBadEffects = new ArrayList<Texture>();
    /**
     * Good effects of right area
     */
    private ArrayList<Texture> rightGoodEffects = new ArrayList<Texture>();
    /**
     * bad effects of right area
     */
    private ArrayList<Texture> rightBadEffects = new ArrayList<Texture>();
    /**
     * good effects of left area
     */
    private ArrayList<Texture> leftGoodEffects = new ArrayList<Texture>();
    /**
     * bad effects of left area
     */
    private ArrayList<Texture> leftBadEffects = new ArrayList<Texture>();
    /**
     * good effects of top area
     */
    private ArrayList<Texture> topGoodEffects = new ArrayList<Texture>();
    /**
     * bad effects of top area
     */
    private ArrayList<Texture> topBadEffects = new ArrayList<Texture>();

    private Texture neutralTrees;

    private Texture badTrees1;
    private Texture badTrees2;
    private Texture badTrees3;
    private Texture badTrees4;
    private Texture badTrees5;
    private Texture badTrees6;

    private Texture goodTrees1;
    private Texture goodTrees2;
    private Texture goodTrees3;
    private Texture goodTrees4;
    private Texture goodTrees5;
    private Texture goodTrees6;

    private Texture centerGood;
    private Texture centerNeutral;

    private boolean neutralMap = true;


    public MapState(com.mygdx.chaoscompany.ChaosCompany g){
        game = g;
        batch = game.getSpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480f);
        batch.setProjectionMatrix(camera.combined);
        manager = com.mygdx.chaoscompany.ChaosCompany.manager;

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

        neutralTrees = new Texture("MapState/Trees.png");

        centerNeutral = new Texture("MapState/Center2.png");
        centerGood = new Texture("MapState/Center3.png");

        statue = new Texture("MapState/Center2.png");
        trees = neutralTrees;
        right = new Texture("MapState/Right1.png");
        left = new Texture("MapState/Left4.png");
        top = new Texture("MapState/Top2.png");

        badTrees1 = new Texture("MapState/Badtrees1.png");
        badTrees2 = new Texture("MapState/BadTrees2.png");
        badTrees3 = new Texture("MapState/BadTrees3.png");
        badTrees4 = new Texture("MapState/BadTrees4.png");
        badTrees5 = new Texture("MapState/BadTrees5.png");
        badTrees6 = new Texture("MapState/BadTrees6.png");

        goodTrees1 = new Texture("MapState/GoodTrees1.png");
        goodTrees2 = new Texture("MapState/GoodTrees2.png");
        goodTrees3 = new Texture("MapState/GoodTrees3.png");
        goodTrees4 = new Texture("MapState/GoodTrees4.png");
        goodTrees5 = new Texture("MapState/GoodTrees5.png");
        goodTrees6 = new Texture("MapState/GoodTrees6.png");

        statueBadEffects.add(new Texture("MapState/Center1.png"));
        statueBadEffects.add(new Texture("MapState/Center4.png"));

        rightGoodEffects.add(right);
        rightBadEffects.add(new Texture("MapState/Right3.png"));

        leftGoodEffects.add(left);
        leftGoodEffects.add(new Texture("MapState/Left2.png"));
        leftGoodEffects.add(new Texture("MapState/Left3.png"));

        leftBadEffects.add(new Texture("MapState/Left1.png"));

        topGoodEffects.add(top);

        topBadEffects.add(new Texture("MapState/Top1.png"));
        topBadEffects.add(new Texture("MapState/Top3.png"));
        topBadEffects.add(new Texture("MapState/Top4.png"));

    }

    /**
     * Called when map is updated
     */
    public void updateMap(){
        int karma = manager.getKarma();

        if(karma > 0){

            left = leftGoodEffects.get(MathUtils.random(0, leftGoodEffects.size() - 1));
            right = rightGoodEffects.get(MathUtils.random(0, rightGoodEffects.size() - 1));
            top = topGoodEffects.get(MathUtils.random(0, topGoodEffects.size() - 1));

            if(karma >= 100){
                trees = goodTrees6;
            } else if(karma >= 80){
                trees = goodTrees5;
            } else if(karma >= 60){
                trees = goodTrees4;
            } else if(karma >= 40){
                trees = goodTrees3;
                statue = centerGood;
            } else if(karma >= 20){
                trees = goodTrees2;
            } else{

                if(neutralMap == false){
                    neutralMap = true;
                    ChaosCompany.soundManager.setBackgroundMusic(ChaosCompany.soundManager.neutralMusic);
                }

                trees = goodTrees1;
                statue = centerNeutral;
            }
        }

        else if(karma < 0){

            right = rightBadEffects.get(MathUtils.random(0, rightBadEffects.size() - 1));
            left = leftBadEffects.get(MathUtils.random(0, leftBadEffects.size() - 1));
            top = topBadEffects.get(MathUtils.random(0, topBadEffects.size() - 1));

            if(karma <= -100){
                trees = badTrees6;
            } else if(karma <= -80){
                trees = badTrees5;
            } else if(karma <= -60){
                trees = badTrees4;
            } else if(karma <= -40){
                trees = badTrees3;
                statue = statueBadEffects.get(MathUtils.random(0, statueBadEffects.size() - 1));
            } else if(karma <= -20){
                if(neutralMap == true){
                    neutralMap = false;
                    ChaosCompany.soundManager.setBackgroundMusic(ChaosCompany.soundManager.sadMusic);
                }
                trees = badTrees2;
            } else{
                trees = badTrees1;
            }
        }

        else{
            trees = neutralTrees;
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
            batch.draw(statue,0,0);
            batch.draw(right,0, 0);
            batch.draw(left,0, 0);
            batch.draw(top,0, 0);
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

        //jee
        trees.dispose();
        statue.dispose();
        left.dispose();
        right.dispose();
        top.dispose();

        badTrees1.dispose();
        badTrees2.dispose();
        badTrees3.dispose();
        badTrees4.dispose();
        badTrees5.dispose();
        badTrees6.dispose();
        goodTrees1.dispose();
        goodTrees2.dispose();
        goodTrees3.dispose();
        goodTrees4.dispose();
        goodTrees5.dispose();
        goodTrees6.dispose();
        centerGood.dispose();
        centerNeutral.dispose();

        stage.dispose();
    }
}