package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        TextView value = (TextView) row.findViewById(R.id.EntryValue);
        value.setText(DataValidator.FormatCurrency(context, Double.parseDouble(entry.GetValue())));

        TextView title = (TextView) row.findViewById(R.id.EntryTitle);
        title.setText(entry.GetTitle());

        TextView category = (TextView) row.findViewById(R.id.EntryCategory);
        category.setText(entry.GetCategory().GetName());

        if (entry.IsIncome()) {
            category.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
            value.setTextColor(ContextCompat.getColor(context, R.color.blue));
            title.setTextColor(ContextCompat.getColor(context, R.color.blue));
        } else {
            category.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            value.setTextColor(ContextCompat.getColor(context, R.color.red));
            title.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        return row;
    }
}
