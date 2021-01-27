/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 28.01.21 0:21
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.utils;

import android.graphics.Color;

import com.aid.moneymanager.R;
import com.aid.moneymanager.firebase.models.User;
import com.aid.moneymanager.firebase.models.WalletEntryCategory;
import com.aid.moneymanager.models.Category;
import com.aid.moneymanager.models.DefaultCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
public class CategoriesHelper {
    public static Category searchCategory(User user, String categoryName) {
        for (Category category : DefaultCategory.getDefaultCategories()) {
            if (category.getCategoryID().equals(categoryName)) return category;
        }
        for (Map.Entry<String, WalletEntryCategory> entry : user.customCategories.entrySet()) {
            if (entry.getKey().equals(categoryName)) {
                return new Category(categoryName, entry.getValue().visibleName, R.drawable.category_default, Color.parseColor(entry.getValue().htmlColorCode));
            }
        }
        return DefaultCategory.createDefaultCategoryModel("Others");
    }

    public static List<Category> getCategories(User user) {
        List<Category> categories = new ArrayList<>();
        categories.addAll(Arrays.asList(DefaultCategory.getDefaultCategories()));
        categories.addAll(getCustomCategories(user));
        return categories;
    }

    public static List<Category> getCustomCategories(User user) {
        ArrayList<Category> categories = new ArrayList<>();
        for(Map.Entry<String, WalletEntryCategory> entry : user.customCategories.entrySet()) {
            String categoryName = entry.getKey();
            categories.add(new Category(categoryName, entry.getValue().visibleName, R.drawable.category_default, Color.parseColor(entry.getValue().htmlColorCode)));
        }
        return categories;
    }
}
