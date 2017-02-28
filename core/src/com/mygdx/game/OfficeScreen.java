package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OfficeScreen implements Screen, InputProcessor {

    private Texture         textureTileset;
    private TextureRegion[]	tileSet;
    private int             tilesetSize = 4;
    private float           tileWidth = 64;
    private float           tileHeight = 32;

    public OfficeScreen(Game game){
        //create tileset
        textureTileset = new Texture("tileset.png");
        textureTileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        tileSet = new TextureRegion[4];

        for(int i = 0; i < tileSet.length; i++)
            tileSet[i] = new TextureRegion(textureTileset, i * tileWidth, 0, tileWidth, tileHeight);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
        textureTileset.dispose();
    }


    //InputProcessor methods
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
}
