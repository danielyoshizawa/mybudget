package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ChangeActivityOnClickListener implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private Class uri;

    public ChangeActivityOnClickListener(Activity activity, Context context, Class uri) {
        this.activity = activity;
        this.context = context;
        this.uri = uri;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, uri);
        activity.startActivity(intent);
    }
}
