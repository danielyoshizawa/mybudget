package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EntryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity context;
    private EntryManager Entries = EntryManager.getInstance();

    public EntryAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Entries.Size();
    }

    @Override
    public Object getItem(int position) {
        return Entries.GetEntryAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (convertView == null) {
            if (inflater == null)
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.entry_layout, parent, false);
        }

        Entry entry = Entries.GetEntryAt(position);

        if (entry.IsIncome()) {
            row.setBackgroundColor(context.getColor(R.color.light_blue));
        } else {
            row.setBackgroundColor(context.getColor(R.color.light_red));
        }

        TextView value = (TextView) row.findViewById(R.id.EntryValue);
        value.setText(entry.GetValue());

        return row;
    }
}
