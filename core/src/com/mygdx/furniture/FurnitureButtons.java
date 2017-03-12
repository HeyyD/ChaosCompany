package com.mygdx.furniture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.ChaosCompany;
import com.mygdx.map.Tile;

/**
 * Created by SamiH on 11.3.2017.
 */

public class FurnitureButtons {
    private TextButton      move;
    private TextButton      rotate;
    private TextButton      buySell;
    private TextButton      cancel;
    private Furniture       furniture;
    private String          buySellText = "B";
    private Skin            skin;
    private ChaosCompany    game;
    private Stage           stage;
    private Vector3         touch;
    private Tile[][]          tiles;
    private Vector2         screenCoords;

    private TextButton.TextButtonStyle textButtonStyle;

    private float           buttonScale = 0.01f;
    public FurnitureButtons(ChaosCompany g, Furniture f){
        game = g;
        furniture = f;
        stage = game.getOfficeState().getStage();
        tiles = game.getOfficeState().getTileMap().getTiles();

        //CREATE SKIN
        skin = new Skin();
        Pixmap pixmap = new Pixmap(40,40 , Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        //Setting up skin font of button.
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        //Config TextButtonStyle and name it "default"
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        //END OF SKIN CREATION

        touch = new Vector3();
        screenCoords = new Vector2(0,0);

        create();
    }

    public void create(){
        //ROTATE BUTTON
        rotate = new TextButton("R", textButtonStyle);
        rotate.setTransform(true);
        rotate.setScale(buttonScale);
        rotate.setPosition(furniture.getX()+ 0.5f,furniture.getY() -0.5f);
        rotate.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 40 && y > 0 && y < 40){
                    furniture.rotate();
                }
            }
        });
        stage.addActor(rotate);

        //MOVE BUTTON
        move = new TextButton("M", textButtonStyle);
        move.setTransform(true);
        move.setScale(buttonScale);
        move.setPosition(furniture.getX()+1f, furniture.getY() +1f );

        move.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchDragged(InputEvent event, float x, float y, int pointer){

                //screenCoords.set(Gdx.input.getX(), Gdx.input.getY());
                //game.getOfficeState().getFurnitureStage().screenToStageCoordinates(screenCoords);
                //furniture.setPosition(screenCoords.x-1.2f, screenCoords.y-1.25f);
                touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.getOfficeState().getCam().unproject(touch);
                touch.mul(game.getOfficeState().getInvIsotransform());


                if((int)touch.x > 0 && (int)touch.x < tiles.length && (int)touch.y > 0 && (int)touch.y < tiles[0].length);
                {
                    try {
                        System.out.println((int) touch.x + " " + (int) touch.y);
                        furniture.setX(tiles[(int) touch.x][(int) touch.y].getX());
                        furniture.setY(tiles[(int) touch.x][(int) touch.y].getY());
                    }catch(Exception e){
                }
                }

                move.setPosition(furniture.getX()+1f, furniture.getY() + 1f);
                cancel.setPosition(furniture.getX(), furniture.getY() + 1f);
                rotate.setPosition(furniture.getX()+0.5f, furniture.getY() -0.5f);
                buySell.setPosition(furniture.getX()+1f, furniture.getY());
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 40 && y > 0 && y < 40){

                }
            }

        });
        stage.addActor(move);

        //CANCEL BUTTON
        cancel = new TextButton("X", textButtonStyle);
        cancel.setTransform(true);
        cancel.setScale(buttonScale);
        cancel.setPosition(furniture.getX(), furniture.getY() +1f );

        cancel.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 40 && y > 0 && y < 40){
                    if(furniture.getBought() == false) {
                        furniture.remove();
                    }
                    rotate.remove();
                    cancel.remove();
                    move.remove();
                    buySell.remove();
                }
            }
        });
        stage.addActor(cancel);

        //BUY / SELL BUTTON
        buySell = new TextButton(buySellText, textButtonStyle);
        buySell.setTransform(true);
        buySell.setScale(buttonScale);
        buySell.setPosition(furniture.getX()+1f, furniture.getY() );

        buySell.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //if user is not on top of the button anymore, it dosent do anything
                if(x > 0 && x < 40 && y > 0 && y < 40){
                    if(furniture.getBought()){
                        rotate.remove();
                        cancel.remove();
                        move.remove();
                        buySell.remove();
                        furniture.sell();
                        buySellText = "B";
                    }else{
                        rotate.remove();
                        cancel.remove();
                        move.remove();
                        buySell.remove();
                        furniture.buy();
                        buySellText = "S";
                    }
                }
            }
        });
        stage.addActor(buySell);
    }
    public TextButton getMove(){
        return move;
    }
    public TextButton getCancel(){
        return cancel;
    }
    public TextButton getRotate(){
        return rotate;
    }
    public TextButton getBuySell(){
        return buySell;
    }

    public boolean isButtonsOpen(){
        boolean isOpen = true;
        if(buySell.getStage() == null && cancel.getStage() == null &&
                rotate.getStage() == null && move.getStage() == null){
            isOpen = false;
        }
        return isOpen;
    }

}
