package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by SamiH on 24.2.2017.
 */

public class MapState implements Screen {
    private ChaosCompany game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture img;

    public MapState(ChaosCompany g){
        game = g;
        batch = game.getSpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 4.8f);
        batch.setProjectionMatrix(camera.combined);
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 2,1,1,1);
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

    }
}