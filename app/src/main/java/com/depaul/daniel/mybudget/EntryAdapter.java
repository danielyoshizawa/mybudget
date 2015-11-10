package com.depaul.daniel.mybudget;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EntryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ListActivity listView;

    public EntryAdapter(ListActivity listView) {
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return Entries.length;
    }

    @Override
    public Object getItem(int position) {
        return Entries[position];
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
                inflater = (LayoutInflater) listView.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_my_budget, parent, false);
        }

        TextView value = (TextView) row.findViewById(R.id.EntryValue);
        value.setText(Entries[position].GetValue());

        return row;
    }

    public static final Entry[] Entries = {
        new Entry(100.00, true),
        new Entry(120.00, false)
    };
}
