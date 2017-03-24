package com.mygdx.employees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class EmployeeMenu extends Actor{

    protected TextButton        cancelButton = null;

    private Employee            employee = null;
    private Skin                skin;
    private OrthographicCamera  camera = null;

    private int                 buttonWidth = 64;
    private int                 buttonHeight = 32;
    private float               buttonScale = 0.01f;

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
        stage.addActor(this);
        cancelButton = new TextButton("CANCEL", skin);
        cancelButton.setTransform(true);

        cancelButton.getLabel().setFontScale(0.01f);
        cancelButton.addListener(new MenuListener(employee));
        stage.addActor(cancelButton);
    }

    @Override
    public void draw(Batch batch, float alpha){
        act(Gdx.graphics.getDeltaTime());
        setSize(camera.viewportWidth * menuWidth, camera.viewportHeight * menuHeight);
        setPosition(employee.getX(), employee.getY() + menuOffset);
        cancelButton.setSize(getWidth() * 0.6f, getHeight() * 0.2f);
        cancelButton.setPosition(getX() + cancelButton.getWidth()/3, getY() + getHeight() * 0.1f);
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
