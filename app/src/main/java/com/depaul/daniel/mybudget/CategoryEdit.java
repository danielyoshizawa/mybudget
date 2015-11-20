package com.depaul.daniel.mybudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CategoryEdit extends Layout {

    private CategoryManager Categories;
    private EditText categoryName;
    private Button addButton, cancelButton;
    private ListView list;
    private CategoryAdapter adapter;
    private DataValidator dataValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup vg = (ViewGroup) findViewById(R.id.content);
        ViewGroup.inflate(this, R.layout.activity_category_edit, vg);

        initialize();
        inflate();
        configureListeners();
        configureListView();
    }

    private void initialize() {
        Categories = CategoryManager.getInstance();
        dataValidator = new DataValidator(this);
    }

    private void inflate() {
        categoryName = (EditText) findViewById(R.id.category_add_value);
        addButton = (Button) findViewById(R.id.category_add_button);
        cancelButton = (Button) findViewById(R.id.category_cancel_button);
        list = (ListView) findViewById(R.id.category_list);
    }

    private void configureListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataValidator.bySize(categoryName.getText().toString(), "Category Name", 3, 20)) {
                    Categories.Add(new Category(categoryName.getText().toString()));
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void configureListView() {
        adapter = new CategoryAdapter(this, Categories);

        list.setAdapter(adapter);
    }
}
