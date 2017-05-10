package com.mygdx.furniture;

/**
 * Created by SamiH on 16.3.2017.
 */

public abstract class ComputerFurniture extends Furniture {
    /**
     * How many EmployeeSlots Computer gives
     */
    private int employeeSlot;

    public int getEmployeeSlot(){
        return employeeSlot;
    }
    public void setEmployeeSlot(int employeeSlot){
        this.employeeSlot = employeeSlot;
    }

}
