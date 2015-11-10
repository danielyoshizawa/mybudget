package com.depaul.daniel.mybudget;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MyBudget extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new EntryAdapter(this));
    }


    @Override
    protected void onListItemClick(android.widget.ListView l, View v, int position, long id) {
        Intent intent = new Intent(MyBudget.this, EntryDetails.class);
        String valueStr = EntryAdapter.Entries.GetEntryAt(position).GetValue();
        intent.putExtra("EntryValue", valueStr);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
