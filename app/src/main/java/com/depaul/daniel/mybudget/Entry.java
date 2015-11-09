package com.depaul.daniel.mybudget;

/*
 * Data Object
*/

public class Entry {

    private double value;
    private boolean isIncome;

    public Entry(double value, boolean isIncome) {
        this.value = value;
        this.isIncome = isIncome;
    }

    public void SetValue(double value) {
        this.value = value;
    }

    public void SetIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public double GetValue() {
        return value;
    }

    public boolean IsIncome() {
        return isIncome;
    }
}
