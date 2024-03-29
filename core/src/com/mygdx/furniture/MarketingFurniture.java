package com.mygdx.furniture;

/**
 * Created by SamiH on 16.3.2017.
 */

abstract class MarketingFurniture extends Furniture {
    /**
     * How much this furniture gives marketing power
     */
    private int marketingPower = 0;

    public int getMarketingPower(){
        return marketingPower;
    }
    public void setMarketingPower(int marketingPower){
        this.marketingPower = marketingPower;
    }
}
