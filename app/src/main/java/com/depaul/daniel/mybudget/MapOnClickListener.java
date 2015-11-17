package com.depaul.daniel.mybudget;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.view.View;

public class MapOnClickListener implements View.OnClickListener {

    private Activity activity;
    private Context context;

    public MapOnClickListener(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, MapsActivity.class);
        activity.startActivity(intent);
    }
}
