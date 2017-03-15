package com.mygdx.employees;

import com.badlogic.gdx.graphics.Texture;

public class Programmer extends Employee{

    public Programmer(float x, float y, float width, float height, float skill) {
        super(new Texture("employee.png"), x, y, width, height, skill);
    }
}
