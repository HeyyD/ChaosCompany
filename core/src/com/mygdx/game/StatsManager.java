package com.mygdx.game;

/**
 * Created by SamiH on 5.3.2017.
 */

public class StatsManager {

    private int welfare = 50;
    private int money = 500;

    public int getMoney(){
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }
    public int getWelfare() {
        return welfare;
    }
    public void setWelfare(int welfare){
        this.welfare = welfare;
    }
}
