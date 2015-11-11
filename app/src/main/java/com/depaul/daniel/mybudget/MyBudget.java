package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyBudget extends Activity {

    private ListView listView;
    private Button addButton;
    private EntryAdapter entryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_budget);

        initialize();
        inflate();
        configure();
    }

    @Override
    protected void onResume() {
        super.onResume();
        entryAdapter.notifyDataSetChanged();
    }

    private void initialize() {
        entryAdapter = new EntryAdapter(this);
    }

    private void inflate() {
        addButton = (Button) findViewById(R.id.button_add_activity);
        listView = (ListView) findViewById(R.id.entry_list);
    }

    private void configure() {
        listView.setAdapter(entryAdapter);
        listView.setOnItemClickListener(new ListViewOnClickListener(this, MyBudget.this));
        addButton.setOnClickListener(new AddOnClickListener(this, MyBudget.this));
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
