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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (convertView == null) {
            if (inflater == null)
                inflater = (LayoutInflater) listView.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_my_budget, parent, false);
        }

        /*ImageView icon = (ImageView) row.findViewById(R.id.image);
        TextView name = (TextView) row.findViewById(R.id.text1);
        TextView description = (TextView) row.findViewById(R.id.text2);

        ItemList band = BANDS[position];
        name.setText(band.getName());
        description.setText(band.getShortDescription());
        icon.setImageResource(band.getIconResource(band.getType()));*/
        return row;
    }
}
