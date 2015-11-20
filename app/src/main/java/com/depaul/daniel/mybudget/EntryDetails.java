package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import java.util.ArrayList;

public class EntryDetails extends Activity {

    private int position;
    private EntryManager Entries;
    private Entry thisEntry;
    private Button removeButton, editButton, changeCurrencyButton;
    private Spinner currencySpinner;
    private TextView valueLabel;
    private TextView latitudeLabel;
    private TextView longitudeLabel;
    private TextView categoryLabel;
    private TextView currencyLabel;
    private ArrayList<Currency> currencyList;

    // Constant for defining latitude and longitude
    static final LatLng DerekPos = new LatLng(40 , -79);

    // GoogleMap class
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        initialize();
        inflate();
        configureListeners();
        configureCurrencySpinner();
    }


    @Override
    protected void onStart() {

        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            // EDIT 1: Could not understand why pass all this values instead passing just the entry position
            // EDIT 1: and retrieving all of them directly from the EntryManager instance

            // EDIT 2: I just removed all intent content and passed just the position retrieving everything from that
            // EDIT 2: Had to do it to keep edit working as I need to update the values when resume
            position = intent.getIntExtra("EntryPosition", 0);
            thisEntry = Entries.GetEntryAt(position);

            valueLabel.setText(thisEntry.GetValue());
            latitudeLabel.setText(thisEntry.GetLatitude());
            longitudeLabel.setText(thisEntry.GetLongitude());
            categoryLabel.setText(thisEntry.GetCategory().GetName());

            try {
                if (googleMap == null) {
                    googleMap = ((MapFragment) getFragmentManager().
                            findFragmentById(R.id.map)).getMap();
                }

                LatLng location = new LatLng(Double.parseDouble(thisEntry.GetLatitude()),
                        Double.parseDouble(thisEntry.GetLongitude()));

                googleMap.addMarker(new MarkerOptions().position(location).title(thisEntry.GetValue()));
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                CameraUpdate center = CameraUpdateFactory.newLatLng(location);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                googleMap.moveCamera(center);
                googleMap.animateCamera(zoom);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onStart();
    }

    private void initialize() {
        currencyList = new ArrayList<Currency>();
        Entries = EntryManager.getInstance();
    }

    private void inflate() {
        removeButton = (Button) findViewById(R.id.button_remove_detail);
        editButton = (Button) findViewById(R.id.button_edit_detail);
        changeCurrencyButton = (Button) findViewById(R.id.button_change_currency);

        valueLabel = (TextView) findViewById(R.id.EntryValue); // TODO rename R.id
        latitudeLabel = (TextView) findViewById(R.id.details_latitude_label);
        longitudeLabel = (TextView) findViewById(R.id.details_longitude_label);
        categoryLabel = (TextView) findViewById(R.id.details_category_label);
        currencyLabel = (TextView) findViewById(R.id.details_currency_label);
        currencySpinner = (Spinner) findViewById(R.id.details_currency_spinner);

    }

    private void configureListeners() {
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entries.RemoveAt(position);
                finish();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EntryEdit.class);
                intent.putExtra("EntryPosition", position);
                context.startActivity(intent);
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
            double newValue = Double.parseDouble(currencyValue) * Double.parseDouble(thisEntry.GetValue());
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
