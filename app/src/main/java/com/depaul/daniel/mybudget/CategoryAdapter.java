package com.depaul.daniel.mybudget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JordanMiguel on 20/11/15.
 */
public class CategoryAdapter extends BaseAdapter {

    private Activity Activity;
    private CategoryManager Categories;
    private static LayoutInflater inflater=null;
    private Context context;

    public CategoryAdapter (Activity a, CategoryManager cat) {
        this.Activity = a;
        this.Categories = cat;
        context = a.getApplication();
        inflater = (LayoutInflater) Activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Categories.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.list_category_row, null);

        TextView title = (TextView) vi.findViewById(R.id.category_name);
        Button remove = (Button) vi.findViewById(R.id.category_remove_button);

        Category cat = Categories.GetCategoryAt(position);

        title.setText(cat.GetName());
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Categories.getList().size() > 1)
                    Categories.RemoveAt(position);
                else
                    Toast.makeText(context.getApplicationContext(),
                            "Cannot remove last cateogory!", Toast.LENGTH_LONG).show();

                notifyDataSetChanged();
            }
        });

        return vi;
    }
}
