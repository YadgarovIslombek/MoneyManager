/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 20.01.21 0:02
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.models;

import android.graphics.Color;

import com.aid.moneymanager.R;

public  class DefaultCategory {
    private static Category[] categories = new Category[]{
            new Category(":others", "Boshqalar", R.drawable.category_default, Color.parseColor("#455a64")),
            new Category(":clothing", "Kiyim-kechak", R.drawable.category_clothing, Color.parseColor("#d32f2f")),
            new Category(":food", "Oziq=ovqat", R.drawable.category_food, Color.parseColor("#c2185b")),
            new Category(":gas_station", "Yoqilg'i (Benzin)", R.drawable.category_gas_station, Color.parseColor("#7b1fa2")),
            new Category(":gaz_station", "Yoqilg'i (Metan)", R.drawable.category_gas_station, Color.parseColor("#7b1fa2")),
            new Category(":gazPr_station", "Yoqilg'i (Propan)", R.drawable.category_gas_station, Color.parseColor("#7b1fa2")),
            new Category(":gaming", "O'yinlar", R.drawable.category_gaming, Color.parseColor("#512da8")),
            new Category(":gift", "Sovg'a", R.drawable.category_gift, Color.parseColor("#303f9f")),
            new Category(":holidays", "Bayram", R.drawable.category_holidays, Color.parseColor("#1976d2")),
            new Category(":home", "Uy", R.drawable.category_home, Color.parseColor("#0288d1")),
            new Category(":kids", "Bolalar", R.drawable.category_kids, Color.parseColor("#0097a7")),
            new Category(":pharmacy", "Dori-darmon", R.drawable.category_pharmacy, Color.parseColor("#00796b")),
            new Category(":repair", "Repair", R.drawable.category_repair, Color.parseColor("#388e3c")),
            new Category(":shopping", "Xaridl", R.drawable.category_shopping, Color.parseColor("#689f38")),
            new Category(":sport", "Sport", R.drawable.category_sport, Color.parseColor("#afb42b")),
            new Category(":transfer", "Qarz", R.drawable.category_transfer, Color.parseColor("#fbc02d")),
            new Category(":transport", "Yo'l kira", R.drawable.category_transport, Color.parseColor("#ffa000")),
            new Category(":work", "Oylik", R.drawable.category_briefcase, Color.parseColor("#f57c00")),


    };
    public static Category createDefaultCategoryModel(String visibleName) {
        return new Category("default", visibleName, R.drawable.category_default,
                Color.parseColor("#26a69a"));
    }


    public static Category[] getDefaultCategories() {
        return categories;
    }
}