package com.depaul.daniel.mybudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CategoryAdd extends AppCompatActivity {

    private CategoryManager Categories;
    private EditText categoryName;
    private Button addButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        initialize();
        inflate();
        configureListeners();
    }

    private void initialize() {
        Categories = CategoryManager.getInstance();
    }

    private void inflate() {
        categoryName = (EditText) findViewById(R.id.category_add_value);
        addButton = (Button) findViewById(R.id.category_add_button);
        cancelButton = (Button) findViewById(R.id.category_cancel_button);
    }

    private void configureListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categories.Add(new Category(categoryName.getText().toString()));
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
