/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 23.01.21 2:57
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.options.categories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aid.moneymanager.R;
import com.aid.moneymanager.models.Category;

import java.util.List;


public class CustomCategoriesAdapter extends ArrayAdapter<Category> implements View.OnClickListener {

    private final Activity activity;
    Context context;

    public CustomCategoriesAdapter(Activity activity, List<Category> data, Context context) {
        super(context, R.layout.favorites_listview_row, data);
        this.context = context;
        this.activity = activity;

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.custom_categories_listview_row, parent, false);

        Category category = getItem(position);

        TextView categoryNameTextView = listItem.findViewById(R.id.category_textview);
        ImageView iconImageView = listItem.findViewById(R.id.icon_imageview);

        iconImageView.setImageResource(category.getIconResourceID());
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(category.getIconColor()));

        categoryNameTextView.setText(category.getCategoryVisibleName(getContext()));


        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditCustomCategoryActivity.class);
                intent.putExtra("category-id", category.getCategoryID());
                intent.putExtra("category-name", category.getCategoryVisibleName(getContext()));
                intent.putExtra("category-color", category.getIconColor());
                activity.startActivity(intent);
            }
        });
        return listItem;
    }


}
