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

    private int money = 5000;

    private int employeeSlots = 0;
    private int employees = 0;

    //karma can range from -100 to 100
    private int karma = 0;


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
