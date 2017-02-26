package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by SamiH on 24.2.2017.
 */

public class MainMenuState implements Screen {
    private Skin skin;
    private ChaosCompany game;
    private Texture img;
    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private final float SCREEN_WIDTH = 800;
    private final float SCREEN_HEIGHT = 480;

    public MainMenuState(ChaosCompany g){
        this.game = g;
        create();
    }

    public void create(){
        batch =         new SpriteBatch();
        stage =         new Stage(new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT));

        Gdx.input.setInputProcessor(stage);

        //Setting up skin color and size
        skin = new Skin();
        Pixmap pixmap = new Pixmap(200,60, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        //Setting up skin font.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        //Create TextButton named "playBtn"
        final TextButton playBtn = new TextButton("PLAY",textButtonStyle);
        playBtn.setPosition(SCREEN_WIDTH/2 - 100 , SCREEN_HEIGHT/2 - 30);

        //Add InputListener to button
        playBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("X: "+ x + "Y: " + y);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 200 && y > 0 && y < 60){
                    game.setScreen(game.getOfficeState());
                }
            }
        });

        stage.addActor(playBtn);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //Clear screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Draw everything
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.begin();
        stage.draw();
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
        skin.dispose();
    }

    public ChaosCompany getGame(){
        return game;
    }
}
