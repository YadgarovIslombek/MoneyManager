/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 19.01.21 22:12
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.add_kiritish;

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

import com.aid.moneymanager.R;

import java.util.List;

public class EnterTypeAdapter extends ArrayAdapter<String> {
private final List<EnterTypeListViewModel> items;
private final Context context;

public EnterTypeAdapter(Context context, int resource,
        List objects) {
        super(context, resource, 0, objects);
        this.context = context;
        items = objects;
        }

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@Override
public View getDropDownView(int position, View convertView,
                            ViewGroup parent) {
        return createItemView(position, convertView, parent);
        }

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@Override
public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
        }

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
private View createItemView(int position, View convertView, ViewGroup parent) {
final View view = LayoutInflater.from(context).inflate(R.layout.new_entry_type_spinner_row, parent, false);

        TextView textView = view.findViewById(R.id.item_category);
        ImageView iconImageView = view.findViewById(R.id.icon_imageview);

        iconImageView.setImageResource(items.get(position).iconID);
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(items.get(position).color));
        textView.setText(items.get(position).name);

        return view;
        }
}