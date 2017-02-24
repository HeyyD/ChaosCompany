package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChaosCompany extends Game {
	private SpriteBatch batch;
    private MainMenuState mainMenu;

    public SpriteBatch getSpriteBatch(){
        return batch;
    }

	@Override
	public void create () {
        batch = new SpriteBatch();
        mainMenu = new MainMenuState(this);
        setScreen(mainMenu);
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
