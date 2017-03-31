package com.mygdx.game;

/**
 * Created by SamiH on 5.3.2017.
 */

public class StatsManager {

    private int wellBeing = 50;
    private int money = 10000;
    private int employeeSlots = 1;

    public int getMoney(){
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }
    public int getWellBeing() {
        return wellBeing;
    }
    public void setWellBeing(int wellBeing){
        this.wellBeing = wellBeing;
    }
    public int getEmployeeSlots(){
        return employeeSlots;
    }
    public void setEmployeeSlots(int slot){
        this.employeeSlots = slot;
    }
}
