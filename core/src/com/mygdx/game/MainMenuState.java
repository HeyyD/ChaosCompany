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
    private Texture backgroundImage;
    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private final float SCREEN_WIDTH = 800;
    private final float SCREEN_HEIGHT = 480;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 60;

    public MainMenuState(ChaosCompany g){
        this.game = g;
        create();
    }

    public void create(){
        batch =             new SpriteBatch();
        stage =             new Stage(new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT));
        camera =            new OrthographicCamera();
        backgroundImage =   new Texture("menubackground.png");

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.setProjectionMatrix(camera.combined);
        Gdx.input.setInputProcessor(stage);

        //Setting up skin color and size of button
        skin = new Skin();
        Pixmap pixmap = new Pixmap(BUTTON_WIDTH,BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        //Setting up skin font of button.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        //Create TextButton three TextButtons
        final TextButton playBtn = new TextButton("PLAY",textButtonStyle);
        final TextButton settingsBtn = new TextButton("SETTINGS", textButtonStyle);
        final TextButton exitBtn = new TextButton("EXIT", textButtonStyle);


        //PLAYBUTTON
        playBtn.setPosition(SCREEN_WIDTH/2 - BUTTON_WIDTH/2 , SCREEN_HEIGHT/2 - BUTTON_HEIGHT /2);
        //Add InputListener to playBtn
        playBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("X: "+ x + "Y: " + y);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < BUTTON_WIDTH && y > 0 && y < BUTTON_HEIGHT){
                    game.setScreen(game.getOfficeState());
                }
            }
        });
        stage.addActor(playBtn);

        //SETTINGS BUTTON
        settingsBtn.setPosition(SCREEN_WIDTH/2 - BUTTON_WIDTH/2 , SCREEN_HEIGHT/2 - BUTTON_HEIGHT /2
        - BUTTON_HEIGHT - 10);
        //Add InputListener to settingsBtn
        settingsBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("X: "+ x + "Y: " + y);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < BUTTON_WIDTH && y > 0 && y < BUTTON_HEIGHT){
                    game.setScreen(game.getOfficeState());
                }
            }
        });
        stage.addActor(settingsBtn);

        //EXIT BUTTON
        exitBtn.setPosition(SCREEN_WIDTH/2 - BUTTON_WIDTH/2 , SCREEN_HEIGHT/2 - BUTTON_HEIGHT /2
                - BUTTON_HEIGHT * 2 - 40);
        //Add InputListener to exitBtn
        exitBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("X: "+ x + "Y: " + y);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < BUTTON_WIDTH && y > 0 && y < BUTTON_HEIGHT){
                    Gdx.app.log("", "Exiting..");
                    Gdx.app.exit();
                }
            }
        });
        stage.addActor(exitBtn);
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
        batch.draw(backgroundImage,0,0);
        batch.end();
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
        stage.dispose();
        skin.dispose();
    }

    public ChaosCompany getGame(){
        return game;
    }
}
