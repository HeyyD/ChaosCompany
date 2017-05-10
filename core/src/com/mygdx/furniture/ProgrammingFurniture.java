package com.mygdx.furniture;

/**
 * Created by SamiH on 16.3.2017.
 */

abstract class ProgrammingFurniture extends Furniture {
    /**
     * How much this furniture gives Programming power
     */
    private int programmingPower = 0;

    public void setProgrammingPower(int power){
        programmingPower = power;
    }
    public int getProgrammingPower(){
        return programmingPower;
    }

}