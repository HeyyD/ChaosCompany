package com.mygdx.furniture;

/**
 * Created by SamiH on 16.3.2017.
 */

abstract class ComputerFurniture extends Furniture {
    private int employeeSlot;

    public int getEmployeeSlot(){
        return employeeSlot;
    }
    public void setEmployeeSlot(int employeeSlot){
        this.employeeSlot = employeeSlot;
    }

}
