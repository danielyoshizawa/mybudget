package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EntryDetails extends Activity {

    private int position;
    private EntryManager entryManager;
    private Button removeButton, changeCurrencyButton;
    private Spinner currencySpinner;
    private TextView valueLabel;
    private TextView latitudeLabel;
    private TextView longitudeLabel;
    private TextView categoryLabel;
    private TextView currencyLabel;
    private CharSequence value;
    private ArrayList<Currency> currencyList;

    private static String currencyWanted = "", currencyValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        currencyList = new ArrayList<Currency>();

        // TODO : Refactor
        entryManager = EntryManager.getInstance();
        removeButton = (Button) findViewById(R.id.button_remove_detail);
        changeCurrencyButton = (Button) findViewById(R.id.button_change_currency);

        valueLabel = (TextView) findViewById(R.id.EntryValue); // TODO rename R.id
        latitudeLabel = (TextView) findViewById(R.id.details_latitude_label);
        longitudeLabel = (TextView) findViewById(R.id.details_longitude_label);
        categoryLabel = (TextView) findViewById(R.id.details_category_label);
        currencyLabel = (TextView) findViewById(R.id.details_currency_label);
        currencySpinner = (Spinner) findViewById(R.id.details_currency_spinner);

        configureCurrencySpinner();
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryManager.RemoveAt(position);
                finish();
            }
        });
        changeCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = currencySpinner.getSelectedItem().toString();

                for(Currency curr: currencyList) {
                    if(curr.getFullName().equals(fullName)) {
                        new GETCurrency().execute(curr.getCodeName());
                    }
                }
            }
        });

    }


    @Override
    protected void onStart() {

        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getCharSequenceExtra("EntryValue"); // TODO rename
            valueLabel.setText(value);
            latitudeLabel.setText(intent.getCharSequenceExtra("entry_latitute_value"));
            longitudeLabel.setText(intent.getCharSequenceExtra("entry_longitude_value"));
            categoryLabel.setText(intent.getCharSequenceExtra("entry_category_value"));
            position = intent.getIntExtra("EntryPosition", 0);
        }
    }

    private class GETCurrency extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /* Show that we are performing the action */
        }

        @Override
        protected String doInBackground(String... args) {
            CurrencyExchange jParser = new CurrencyExchange();

            String currencyValue = null;
            try {
                currencyValue = jParser.getCurrencyValue(args[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return currencyValue;
        }

        @Override
        protected void onPostExecute(String currencyValue) {
            double newValue = Double.parseDouble(currencyValue) * Double.parseDouble(value.toString());
            Log.d(currencyValue + "  " + value.toString(), "TEST");
            currencyLabel.setText(Double.toString(newValue));
        }
    }

    private void configureCurrencySpinner() {
        currencyList.add(new Currency("BRL", "Brazilian Reais"));
        currencyList.add(new Currency("EUR", "Euro"));
        currencyList.add(new Currency("GBP", "Pound Sterling"));
        currencyList.add(new Currency("CAD", "Canadian Dollar"));
        currencyList.add(new Currency("AUD", "Australian Dollar")); // You can add more if wanted

        ArrayAdapter<Currency> dataAdapter = new ArrayAdapter<Currency>(this, android.R.layout.simple_spinner_item, currencyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(dataAdapter);
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
