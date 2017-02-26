package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by SamiH on 24.2.2017.
 */

public class MainMenuState implements Screen {



    private ChaosCompany game;
    private OrthographicCamera camera;
    private Texture img;
    private TextureRegion imgRegion;
    private TextureRegionDrawable imgRegionDraw;
    private ImageButton playBtn;
    private Stage stage;

    public MainMenuState(ChaosCompany g){
        camera =            new OrthographicCamera();
        camera.setToOrtho(false, 8f, 4.8f);

        game =              g;
        img =               new Texture("button1.png");
        imgRegion =         new TextureRegion(img);
        imgRegionDraw =     new TextureRegionDrawable(imgRegion);
        playBtn =           new ImageButton(imgRegionDraw);
        stage =             new Stage(new ScreenViewport(camera));


        playBtn.setPosition(1,1);

        playBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("TAG", "Toimiiko? No vittu ei :D");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("toimiiko?","No toimii!");
                getGame().setScreen(getGame().getOfficeState());
                return true;
            }
        });
        stage.addActor(playBtn);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act(Gdx.graphics.getDeltaTime());
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

    public ChaosCompany getGame(){
        return game;
    }
}
