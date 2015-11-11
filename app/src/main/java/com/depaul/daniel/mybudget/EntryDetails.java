package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EntryDetails extends Activity {

    private int position;
    private EntryManager entryManager;
    private Button removeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        // TODO : Refactor
        entryManager = EntryManager.getInstance();
        removeButton = (Button) findViewById(R.id.button_remove_detail);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryManager.RemoveAt(position);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            TextView name = (TextView) findViewById(R.id.EntryValue);
            name.setText(intent.getCharSequenceExtra("EntryValue"));
            position = intent.getIntExtra("EntryPosition", 0);
        }
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
