package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChaosCompany extends Game {
	private SpriteBatch batch;
    private MainMenuState mainMenuState;
    private OfficeState officeState;

    public SpriteBatch getSpriteBatch(){
        return batch;
    }

	@Override
	public void create () {
        batch = new SpriteBatch();
        mainMenuState = new MainMenuState(this);
        officeState = new OfficeState(this);
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
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
