/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 22.01.21 0:15
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.statistics;

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


public class TopCategoriesStatisticsAdapter extends ArrayAdapter<TopCategoryStatisticsListViewModel> implements View.OnClickListener {

    private ArrayList<TopCategoryStatisticsListViewModel> dataSet;
    Context context;


    public TopCategoriesStatisticsAdapter(ArrayList<TopCategoryStatisticsListViewModel> data, Context context) {
        super(context, R.layout.top_categories_statistics_listview_row, data);
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
            listItem = LayoutInflater.from(context).inflate(R.layout.top_categories_statistics_listview_row, parent, false);

        TopCategoryStatisticsListViewModel dataModel = getItem(position);
        Category category = dataModel.getCategory();

        TextView categoryNameTextView = listItem.findViewById(R.id.item_category);
        TextView sumTextView = listItem.findViewById(R.id.item_sum);
        TextView percentageTextView = listItem.findViewById(R.id.item_percentage);

        ImageView iconImageView = listItem.findViewById(R.id.icon_imageview);

        iconImageView.setImageResource(category.getIconResourceID());
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(category.getIconColor()));

        categoryNameTextView.setText(dataModel.getCategoryName());
        sumTextView.setText(CurrencyHelper.formatCurrency(dataModel.getCurrency(), dataModel.getMoney()));
        percentageTextView.setText((int)(dataModel.getPercentage() * 100) +"." + (int)(dataModel.getPercentage() * 10000) % 100  + "%");
        if (dataModel.getMoney() < 0)
            sumTextView.setTextColor(ContextCompat.getColor(context, R.color.gauge_expense));
        else
            sumTextView.setTextColor(ContextCompat.getColor(context, R.color.gauge_income));
        listItem.setClickable(false);
        listItem.setOnClickListener(null);
        return listItem;
    }
}
