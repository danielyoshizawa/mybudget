package com.depaul.daniel.mybudget;

/**
 * Created by JordanMiguel on 18/11/15.
 */
public class Currency {

    private String codeName, fullName;

    public Currency(String codeName, String fullName) {
        this.codeName = codeName;
        this.fullName = fullName;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
