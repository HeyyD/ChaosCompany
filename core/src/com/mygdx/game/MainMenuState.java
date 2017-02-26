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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by SamiH on 24.2.2017.
 */

public class MainMenuState implements Screen {
    private Skin skin;
    private ChaosCompany game;
    private Texture img;
    private Stage stage;
    private SpriteBatch batch;

    public MainMenuState(ChaosCompany g){
        this.game = g;
        create();
    }

    public void create(){
        batch =         new SpriteBatch();
        stage =         new Stage();
        Gdx.input.setInputProcessor(stage);

        //Setting up skin color and size
        skin = new Skin();
        Pixmap pixmap = new Pixmap(100,30, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        //Setting up skin font.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        //Create TextButton named "playBtn"
        final TextButton playBtn = new TextButton("PLAY",textButtonStyle);
        playBtn.setPosition(200, 200);


        playBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getOfficeState());
            }
        });

        stage.addActor(playBtn);
        stage.addActor(playBtn);
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
        stage.draw();
    }

    private void playButton(){
        Gdx.app.log("",""+ Gdx.input.getY());
        if(Gdx.input.isTouched()
                && Gdx.input.getX()/100f > 3 && Gdx.input.getX() /100f < 3+ img.getWidth() / 100f
                && Gdx.input.getY()/100f > 3 && Gdx.input.getY() /100f < 3+ img.getHeight() / 100f){
            img = new Texture("button2.png");
        }else{
            img = new Texture("button1.png");
        }
        if(Gdx.input.justTouched()
                && Gdx.input.getX()/100f > 3 && Gdx.input.getX() /100f < 3+ img.getWidth() / 100f
                && Gdx.input.getY()/100f > 3 && Gdx.input.getY() /100f < 3+ img.getHeight() / 100f){
            game.setScreen(game.getOfficeState());
        }
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
