package com.depaul.daniel.mybudget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class EntryEdit extends AppCompatActivity {

    private Button editButton;
    private Button cleanButton;
    private Button newCatButton;
    private Button cancelButton;
    private Button spendButton;
    private Button incomeButton;
    private EntryManager Entries;
    private Entry thisEntry;
    private CategoryManager Categories;
    private LinearLayout layout;
    private Boolean isIncome;

    private int position;
    private int spinnerSelection;

    private EditText valueText;
    private EditText titleText;
    private Spinner categorySpinner;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_edit);

        initialize();
        inflate();
        configure();
        configureListeners();
        configureCategorySpinner();
    }

    @Override
    protected void onStart() {

        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra("EntryPosition", 0);
            thisEntry = Entries.GetEntryAt(position);

            titleText.setText(thisEntry.GetTitle());
            valueText.setText(DataValidator.FormatCurrency(this, Double.parseDouble(thisEntry.GetValue())));

            // Setting the actual category on the Spinner
            int pos = 0;
            for(Category cat: Categories.getList()) {
                if(cat.equals(thisEntry.GetCategory())) {
                    spinnerSelection = pos;
                }
                pos++;
            }

            if(thisEntry.IsIncome()) {
                isIncome = true;
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_blue));
            } else {
                isIncome = false;
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red));
            }


        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        configureCategorySpinner();
    }

    private void initialize() {
        Entries = EntryManager.getInstance();
        Categories = CategoryManager.getInstance();
    }

    private void inflate() {
        editButton = (Button) findViewById(R.id.entity_edit_button);
        newCatButton = (Button) findViewById(R.id.entity_newcat_button);
        cleanButton = (Button) findViewById(R.id.entity_clean_button);
        cancelButton = (Button) findViewById(R.id.entity_cancel_button);
        spendButton = (Button) findViewById(R.id.entity_add_spend_button);
        incomeButton = (Button) findViewById(R.id.entity_add_income_button);
        layout = (LinearLayout) findViewById(R.id.entity_add_layout);

        valueText = (EditText) findViewById(R.id.entity_add_value);
        titleText = (EditText) findViewById(R.id.entity_title);

        categorySpinner = (Spinner) findViewById(R.id.entity_add_category_spinner);
    }

    private void configure() {
        valueText.setRawInputType(Configuration.KEYBOARD_12KEY);
        valueText.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    valueText.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = DataValidator.FormatCurrency(EntryEdit.this, parsed, true);

                    current = formatted;
                    valueText.setText(formatted);
                    valueText.setSelection(formatted.length());

                    valueText.addTextChangedListener(this);
                }
            }
        });
        titleText.requestFocus();
    }
    private void configureListeners() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cleanString = valueText.getText().toString().replaceAll("[$,]", "");
                double value = Double.parseDouble(cleanString);

                thisEntry.SetTitle(titleText.getText().toString());
                thisEntry.SetValue(value);
                thisEntry.SetCategory(category);
                thisEntry.SetIsIncome(isIncome);
                finish();
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
                Intent intent = new Intent(context, CategoryEdit.class);
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
        valueText.setText("$0.00");
        titleText.setText("");
        titleText.requestFocus();
    }

    private void configureCategorySpinner() {
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, Categories.getList());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
        categorySpinner.setSelection(spinnerSelection, true);

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
