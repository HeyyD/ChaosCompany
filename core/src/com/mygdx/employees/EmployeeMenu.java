package com.mygdx.employees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EmployeeMenu extends Actor{

    protected TextButton        cancelButton = null;

    private Employee            employee = null;
    private Skin                skin;
    private OrthographicCamera  camera = null;

    private int                 buttonWidth = 50;
    private int                 buttonHeight = 50;

    private Texture             menuBackground;
    private float               menuWidth = 0.2f;
    private float               menuHeight = 0.3f;
    private float               menuOffset = 1f;

    EmployeeMenu(Employee employee, Stage stage, OrthographicCamera camera){
        menuBackground = new Texture("white.jpg");
        this.employee = employee;
        this.camera = camera;
        createSkin();
        setSize(camera.viewportWidth * menuWidth, camera.viewportHeight * menuHeight);
        cancelButton = new TextButton("X", skin);
        cancelButton.setTransform(true);
        cancelButton.getLabel().setFontScale(2);
        cancelButton.addListener(new MenuListener(employee));
        stage.addActor(this);
        stage.addActor(cancelButton);
    }

    @Override
    public void draw(Batch batch, float alpha){
        act(Gdx.graphics.getDeltaTime());
        setSize(camera.viewportWidth * menuWidth, camera.viewportHeight * menuHeight);
        setPosition(employee.getX(), employee.getY() + menuOffset);
        cancelButton.setScale(getWidth() * 0.003f);
        cancelButton.setPosition(getX() + getWidth() * 0.7f, getY() + getHeight() * 0.7f);

        batch.draw(menuBackground, getX(), getY(), getWidth(), getHeight());
}

    @Override
    public void act(float delta){
        super.act(delta);
    }

    private void createSkin(){
        //Setting up skin color and size of button
        skin = new Skin();
        Pixmap pixmap = new Pixmap(buttonWidth, buttonHeight, Pixmap.Format.RGBA8888);
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

    }

    private class MenuListener extends InputListener {

        private Employee employee;

        public MenuListener(Employee employee) {
            this.employee = employee;
        }

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                EmployeeMenu.this.cancelButton.remove();
                employee.menu.remove();
                employee.menu = null;
            }
        }
    }