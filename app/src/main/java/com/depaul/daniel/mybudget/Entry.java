package com.depaul.daniel.mybudget;

/*
 * Data Object Entry
*/

public class Entry {

    private String title;
    private double value;
    private boolean isIncome;
    private double latitude;
    private double longitude;
    private Category category;

    public Entry(String title, double value, boolean isIncome, double latitude, double longitude, Category category) {
        this.title = title;
        this.value = value;
        this.isIncome = isIncome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public void SetValue(double value) { this.value = value; }

    public void SetIsIncome(boolean isIncome) { this.isIncome = isIncome; }

    public void SetTitle(String title) { this.title = title; }

    public String GetTitle() { return this.title; }

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

    public void SetCategory(Category category) {
        this.category = category;
    }

    public String GetLatitude() { // TODO Not sure if it is ok to convert to String here on the entity
        return Double.toString(latitude);
    }

    public String GetLongitude() {
        return Double.toString(longitude);
    }

    public double GetLatitudeDouble() {
        return latitude;
    }

    public double GetLongitudeDouble() {
        return longitude;
    }

    public Category GetCategory() {
        return category;
    }

    public double GetValueDouble() {
        return value;
    }
}
