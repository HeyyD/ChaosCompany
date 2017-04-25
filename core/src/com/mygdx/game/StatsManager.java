package com.mygdx.game;

/**
 * Created by SamiH on 5.3.2017.
 */

public class StatsManager {

    private int wellBeing = 50;
    private int money = 2000;
    private int employeeSlots = 0;
    private int employees = 0;

    //karma can range from -100 to 100
    private int karma = 0;

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

    public int getKarma(){
        return karma;
    }

    public void setKarma(int karma){
        if(karma <= -100)
            this.karma = -100;
        else if(karma >= 100)
            this.karma = 100;
        else
            this.karma = karma;
    }

    public void setEmployeeSlots(int slot){
        this.employeeSlots = slot;
    }
    public void setEmployees(int employees){
        this.employees = employees;
    }
    public int getEmployees(){
        return employees;
    }
}
