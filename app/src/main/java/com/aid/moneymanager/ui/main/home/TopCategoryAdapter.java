/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 21.01.21 23:19
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.aid.moneymanager.R;
import com.aid.moneymanager.models.Category;
import com.aid.moneymanager.utils.CurrencyHelper;

import java.util.ArrayList;



public class TopCategoryAdapter extends ArrayAdapter<TopCategoryListViewModel> implements View.OnClickListener {

    private ArrayList<TopCategoryListViewModel> dataSet;
    Context context;


    public TopCategoryAdapter(ArrayList<TopCategoryListViewModel> data, Context context) {
        super(context, R.layout.favorites_listview_row, data);
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public void onClick(View v) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.favorites_listview_row, parent, false);

        TopCategoryListViewModel dataModel = getItem(position);
        Category category = dataModel.getCategory();

        TextView categoryNameTextView = listItem.findViewById(R.id.item_category);
        TextView sumTextView = listItem.findViewById(R.id.item_sum);
        ImageView iconImageView = listItem.findViewById(R.id.icon_imageview);

        iconImageView.setImageResource(category.getIconResourceID());
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(category.getIconColor()));

        categoryNameTextView.setText(dataModel.getCategoryName());
        sumTextView.setText(CurrencyHelper.formatCurrency(dataModel.getCurrency(), dataModel.getMoney()));
        if (dataModel.getMoney() < 0)
            sumTextView.setTextColor(ContextCompat.getColor(context, R.color.gauge_expense));
        else
            sumTextView.setTextColor(ContextCompat.getColor(context, R.color.gauge_income));

        listItem.setClickable(false);
        listItem.setOnClickListener(null);
        return listItem;
    }
}
