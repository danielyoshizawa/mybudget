package com.depaul.daniel.mybudget;

/*
 * Data Object Entry
*/

public class Entry {

    private double value;
    private boolean isIncome;
    private double latitude;
    private double longitude;

    public Entry(double value, boolean isIncome, double latitude, double longitude) {
        this.value = value;
        this.isIncome = isIncome;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void SetValue(double value) {
        this.value = value;
    }

    public void SetIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public String GetValue() {
        return Double.toString(value);
    }

    public boolean IsIncome() {
        return isIncome;
    }

    public void SetLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void SetLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String GetLatitude() { // TODO Not sure if it is ok to convert to String here on the entity
        return Double.toString(latitude);
    }

    public String GetLongitude() {
        return Double.toString(longitude);
    }
}
