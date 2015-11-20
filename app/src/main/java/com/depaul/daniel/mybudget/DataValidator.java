package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JordanMiguel on 20/11/15.
 */
public class DataValidator {
    private Context context;

    public DataValidator(Activity a) {
        context = a.getApplicationContext();
    }

    public void showError(String phrase) {
        Toast.makeText(context,
                phrase, Toast.LENGTH_LONG).show();
    }

    public boolean bySize(String text, String field, int min, int max) {
        if(text.length() < min || text.length() > max) {
            showError(field+" needs to have between "+min+" and "+max+" characters!");
            return false;
        }
        return true;
    }


}
