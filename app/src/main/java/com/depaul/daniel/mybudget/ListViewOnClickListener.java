package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;


public class ListViewOnClickListener implements AdapterView.OnItemClickListener {

    private Activity activity;
    private Context context;
    private EntryManager Entries;

    public ListViewOnClickListener(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        Entries = EntryManager.getInstance();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, EntryDetails.class);
        Entry entry = Entries.GetEntryAt(position);
        intent.putExtra("EntryValue", entry.GetValue());
        intent.putExtra("entry_latitute_value", entry.GetLatitude());
        intent.putExtra("entry_longitude_value", entry.GetLongitude());
        intent.putExtra("EntryPosition", position);
        activity.startActivity(intent);
    }
}
