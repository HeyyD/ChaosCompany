package com.mygdx.game;

/**
 * Created by SamiH on 5.3.2017.
 */

public class StatsManager {

    private int wellBeing = 50;
    private int programmingPower = 0;
    private int money = 250000;
    private int employeeSlots = 0;
    private int employees = 0;

    //karma can range from -100 to 100
    private int karma = 0;
    //this is used so map can be updated. If the step is 10 or -10 the map will be udated.
    private int karmaStep = 0;

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
    public int getProgrammingPower(){
        return programmingPower;
    }
    public void setProgrammingPower(int programmingPower){
        this.programmingPower = programmingPower;
    }
    public int getEmployeeSlots(){
        return employeeSlots;
    }

    public int getKarma(){
        return karma;
    }

    public void setKarma(int karma){

        this.karma += karma;

        if(this.karma <= -100)
            this.karma = -100;
        else if(this.karma >= 100)
            this.karma = 100;

        if(this.karma < 100 && this.karma > -100) {
            karmaStep += karma;

            if (karmaStep >= 10 || karmaStep <= -10) {
                if (karmaStep > 0)
                    karmaStep -= 10;
                else if (karmaStep < 0)
                    karmaStep += 10;

                ChaosCompany.mapState.updateMap();
            }
        }
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
