package com.depaul.daniel.mybudget;

public class Category {

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public String GetName() {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
