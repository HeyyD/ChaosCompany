package com.mygdx.employees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Employee extends Actor {

    //texture of the employee
    protected Texture texture;
    //The float that determines how well the employee can do his/her job 0-1
    private float skill;
    //Boolean to determine if the employee is free to work
    private boolean isAvailable;

    public Employee(Texture texture, float x, float y, float width, float height, float skill){
        this.skill = skill;
        isAvailable = true;
        this.texture = texture;
        setSize(width, height);
        setPosition(x, y);
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha){
        act(Gdx.graphics.getDeltaTime());
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

}
