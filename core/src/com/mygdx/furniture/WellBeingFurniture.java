package com.mygdx.furniture;

/**
 * Created by SamiH on 16.3.2017.
 */

abstract class WellBeingFurniture extends Furniture {
    /**
     * How much this furniture gives wellBeing
     */
    private int wellBeing = 0;

    public void setWellBeing(int wellBeing){
        this.wellBeing = wellBeing;
    }
    public int getWellBeing(){
        return wellBeing;
    }

}
