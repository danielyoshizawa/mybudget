package com.depaul.daniel.mybudget;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyFormatInputFilter implements InputFilter {

    // Need to verify this Regex
    Pattern mPattern = Pattern.compile("^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{2})?|(?:,[0-9]{3})*(?:\\.[0-9]{2})?|(?:\\.[0-9]{3})*(?:,[0-9]{2})?)$");//(0|[1-9]+[0-9]*)?(\\\\.[0-9]{0,2})?");

    @Override
    public CharSequence filter(
            CharSequence source,
            int start,
            int end,
            Spanned dest,
            int dstart,
            int dend) {

        String result =
                dest.subSequence(0, dstart)
                        + source.toString()
                        + dest.subSequence(dend, dest.length());

        Matcher matcher = mPattern.matcher(result);

        if (!matcher.matches()) return dest.subSequence(dstart, dend);

        return null;
    }
}