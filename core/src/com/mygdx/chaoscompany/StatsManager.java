package com.mygdx.chaoscompany;

/**
 * All stats of the game is managed through this class
 * Created by SamiH on 5.3.2017.
 */

public class StatsManager {

    //Economy variables of game
    /**
     * Affects how much money you get per game
     */
    private int wellBeing = 0;
    /**
     * Affects of how you produce games
     */
    private int programmingPower = 0;
    /**
     * Multiplier to you income
     */
    private int marketingPower = 0;
    /**
     * Income you add to your money every 20sec
     */
    private int income = 0;

    /**
     * Value of money you get per tick for one game
     */
    private int gameValue = 200;
    /**
     * All game values added togheter
     */
    private int gameIncome = 0;
    /**
     * All salaries added togheter
     */
    private int salaries = 0;

    /**
     * Money
     */
    private int money = 5000;

    /**
     * How much employees you can have
     */
    private int employeeSlots = 0;
    /**
     * How many employees you have
     */
    private int employees = 0;

    //karma can range from -100 to 100
    /**
     * karma that changes you when make games, affects to map state
     */
    private int karma = 0;
    /**
     * this is used so map can be updated. If the step is 7 or -7 the map will be updated.
     */
    private int stepAmount = 7;
    /**
     * How much Karma updated per game
     */
    private int karmaStep = 0;

    /**
     * Resets the manager when new game is started
     */
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
