package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

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

    public static String FormatCurrency(Activity a, double number) {
        Locale defaultLocale = new Locale(a.getResources().getString(R.string.lang),
                a.getResources().getString(R.string.loc),
                a.getResources().getString(R.string.currency));
        String formatted = NumberFormat.getCurrencyInstance(defaultLocale).format(number);

        return formatted;
    }

    public static String FormatCurrency(Activity a, double number, boolean typing) {
        Locale defaultLocale = new Locale(a.getResources().getString(R.string.lang),
                a.getResources().getString(R.string.loc),
                a.getResources().getString(R.string.currency));
        String formatted = NumberFormat.getCurrencyInstance(defaultLocale).format((number / 100));

        return formatted;
    }


}
