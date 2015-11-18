package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

// TODO : Extract Listeners
// TODO : Garanty consistency
public class EntityAdd extends Activity {

    private Button addButton;
    private Button cleanButton;
    private Button newCatButton;
    private Button cancelButton;
    private Button spendButton;
    private Button incomeButton;
    private EditText valueText;
    private EntryManager Entries;
    private CategoryManager Categories;
    private LinearLayout layout;
    private Boolean isIncome;

    private EditText latitudeText;
    private EditText longitudeText;
    private Spinner categorySpinner;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_add);

        initialize();
        inflate();
        configure();
        configureListeners();
        configureCategorySpinner();
    }

    @Override
    protected void onResume(){
        super.onResume();
        configureCategorySpinner();
    }

    private void initialize() {
        isIncome = false;
        Entries = EntryManager.getInstance();
        Categories = CategoryManager.getInstance();
    }

    private void inflate() {
        valueText = (EditText) findViewById(R.id.entity_add_value);
        addButton = (Button) findViewById(R.id.entity_add_button);
        newCatButton = (Button) findViewById(R.id.entity_newcat_button);
        cleanButton = (Button) findViewById(R.id.entity_clean_button);
        cancelButton = (Button) findViewById(R.id.entity_cancel_button);
        spendButton = (Button) findViewById(R.id.entity_add_spend_button);
        incomeButton = (Button) findViewById(R.id.entity_add_income_button);
        layout = (LinearLayout) findViewById(R.id.entity_add_layout);

        latitudeText = (EditText) findViewById(R.id.entity_add_latitude);
        longitudeText = (EditText) findViewById(R.id.entity_add_longitude);

        categorySpinner = (Spinner) findViewById(R.id.entity_add_category_spinner);
    }

    private void configure() {
        valueText.setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
    }

    private void configureListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(valueText.getText().toString());
                double latitude = Double.parseDouble(latitudeText.getText().toString());
                double longitude = Double.parseDouble(longitudeText.getText().toString());
                Entries.Add(new Entry(value, isIncome, latitude, longitude, category));
                finish(); // When added, its finished, so, I switched from clean to finish add activity
            }
        });

        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanFields();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CategoryAdd.class);
                context.startActivity(intent);
            }
        });

        spendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.light_red));
                isIncome = false;
            }
        });

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.light_blue));
                isIncome = true;
            }
        });
    }

    private void cleanFields() {
        valueText.setText("");
        latitudeText.setText("");
        longitudeText.setText("");
    }

    private void configureCategorySpinner() {
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, Categories.getList());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = Categories.GetCategoryAt(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = Categories.GetCategoryAt(0);
            }
        });
    }
}
