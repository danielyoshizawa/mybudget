package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;


public class ListViewOnClickListener implements AdapterView.OnItemClickListener {

    private Activity activity;
    private Context context;

    public ListViewOnClickListener(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, EntryDetails.class);
        String valueStr = EntryAdapter.Entries.GetEntryAt(position).GetValue();
        intent.putExtra("EntryValue", valueStr);
        activity.startActivity(intent);
    }
}
