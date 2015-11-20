package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

// TODO : Create a GUI Manager

public class MyBudget extends Layout {

    private ListView listView;
    private Button addButton;
    private EntryAdapter entryAdapter;
    private TextView totalValueLabel;
    private TextView incomeValueLabel;
    private TextView spendValueLabel;
    private EntryManager entryManager;
    private CategoryManager categoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup vg = (ViewGroup) findViewById(R.id.content);
        ViewGroup.inflate(MyBudget.this, R.layout.activity_my_budget, vg);

        initialize();
        inflate();
        configure();
        addCategories();
    }

    @Override
    protected void onResume() {
        super.onResume();
        entryAdapter.notifyDataSetChanged();

        totalValueLabel.setText(entryManager.GetTotal());
        incomeValueLabel.setText(entryManager.GetIncome());
        spendValueLabel.setText(entryManager.GetSpend());
    }

    private void initialize() {
        entryAdapter = new EntryAdapter(this);
        entryManager = EntryManager.getInstance();
        categoryManager = CategoryManager.getInstance();
    }

    private void inflate() {
        addButton = (Button) findViewById(R.id.button_add_activity);
        listView = (ListView) findViewById(R.id.entry_list);
        totalValueLabel = (TextView) findViewById(R.id.total_label);
        incomeValueLabel = (TextView) findViewById(R.id.income_label);
        spendValueLabel = (TextView) findViewById(R.id.spend_label);
    }

    private void configure() {
        listView.setAdapter(entryAdapter);
        listView.setOnItemClickListener(new ListViewOnClickListener(this, MyBudget.this));
        addButton.setOnClickListener(new AddOnClickListener(this, MyBudget.this));
    }

    private void addCategories() {
        categoryManager.Add(new Category("Groceries"));
        categoryManager.Add(new Category("Bills"));
        categoryManager.Add(new Category("Gas"));
        categoryManager.Add(new Category("Savings"));
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
