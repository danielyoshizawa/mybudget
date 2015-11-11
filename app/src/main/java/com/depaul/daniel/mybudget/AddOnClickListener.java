package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class AddOnClickListener implements View.OnClickListener {

    private Activity activity;
    private Context context;

    public AddOnClickListener(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, EntityAdd.class);
        activity.startActivity(intent);
    }
}
