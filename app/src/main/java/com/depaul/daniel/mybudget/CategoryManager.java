package com.depaul.daniel.mybudget;

import java.util.ArrayList;

/**
 * Created by JordanMiguel on 18/11/15.
 */
public class CategoryManager {
    private static CategoryManager instance = null;
    private ArrayList<Category> categoryList;

    protected CategoryManager() {
        categoryList = new ArrayList<Category>();
    }

    public static CategoryManager getInstance() {
        if (instance == null)
            instance = new CategoryManager();
        return instance;
    }

    public void Add(Category category) {
        categoryList.add(category);
    }

    public ArrayList<Category> getList() {
        return categoryList;
    }

    public Category GetCategoryAt(int position) {
        return categoryList.get(position);
    }

    public int Size() {
        return categoryList.size();
    }

    public void RemoveAt(int position) {
        categoryList.remove(position);
    }

    public ArrayList<String> GetCategoriesName() {
        ArrayList<String> names = new ArrayList<String>();

        for (Category category : categoryList) {
            names.add(category.GetName());
        }

        return names;
    }
}
