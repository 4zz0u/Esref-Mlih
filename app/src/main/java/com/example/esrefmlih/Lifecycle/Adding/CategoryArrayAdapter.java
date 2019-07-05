package com.example.esrefmlih.Lifecycle.Adding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esrefmlih.R;

import java.util.List;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {

    CategoryArrayAdapter(Context context, List<Category> objects) {
       super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // An instance of category
        Category category = getItem(position);

        //getting the category name from the instance and push it to the item text view
        TextView categoryText =  listItemView.findViewById(R.id.text_category);
        categoryText.setText(category.getCategoryName());

        //getting the category icon from the instance and push it to the item icon
        ImageView iconCategory =  listItemView.findViewById(R.id.ic_category);
        iconCategory.setImageResource(category.getIconRessourceId());

        //changing the category's background color
        listItemView.setBackgroundResource(category.getBackgroundColor());


        return listItemView;
    }
}
