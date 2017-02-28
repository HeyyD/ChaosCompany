package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ChaosCompany extends Game {

    public Screen officeScreen;

	@Override
	public void create () {
        officeScreen = new OfficeScreen(this);
        this.setScreen(officeScreen);
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {

	}
}
