package com.mygdx.game;

/**
 * Created by SamiH on 5.3.2017.
 */

public class StatsManager {

    //Economy variables of game
    private int wellBeing = 0;
    private int programmingPower = 0;
    private int marketingPower = 0;
    private int income = 0;

    private int gameValue = 200;
    private int gameIncome = 0;
    private int salaries = 0;

    private int money = 5000;

    private int employeeSlots = 0;
    private int employees = 0;

    //karma can range from -100 to 100
    private int karma = 0;
    //this is used so map can be updated. If the step is 7 or -7 the map will be updated.
    private int stepAmount = 7;
    private int karmaStep = 0;

    public void resetManager(){
        wellBeing = 0;
        programmingPower = 0;
        marketingPower = 0;
        income = 0;
        gameValue = 0;
        gameIncome = 0;
        salaries = 0;
        money = 5000;
        employeeSlots = 0;
        employees = 0;
        karma = 0;
        stepAmount = 7;
        karmaStep = 0;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getGameValue() {
        return gameValue;
    }

    public void setGameValue(int gameValue) {
        this.gameValue = gameValue;
    }

    public int getGameIncome() {
        return gameIncome;
    }

    public void setGameIncome(int gameIncome) {
        this.gameIncome = gameIncome;
    }

    public int getSalaries() {
        return salaries;
    }

    public void setSalaries(int salaries) {
        this.salaries = salaries;
    }

    public int getMarketingPower() {
        return marketingPower;
    }

    public void setMarketingPower(int marketingPower) {
        this.marketingPower = marketingPower;
    }



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

            if (karmaStep >= stepAmount || karmaStep <= -stepAmount) {
                if (karmaStep > 0)
                    karmaStep -= stepAmount;
                else if (karmaStep < 0)
                    karmaStep += stepAmount;

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
