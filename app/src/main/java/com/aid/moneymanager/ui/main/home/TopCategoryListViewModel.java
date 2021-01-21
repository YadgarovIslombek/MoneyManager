/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 21.01.21 23:24
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.home;


import com.aid.moneymanager.firebase.models.Currency;
import com.aid.moneymanager.models.Category;

public class TopCategoryListViewModel {
    private long money;
    private final Currency currency;
    private final Category category;
    private String categoryName;

    public TopCategoryListViewModel(Category category, String categoryName, Currency currency, long money) {
        this.category = category;
        this.categoryName = categoryName;
        this.currency = currency;
        this.money = money;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public long getMoney() {
        return money;
    }

    public Category getCategory() {
        return category;
    }
}
