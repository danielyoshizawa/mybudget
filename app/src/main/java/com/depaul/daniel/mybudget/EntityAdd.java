package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
    private EditText titleText;
    private EntryManager Entries;
    private CategoryManager Categories;
    private LinearLayout layout;
    private Boolean isIncome;

    private Spinner categorySpinner;

    private Category category;
    private DataValidator dataValidator;

    private AppLocationService appLocationService;

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
        dataValidator = new DataValidator(this);
        appLocationService = new AppLocationService(this);
    }

    private void inflate() {
        valueText = (EditText) findViewById(R.id.entity_add_value);
        titleText = (EditText) findViewById(R.id.entity_title);
        addButton = (Button) findViewById(R.id.entity_add_button);
        newCatButton = (Button) findViewById(R.id.entity_newcat_button);
        cleanButton = (Button) findViewById(R.id.entity_clean_button);
        cancelButton = (Button) findViewById(R.id.entity_cancel_button);
        spendButton = (Button) findViewById(R.id.entity_add_spend_button);
        incomeButton = (Button) findViewById(R.id.entity_add_income_button);
        layout = (LinearLayout) findViewById(R.id.entity_add_layout);

        categorySpinner = (Spinner) findViewById(R.id.entity_add_category_spinner);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red)); // Defaulting as Spend
    }

    private void configure() {
        valueText.setRawInputType(Configuration.KEYBOARD_12KEY);
        valueText.setText("$0.00");
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
                    String formatted = DataValidator.FormatCurrency(EntityAdd.this, parsed, true);

                    current = formatted;
                    valueText.setText(formatted);
                    valueText.setSelection(formatted.length());

                    valueText.addTextChangedListener(this);
                }
            }
        });
    }

    private void configureListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                String title = titleText.getText().toString();
                String cleanString = valueText.getText().toString().replaceAll("[$,]", "");
                double value = Double.parseDouble(cleanString);

                if (gpsLocation != null) {
                    double latitude = gpsLocation.getLatitude();
                    double longitude = gpsLocation.getLongitude();

                    if(dataValidator.bySize(Double.toString(value), "Value", 3, 20)
                            && dataValidator.bySize(title, "Title", 3, 20)) {
                        Entries.Add(new Entry(title, value, isIncome, latitude, longitude, category));
                        finish(); // When added, its finished, so, I switched from clean to finish add activity
                    }
                } else {
                    showSettingsAlert();
                }
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

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                EntityAdd.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        EntityAdd.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
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
