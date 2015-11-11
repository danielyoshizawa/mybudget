package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EntityAdd extends Activity {

    private Button addButton;
    private EditText valueText;
    private EntryManager Entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_add);

        Entries = EntryManager.getInstance();
        valueText = (EditText) findViewById(R.id.entity_add_value);
        addButton = (Button) findViewById(R.id.entity_add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(valueText.getText().toString());
                Entries.Add(new Entry(value, true));
            }
        });
    }
}
