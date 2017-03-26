package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChaosCompany extends Game {

    //Screens
    public static MainMenuState   mainMenuState;
    public static OfficeState     officeState;
    public static MapState        mapState;
    public static HireState       hireState;

    //Manager
    public static StatsManager    manager;

	protected SpriteBatch   batch;

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

	@Override
	public void create () {

        //Stats Manager
        manager                 = new StatsManager();

        //Screens
        batch                   = new SpriteBatch();
        mainMenuState           = new MainMenuState(this);
        officeState             = new OfficeState(this);
        mapState                = new MapState(this);
        hireState               = new HireState(this);

        setScreen(mainMenuState);
    }

    @Override
	public void render () {
        super.render();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && getScreen() == mainMenuState ){
            setScreen(officeState);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            setScreen(mainMenuState);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.V) && getScreen() == officeState){
            setScreen(mapState);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.B) && getScreen() == mapState){
            setScreen(hireState);
        }
    }
    @Override
    public void dispose () {
        batch.dispose();
    }

    public OfficeState getOfficeState(){
        return this.officeState;
    }

    public HireState getHireState(){
        return hireState;
    }


    public StatsManager getManager(){
        return manager;
    }
}
