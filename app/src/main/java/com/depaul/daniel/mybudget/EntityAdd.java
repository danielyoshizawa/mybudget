package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EntityAdd extends Activity {

    private Button addButton;
    private Button cleanButton;
    private Button cancelButton;
    private Button spendButton;
    private Button incomeButton;
    private EditText valueText;
    private EntryManager Entries;
    private LinearLayout layout;
    private Boolean isIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_add);

        initialize();
        inflate();
        configure();
        configureListeners();
    }

    private void initialize() {
        isIncome = false;
        Entries = EntryManager.getInstance();
    }

    private void inflate() {
        valueText = (EditText) findViewById(R.id.entity_add_value);
        addButton = (Button) findViewById(R.id.entity_add_button);
        cleanButton = (Button) findViewById(R.id.entity_clean_button);
        cancelButton = (Button) findViewById(R.id.entity_cancel_button);
        spendButton = (Button) findViewById(R.id.entity_add_spend_button);
        incomeButton = (Button) findViewById(R.id.entity_add_income_button);
        layout = (LinearLayout) findViewById(R.id.entity_add_layout);
    }

    private void configure() {
        valueText.setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
    }

    private void configureListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(valueText.getText().toString());
                Entries.Add(new Entry(value, isIncome));
                valueText.setText("");
            }
        });

        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueText.setText("");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(getColor(R.color.light_red));
                isIncome = false;
            }
        });

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(getColor(R.color.light_blue));
                isIncome = true;
            }
        });
    }
}
