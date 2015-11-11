package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

public class MyBudget extends Activity {

    private ListView listView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_budget);

        inflate();
        configure();
    }

    private void inflate() {
        addButton = (Button) findViewById(R.id.button_add_activity);
        listView = (ListView) findViewById(R.id.entry_list);
    }

    private void configure() {
        listView.setAdapter(new EntryAdapter(this));
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
